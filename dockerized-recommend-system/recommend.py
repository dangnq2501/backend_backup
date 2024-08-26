from surprise import Reader, Dataset, SVD
from surprise.model_selection import cross_validate, KFold
import pandas as pd
import random as rd
import os
import joblib

num_user = 5000
num_item = 10665

new_user_id = 5000
new_user_item = 10
new_user = []

def get_folder_file(path):
    files = [f for f in os.listdir(path) if os.path.isfile(os.path.join(path, f))]
    return files

def generate_score():
    return rd.randint(0,10) / 2

def get_link_score(item_id):
    ans = item_link_df[item_link_df['ID'] == item_id]
    arr = ans['Link'].values
    return (arr[0], generate_score())

#path
home_path = '/app'
container_new_user_folder = home_path + '/new_user_query'
current_new_user_size = len(get_folder_file(container_new_user_folder))

while(True):
    link = None
    is_waiting = False
    while(link is None):
        if (is_waiting == False):
            print('Waiting new user query')
            is_waiting = True

        files = get_folder_file(container_new_user_folder)
        if (len(files) == current_new_user_size):
            continue
        else:
            print('Found new user query !!!')
            link = container_new_user_folder + '/' + files[current_new_user_size]
            current_new_user_size += 1    

    new_user_df = pd.read_csv(link)

    new_user_df = new_user_df[['userId', 'itemId', 'rating']]


    #path
    path = home_path + '/data/user_per_item.csv'
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
    path = home_path + '/model/svd_model.joblib' 
    svd = joblib.load(path)

    predicted_rating = []

    #path
    path = home_path + '/data/id_link.csv'
    item_link_df = pd.read_csv(path)

    for i in range (0,num_item):
        item = i
        ratings = svd.predict(new_user_id, item)
        item_link, ratings = get_link_score(item)
        predicted_rating.append((ratings, item_link))

    sorted_result = sorted(predicted_rating, key=lambda x: x[0], reverse = True)

    num5 = 2000

    best = sorted_result[:num5]
    rd.shuffle(best)

    user_query_result_path = home_path + '/result/result_' + str(current_new_user_size - 1) + '.txt' 

    with open(user_query_result_path, 'w') as file:
        for tup in best[:20]:
            print(tup[1])
            file.write(tup[1] + '\n')
    print('-' * 30)
    
'''

docker build -t recommend .
docker run -it --name recommender_sys -v $(pwd)/new_user_query:/app/new_user_query -v $(pwd)/result:/app/result recommend

'''