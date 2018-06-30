import requests
import time
import datetime
import json
import pymongo


connect_url = 'mongodb://ligen:fetwcgxo1@120.25.244.188:20001/ligen?authMechanism=SCRAM-SHA-1'
client = pymongo.MongoClient(connect_url)
db = client.ligen

# collection = 'ynwfc'
# collection = 'ynffc'
collection = 'cqssc'
count = 0
while count <= 10:
    response = requests.get('https://34589.com/api/lottery/' + collection + '/history?page=' + str(count) + '&size=50')
    response.encoding = "utf-8"
    html = response.text
    print(html)
    rawData = json.loads(html)
    content = rawData['content']
    for c in content:
        time_local = time.localtime(int(c['drawTime'])/1000)
        dt = time.strftime("%Y-%m-%d %H:%M:%S", time_local)
        no = c['no']
        result = "".join(c['result']['numbers'])
        data = {}
        data['_id'] = dt
        data['no'] = no
        data['result'] = result
        db[collection].save(data)

    count += 1
    print(count)

