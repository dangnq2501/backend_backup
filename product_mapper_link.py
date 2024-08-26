import pandas as pd
def get_link(x):
    return '/'.join(x.split('/')[:-1])
df = pd.read_csv('products.csv')
df['origin_link'] = df['imageFile'].apply(get_link)
df = df[['origin_link', 'path_id']]
df.to_csv('link_mapper.csv', index=False)