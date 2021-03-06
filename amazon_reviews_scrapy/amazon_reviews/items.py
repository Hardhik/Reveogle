# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class AmazonReviewsItem(scrapy.Item):
    productUrl = scrapy.Field()
    productName = scrapy.Field()
    imageLink = scrapy.Field()
    reviewTitle = scrapy.Field()
    reviewText = scrapy.Field()
    reviewDate = scrapy.Field()
    reviewUrl = scrapy.Field()
