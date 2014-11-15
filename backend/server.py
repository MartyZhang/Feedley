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
import config

app = Flask(__name__)

apiKEY = '0c25d304894f3e6d646227454280b8fe'

def parse(data):
    properties = ['f2f_url', 'publisher', 'publisher_url', 'social_rank']
    for i in data['recipes']:
        for j in properties:
            del i[j]
    return data

## Get recipe from allrecipes.com
def get_recipe_allrecipe(request, title):
#  request = 'http://allrecipes.com/Recipe/Slow-Cooker-Chicken-Tortilla-Soup/Detail.aspx'
  req = request[title]['source_url'].replace('Detail.aspx', 'kitchenview.aspx') # Reformat to kitchenview.aspx
  print req
  page = requests.get(req)

  tree = html.fromstring(page.text)
  ingredients = tree.xpath('//ul[@class="generaltext"]/li/text()')
  recipe = tree.xpath('//div[@class="direction"]/text()')

  print 'INGREDIENTS'
  for ingredient in ingredients:
    print ingredient.replace('\r\n', '', 2)

  print 'RECIPE'
  for instruction in recipe:
    print instruction.replace('\r\n', '')

  return {'ingredients': ingredients, 'recipe': recipe}


## Get recipe from simplyrecipes.com
def get_recipe_simplyrecipes(request, title):
  page = requests.get(request[title]['source_url'])
  tree = html.fromstring(page.text)
  ingredients = tree.xpath('//li[@class="ingredient"]/text()')
  recipe = tree.xpath('//div[@itemprop="recipeInstructions"]/p/text()')

  print 'INGREDIENTS'
  for ingredient in ingredients:
    print ingredient

  print 'RECIPE'
  for instruction in recipe:
    print instruction

  return {'ingredients': ingredients, 'recipe': recipe}


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
        image_and_source = {recipe['title']: {'image_url': recipe['image_url'], 'source_url': recipe['source_url']}}
        recipes[recipe['title']] = {'image_url': image_and_source[recipe['title']]['image_url'], 'source_url': image_and_source[recipe['title']]['source_url'], 'ingredients': get_recipe_simplyrecipes(image_and_source, image_and_source.keys()[0])['ingredients'], 'recipe': get_recipe_simplyrecipes(image_and_source, image_and_source.keys()[0])['recipe'] }

      if "allrecipes" in recipe['source_url']:
        image_and_source = {recipe['title']: {'image_url': recipe['image_url'], 'source_url': recipe['source_url']}}
        recipes[recipe['title']] = {'image_url': image_and_source[recipe['title']]['image_url'], 'source_url': image_and_source[recipe['title']]['source_url'], 'ingredients': get_recipe_allrecipe(image_and_source, image_and_source.keys()[0])['ingredients'], 'recipe': get_recipe_allrecipe(image_and_source, image_and_source.keys()[0])['recipe'] }

    return jsonify({'recipes': recipes})




if __name__ == '__main__':
    app.run(debug=True)
