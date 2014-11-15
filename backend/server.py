#!flask/bin/python

# GET: Obtain info
# POST: Create new info
# PUT: Update info
# DELETE: Delete info

from flask import Flask, jsonify, request
import urllib2
import json
import config

app = Flask(__name__)

apiKEY = '0c25d304894f3e6d646227454280b8fe'

def parse(data):
    properties = ['f2f_url', 'publisher', 'publisher_url', 'social_rank']
    for i in data['recipes']:
        for j in properties:
            del i[j]
    return data


# http://localhost:5000/getrecipes?items=chicken,tomato
@app.route('/getrecipes', methods=['GET'])
def get_recipes():
    response = request.args.get('items')
    items = str(response)
    url = 'http://food2fork.com/api/search?key='+apiKEY+'&q='+items
    json_data = json.load(urllib2.urlopen(url))
    formatted_data = parse(json_data)
    print url
    d = formatted_data
    recipes = {}

    for recipe in formatted_data['recipes']:
      if "simplyrecipe" in recipe['source_url']:
        recipes[recipe['title']] = [recipe['image_url'], recipe['source_url']]
      if "allrecipes" in recipe['source_url']:
        recipes[recipe['title']] = [recipe['image_url'], recipe['source_url']]

    return jsonify({'recipes': recipes})


if __name__ == '__main__':
    app.run(debug=True)
