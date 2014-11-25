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
import ujson # FASTER JSON YO

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
    json_data = ujson.load(urllib2.urlopen(url))

    unwanted_properties = ['f2f_url', 'publisher', 'publisher_url', 'social_rank']

    # create new list with recipe instructions
    recipes = []
    for recipe in json_data['recipes']:

        # Delete unwanted information
        for j in unwanted_properties:
            del recipe[j]

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

'''
  Tentative alternative to API key
  To use: http://localhost:5000/webscrapetest?items=[ITEM1],[ITEM2],[ITEM3]
'''

@app.route('/webscrapetest')
def test():

    response = request.args.get('items')
    items = str(response)

    # Scrape f2f search page
    url = 'http://food2fork.com/top?q='+items
    page = requests.get(url)
    tree = html.fromstring(page.text)

    json_data = []

    titles = tree.xpath('//span[@class="recipe-name"]/text()')
    images = tree.xpath('//img[@src]')
    del images[0]
    del images[0]
    links = tree.xpath('//a[@class="recipe-link"]')
    recipe_urls = tree.xpath("//span[@class='publisher']/a[@href]/text()")

    for recipe_url, title, image, link in zip(recipe_urls, titles,images,links):

      # Only scrape two additional pages if recipe is from allrecipes.com
      if "All Recipes" in recipe_url:

        # Scrape f2f recipe page
        f2f_link = 'http://food2fork.com' + link.attrib['href']
        f2f_page = requests.get(f2f_link)
        f2f_tree = html.fromstring(f2f_page.text)
        ingredients = f2f_tree.xpath('//li[@itemprop="ingredients"]/text()')
        source_url = f2f_tree.xpath("//div[@class='span5 offset1 about-container']/a[contains(text(),'View on')]/@href")

        # Scrape actual recipe webpage
        ingred_instr = []
        image_and_source = {
          title.strip(): {'image_url': image.attrib['src'], 'source_url': source_url[0]}
        }
        ingred_instr = get_recipe_allrecipe(image_and_source, title.strip())['recipe']

        json_data.append({"title": title.strip(), "image_url": image.attrib['src'], "f2f_url": f2f_link, "ingredients": ingredients, "source_url": source_url[0], "recipe": ingred_instr})

      else:
        continue

    return jsonify({'recipes': json_data})


if __name__ == '__main__':
    app.debug=True
    app.run(host='0.0.0.0', port=PORT)
