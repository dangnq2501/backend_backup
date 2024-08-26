import pandas as pd
import numpy as np
import math

num_user = 500
num_item = 1066

df = pd.read_csv('/home/dominhnhat/recomend_sys/data/user_rating.csv')

user_item_rating = []

for i in range (0,num_user):
    for j in range (0,num_item):
        elem = df.iloc[i, j]
        print(i, ' ', j, ' ', elem)
        if (not math.isnan(elem)):
            tup = (i, j, elem)
            user_item_rating.append(tup)

#print(len(user_item_rating))

df = pd.DataFrame(user_item_rating)
df.columns = ['userId', 'movieId', 'rating']
df.to_csv('user_per_item.csv')