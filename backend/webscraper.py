from lxml import html
import requests

# Specific request will be of form:
# "Title:" [
#  "image_url",
#  "recipe_url",
#]


## Get recipe from allrecipes.com
def get_recipe_allrecipe(request, title):
#  request = 'http://allrecipes.com/Recipe/Slow-Cooker-Chicken-Tortilla-Soup/Detail.aspx'
  req = request[title][1].replace('Detail.aspx', 'kitchenview.aspx') # Reformat to kitchenview.aspx
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
  page = requests.get(request[title][1])
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


if __name__ == '__main__':
  request = {
    "Tuna Salad Sandwich": [
      "http://static.food2fork.com/tunasaladsandwich300x200684f355c.jpg",
      "http://www.simplyrecipes.com/recipes/tuna_salad_sandwich/"
    ]
  }

  title = request.keys()[0]
  if "allrecipes" in request[title][1]:
    get_recipe_allrecipe(request, title)
  if "simplyrecipes" in request[title][1]:
    get_recipe_simplyrecipes(request, title)
