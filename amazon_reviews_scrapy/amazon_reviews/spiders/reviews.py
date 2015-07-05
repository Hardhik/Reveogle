import scrapy
#from scrapy.spider import BaseSpider
from scrapy.contrib.spiders import CrawlSpider, Rule
from scrapy.contrib.linkextractors.sgml import SgmlLinkExtractor
from urlparse import urlparse
from scrapy.selector import HtmlXPathSelector
from amazon_reviews.items import AmazonReviewsItem
from scrapy.http import Request
import re
 
class MySpider(CrawlSpider):
	name = "amazon"
	allowed_domains = ["www.amazon.com"]
	#start_urls = ["http://www.amazon.com/gp/product/B00VQP3DNY"]
	start_urls = ["http://www.amazon.com","http://www.amazon.com/Oneplus-One-Android-3100mAh-Battery/dp/B00LEAPLWW","http://www.amazon.com/HP-Stream-Celeron-Horizon-Personal/dp/B00NSHLUBU","http://www.amazon.com/Fitbit-Surge-Fitness-Superwatch-Black/dp/B00N2BWF6Q","http://www.amazon.com/gp/product/B00UB76290/"]
	#start_urls = ["http://www.amazon.com/Canon-EOS-Rebel-T5-Professional/dp/B00J34YO92"]
	rules = [
		Rule(SgmlLinkExtractor(allow=(r'^(?:ftp|http|https):\/\/(?:[\w\.\-\+]+:{0,1}[\w\.\-\+]*@)?(?:[a-z0-9\-\.]+)(?::[0-9]+)?(?:\/|\/(?:[\w#!:\.\?\+=&amp;%@!\-\/\(\)]+)|\?(?:[\w#!:\.\?\+=&amp;%@!\-\/\(\)]+))?$'),
					deny=(
					r'amazon\.com\/gp\/voting\/*',
					r'amazon\.com\/gp\/help\/*',
					r'https:\/\/www/.amazon\.com\/ap\/signin*',
					r'amazon\.com\/gp\/vine\/*',
					r'amazon\.com\/ap\/forgotpassword*',
					r'amazon\.com\/review\/*',
					r'amazon\.com\/gp\/item-dispatch*'
					r'amazon\.com\/forum\/*',
					r'amazon\.com\/gp\/student\/*',
					r'amazon.com\/gp\/most-wished-for*',
					r'amazon.com\/gp\/most-gifted*',
					r'amazon.com\/Best-Sellers*',
					r'amazon.com\/gp\/bestsellers*',
					),
					allow_domains=('www.amazon.com')
					),
					callback='parse_item', 
					follow=True
				
		)
	]

	def parse_item(self, response):
		hxs = HtmlXPathSelector(response)
		for sel in hxs.select('//div[@id="revMHRL"]/div'):
		    item = AmazonReviewsItem()
		    item['productUrl'] = response.request.url
		    item['productName'] = hxs.select('//span[@id="productTitle"]/text()').extract()
		    if(item['productName']==[]):
		        continue
		    item['imageLink'] = hxs.select('//div[@id="imgTagWrapperId"]/img/@src').extract()
		    item['reviewTitle'] = sel.select('./div/div/a[@class="a-link-normal a-text-normal a-color-base"]/span/text()').extract()
		    item['reviewDate'] = sel.select('./div/span[@class="a-color-secondary"]/span[@class="a-color-secondary"]/text()').extract()
		    	
		    #item['reviewText'] = sel.select('./div/div[@class="a-section"]/text()').extract()
		    item['reviewUrl']=sel.select('//div[@id="revF"]/div/a[@class="a-link-emphasis a-text-bold"]/@href').extract()
		    revText = sel.select('./div/div[@class="a-section"]/text()').extract()
	 	    temp_str= ' '.join(revText)
		    try:
#temp_str = revText[0]
			if(temp_str[-1]=='\n'):
			    temp_str=temp_str[:-1]
		    	temp_str= temp_str.replace("\n", "")
			temp_str=temp_str.translate(None, "\n")
			item['reviewText'] = temp_str
		    except :
		   	item['reviewText'] = temp_str
		    if(item['reviewText']=="   "):
		    	item['reviewText'] = sel.select('./div/div/span[@class="MHRHead"]/text()').extract()
	#	    item['link'] = "http://amazon.com" + sel.xpath('.//div//a[contains(@class,"a-size-base a-link-normal review-title a-color-base a-text-bold")]/@href').extract()[0]
		    yield item
		
