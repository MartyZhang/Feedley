#!flask/bin/python

# GET: Obtain info
# POST: Create new info
# PUT: Update info
# DELETE: Delete info

from flask import Flask, jsonify, request
import urllib2, json

apiKEY = '0c25d304894f3e6d646227454280b8fe'

app = Flask(__name__)

# format the items array
def format_ingredients(ingredients):
    formatted_items = [x.replace(' ','%20') for x in ingredients]
    return ','.join(formatted_items)

@app.route('/getrecipes', methods=['GET'])
def get_recipes():
    response = request.args.get('items')
    url = 'http://food2fork.com/api/search?key='+apiKEY+'&q='+str(format_ingredients(response))
    data = json.load(urllib2.urlopen(url))
    print url
    return jsonify({'recipes': data})

if __name__ == '__main__':
    app.run(debug=True)