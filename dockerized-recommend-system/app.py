from typing import List
from surprise import Reader, Dataset, SVD
from surprise.model_selection import cross_validate, KFold
import pandas as pd
import random as rd
import os
import joblib
from fastapi import FastAPI, Query

num_user = 5000
num_item = 10665

def generate_score():
    return rd.randint(0,10) / 2

def get_link_score(item_id, item_link_df):
    ans = item_link_df[item_link_df['ID'] == item_id]
    arr = ans['Link'].values
    return (arr[0], generate_score())

new_user_id = 5000
new_user_item = 10
new_user = []
app = FastAPI()

@app.get("/recommend_lists/")
def recommend_lists(product_history: List[str] = Query(...), rating_history: List[float] = Query(...), user_id: str = Query(...)):

    user_id = int(user_id)
    new_user_id = user_id
    #path
    path = 'data/id_link.csv'
    item_link_df = pd.read_csv(path)
    converted_product_list = [product.split('_')[-1] for product in product_history]
    matching_ids = []
    for product in converted_product_list:
        matching_row = item_link_df[item_link_df['Link'].str.endswith(product)]
        matching_ids.append(matching_row['ID'].values[0])
    new_user_df = pd.DataFrame({
     'userId': [user_id for x in range(len(matching_ids))],
     'itemId': matching_ids,
     'rating':rating_history
    })
    path = 'data/user_per_item.csv'
    reader = Reader()
    ratings = pd.read_csv(path)
    #ratings = ratings.drop('Unnamed: 0', axis = 0)
    #print(ratings)
    ratings = pd.concat([ratings, new_user_df])
    ratings.reset_index(drop=True)
    #print(ratings)

    #data = Dataset.load_from_df(ratings[['userId', 'itemId', 'rating']], reader)
    #cross_validate(algo, data, measures=['RMSE', 'MAE'], cv=kf, verbose=True)

    #path
    path = 'model/svd_model.joblib'
    svd = joblib.load(path)

    predicted_rating = []


    for i in range (0,num_item):
        item = i
        ratings = svd.predict(new_user_id, item)
        item_link, ratings = get_link_score(item, item_link_df)
        predicted_rating.append((ratings, item_link))

    sorted_result = sorted(predicted_rating, key=lambda x: x[0], reverse = True)

    num5 = 2000

    best = sorted_result[:num5]
    rd.shuffle(best)
    best = best[:100]
    converted_best = []
    print(best[:3])
    for (score, link) in best:
        parts = link.split('/')
        converted_best.append('_'.join([parts[-2], parts[-1]]))
    return converted_best