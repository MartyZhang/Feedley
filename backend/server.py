#!flask/bin/python

# GET: Obtain info
# POST: Create new info
# PUT: Update info
# DELETE: Delete info

from flask import Flask, jsonify, request, render_template
from lxml import html
import requests
import urllib2
import json
import os
import config

app = Flask(__name__)

apiKEY = '0c25d304894f3e6d646227454280b8fe'


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

    page = requests.get(request[title]['source_url'])
    tree = html.fromstring(page.text)
    ingredients_formatted = []
    recipe_formatted = []
    numbers = tree.xpath('//span[@class="ingredient-amount"]/text()')
    ingredients = tree.xpath('//span[@class="ingredient-name"]/text()')
    recipe = tree.xpath('//span[@class="plaincharacterwrap break"]/text()')
    for ingredient, number in zip(ingredients, numbers):
        ingredients_formatted.append(number + ' ' + ingredient)

    for instruction in recipe:
        recipe_formatted.append(instruction)

    return {'ingredients': ingredients_formatted, 'recipe': recipe_formatted}

@app.route('/')
def main():
    return render_template('index.html')

'''
    Send a response via GET request
'''
@app.route('/getrecipes', methods=['GET'])
def get_recipes():
    # get items as url arguements
    response = request.args.get('items')
    items = str(response)
    # append API key and items to food2fork API URL
    url = 'http://food2fork.com/api/search?key='+apiKEY+'&q='+items

    # retrieve JSON Data
    json_data = json.load(urllib2.urlopen(url))
    # format json data and remove unecessary properties
    formatted_data = remove_properties(json_data)

    # create new list with recipe instructions
    recipes = []
    for recipe in formatted_data['recipes']:
        if "allrecipes" in recipe['source_url']:
            image_and_source = {
                recipe['title']:
                    {'image_url': recipe['image_url'],
                     'source_url': recipe['source_url']
                    }
            }
            ingred_instr = get_recipe_allrecipe(image_and_source, recipe['title'])
            element = {
                'title': recipe['title'],
                'image_url': recipe['image_url'],
                'source_url': recipe['source_url'],
                'ingredients': ingred_instr['ingredients'],
                'recipe': ingred_instr['recipe']
            }
            recipes.append(element)
           # recipes.append(recipes_elem)

    return jsonify({'recipes': recipes})

if __name__ == '__main__':
    app.debug=True
    app.run(host='0.0.0.0', port=PORT)
