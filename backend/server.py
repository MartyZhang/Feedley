#!flask/bin/python

# GET: Obtain info
# POST: Create new info
# PUT: Update info
# DELETE: Delete info

from flask import Flask, jsonify, request
import urllib2, json
import config

app = Flask(__name__)

# http://localhost:5000/getrecipes?items=chicken,tomato
@app.route('/getrecipes', methods=['GET'])
def get_recipes():
    response = request.args.get('items')
    items = str(response)
    url = 'http://food2fork.com/api/search?key='+config.apiKEY+'&q='+items
    data = json.load(urllib2.urlopen(url))
    print url
    return jsonify({'recipes': data})

if __name__ == '__main__':
    app.run(debug=True)