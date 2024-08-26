import pandas as pd
import numpy as np
import random as rd

num_user = 5000
num_item = 10665

import os

def list_all_folders(folder_path):
    folders = []
    cnt = 0
    for root, dirs, files in os.walk(folder_path):
        for dir_name in dirs:

            path = os.path.join(root, dir_name) 

            tmp = os.path.basename(path)
            if (tmp.isdigit()):
                cnt += 1
                folders.append(path)

    arr = [[None for _ in range (num_item + 1)] for _ in range(num_user)]

    for i in range(0, num_user):
        arr[i][0] = i
        for j in range (1, num_item):
            num = rd.randint(0,20)
            if (num == 0):
                value = rd.randint(0,10)
                value /= 2
                arr[i][j] = value

    #print(arr)

    cols = [i for i in range (num_item + 1)]
    cols[0] = 'User'

    df = pd.DataFrame(arr, columns=cols)

    #print(df)

    df.to_csv('user_rating.csv', index = False)

folder_path = '/home/dominhnhat/recomend_sys/data/fashion200k/women' 
list_all_folders(folder_path)
