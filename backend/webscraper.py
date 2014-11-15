from lxml import html
import requests



## Get recipe from allrecipes.com
def get_recipe_allrecipe(request):
#  request = 'http://allrecipes.com/Recipe/Slow-Cooker-Chicken-Tortilla-Soup/Detail.aspx'
  req = request.replace('Detail.aspx', 'kitchenview.aspx') # Reformat to kitchenview.aspx
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


## Get recipe from simplyrecipes.com
def get_recipe_simplyrecipes(request):
  page = requests.get(request)
  tree = html.fromstring(page.text)
  ingredients = tree.xpath('//li[@class="ingredient"]/text()')
  recipe = tree.xpath('//div[@itemprop="recipeInstructions"]/p/text()')

  print 'INGREDIENTS'
  for ingredient in ingredients:
    print ingredient

  print 'RECIPE'
  for instruction in recipe:
    print instruction


if __name__ == '__main__':
  #request = 'http://allrecipes.com/Recipe/Slow-Cooker-Chicken-Tortilla-Soup/Detail.aspx'
  request = 'http://www.simplyrecipes.com/recipes/arroz_con_pollo/'
  if "allrecipes" in request:
    get_recipe_allrecipe(request)
  if "simplyrecipes" in request:
    get_recipe_simplyrecipes(request)
