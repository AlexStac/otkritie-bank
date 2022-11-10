#!/usr/bin/env python
# coding: utf-8

# In[22]:


import pickle
import pandas as pd
em_model = 'emotional_model.sav'
cat_model = 'category_model.sav'

emotional_model = pickle.load(open(em_model, 'rb'))
category_model = pickle.load(open(cat_model, 'rb'))

data = input()
appeal = []
appeal.append(data)
data_dt = pd.DataFrame()
data_dt['appeal'] = appeal
data_dt['emotion'] = emotional_model.predict(appeal)
data_dt['category'] = category_model.predict(appeal)
data_dt.to_csv('final.csv', sep = '|', index = False)

