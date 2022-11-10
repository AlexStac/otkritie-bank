#!/usr/bin/env python
# coding: utf-8

# # КЛАССИФИКАЦИЯ ОБРАЩЕНИЙ ПОЛЬЗОВАТЕЛЕЙ

# In[ ]:


mapping = {'Банкоматы и офисы': 0,
           'Кредиты': 1,
           'Счета и карты': 2,
           'Управление данными': 3,
           'Технические неполадки/вопросы': 4,
           'Платежи': 5,
           'Прочее': 6,
           'Документы': 7,
           'Мошенничество': 8
          }
re_mapping = {y: x for x, y in mapping.items()}
neg_emotion_warn = '***********!Негативные эмоции, обратите внимание на сообщение!********'


# In[11]:


import pickle
import pandas as pd
import numpy as np
em_model = 'emotional_model.sav'
cat_model = 'category_model.sav'

emotional_model = pickle.load(open(em_model, 'rb'))
category_model = pickle.load(open(cat_model, 'rb'))

data = input("Введите ваше обращение: ")
appeal = []
appeal.append(data)
data_dt = pd.DataFrame()
data_dt['appeal'] = appeal
data_dt['emotion'] = np.where(emotional_model.predict(appeal), neg_emotion_warn, 0)
data_dt['category'] = pd.Series(category_model.predict(appeal)).map(re_mapping)
# data_dt.to_csv('final.csv', sep = '|', index = False)
if data_dt['emotion'].values[0] != "0":
    print("\033[31m{}".format(data_dt['emotion'].values[0]))
print('\033[30mКатегория:', data_dt.category[0])

