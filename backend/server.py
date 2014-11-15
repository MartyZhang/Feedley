#!flask/bin/python

# GET: Obtain info
# POST: Create new info
# PUT: Update info
# DELETE: Delete info

from flask import Flask, jsonify, request
from lxml import html
import requests
import urllib2
import json
import os
import config

app = Flask(__name__)

PORT=int(os.environ.get('PORT', 5000))

# Sample command: http://localhost:5000/getrecipes?items=chicken,tomato

'''
    Remove unecessary properties from the JSON Data
'''
def remove_properties(data):
    properties = ['f2f_url', 'publisher', 'publisher_url', 'social_rank']
    for i in data['recipes']:
        for j in properties:
            del i[j]
    return data


'''
    Only get recipes from allrecipes using webscraper.py
'''
def get_recipe_allrecipe(request, title):

    # reformat URL
    req = request[title]['source_url'].replace('Detail.aspx', 'kitchenview.aspx')
    page = requests.get(req)

    # Scrape the web page
    tree = html.fromstring(page.text)
    ingredients = tree.xpath('//ul[@class="generaltext"]/li/text()')
    ingredients_formatted = []
    recipe = tree.xpath('//div[@class="direction"]/text()')
    recipe_formatted = []

    for ingredient in ingredients:
        ingredients_formatted.append(ingredient.strip())

    for instruction in recipe:
        recipe_formatted.append(instruction.strip())

    return {'ingredients': ingredients_formatted, 'recipe': recipe_formatted}

'''
    Send a response via GET request
'''
@app.route('/getrecipes', methods=['GET'])
def get_recipes():
    # get items as url arguements
    response = request.args.get('items')
    items = str(response)
    # append API key and items to food2fork API URL
    url = 'http://food2fork.com/api/search?key='+config.apiKEY+'&q='+items

    # retrieve JSON Data
    json_data = json.load(urllib2.urlopen(url))
    # format json data and remove unecessary properties
    formatted_data = remove_properties(json_data)

    # create new list with recipe instructions
    recipes = {}
    for recipe in formatted_data['recipes']:
        if "allrecipes" in recipe['source_url']:
            image_and_source = {recipe['title']: {'image_url': recipe['image_url'], 'source_url': recipe['source_url']}}
            recipes[recipe['title']] = {'image_url': image_and_source[recipe['title']]['image_url'], 'source_url': image_and_source[recipe['title']]['source_url'], 'ingredients': get_recipe_allrecipe(image_and_source, image_and_source.keys()[0])['ingredients'], 'recipe': get_recipe_allrecipe(image_and_source, image_and_source.keys()[0])['recipe'] }

    return jsonify({'recipes': recipes})

if __name__ == '__main__':
    app.debug=True
    app.run(host='0.0.0.0', port=PORT)
