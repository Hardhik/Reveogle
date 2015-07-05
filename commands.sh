cd amazon_reviews/amazon_reviews/spiders
scrapy crawl amazon -o data.csv -t csv  #abort after sufficiently crawled.(Ctrl-C)
#sleep 20m 
mv data.csv ../../../
cd ../../..
rm -rf index
javac -classpath .:lucene-core-4.0.0.jar:lucene-analyzers-common-4.0.0.jar:lucene-queryparser-4.0.0.jar SimpleFileIndexer.java
java -classpath .:lucene-core-4.0.0.jar:lucene-analyzers-common-4.0.0.jar:lucene-queryparser-4.0.0.jar SimpleFileIndexer
javac -classpath .:lucene-core-4.0.0.jar:lucene-analyzers-common-4.0.0.jar:lucene-queryparser-4.0.0.jar Searcher.java
