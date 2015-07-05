import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;



import java.io.File;

//import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;

public class SimpleFileIndexer {

	public static void main(String[] args) throws Exception {

		//File indexDir = new File("/home/swapna/freelance/scrapy/amazon_reviews/amazon_reviews/spiders/index");
		//String dataFile = "/home/swapna/freelance/scrapy/amazon_reviews/amazon_reviews/spiders/new_two.csv";
		File indexDir = new File("./index");
		String dataFile = "./new_two.csv";
		String suffix = "java";
/*
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
*/
		SimpleFileIndexer indexer = new SimpleFileIndexer();

		indexer.index(indexDir, dataFile, suffix);

		System.out.println("Indexing Done");

	}

	private void index(File indexDir, String dataFile, String suffix) throws Exception {

		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir),config);
		/*IndexWriter indexWriter = new IndexWriter(
				FSDirectory.open(indexDir), 
				new SimpleAnalyzer(),
				true,
				IndexWriter.MaxFieldLength.LIMITED);*/
		//indexWriter.setUseCompoundFile(false);

		//indexDirectory(indexWriter, dataDir, suffix);
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		//productUrl,reviewTitle,reviewUrl,reviewDate,reviewText,imageLink,productName
		try {

			br = new BufferedReader(new FileReader(dataFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] item = line.split(cvsSplitBy);
				System.out.println(item[0]);
				System.out.println(item[1]);
				System.out.println(item[2]);
				System.out.println(item[3]);
				System.out.println(item[4]);
				System.out.println(item[5]);
				System.out.println(item[6]);
				addDoc(indexWriter,item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
				System.out.println("Country [code= " + item[4] + " , name=" + item[5] + "]");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		//indexWriter.optimize();
		indexWriter.close();

	}
	//productUrl,reviewTitle,reviewUrl,reviewDate,reviewText,imageLink,productName
	private static void addDoc(IndexWriter w, String productUrl, String reviewTitle, String reviewUrl, String reviewDate, String reviewText, String imageLink, String productName) throws IOException {
		Document doc = new Document();
		/*doc.add(new TextField("productName", productName, Field.Store.YES));
		doc.add(new StringField("productUrl", productUrl, Field.Store.YES));
		doc.add(new StringField("reviewTitle", reviewTitle, Field.Store.YES));
		doc.add(new StringField("reviewUrl", reviewUrl, Field.Store.YES));
		doc.add(new StringField("reviewDate", reviewDate, Field.Store.YES));
		doc.add(new StringField("reviewText", reviewText, Field.Store.YES));
		doc.add(new StringField("imageLink", imageLink, Field.Store.YES));*/
		doc.add(new StringField("productUrl", productUrl, Field.Store.YES));
		doc.add(new StringField("reviewTitle", reviewTitle, Field.Store.YES));
		doc.add(new StringField("reviewUrl", reviewUrl, Field.Store.YES));
		doc.add(new StringField("reviewDate", reviewDate, Field.Store.YES));
		doc.add(new StringField("reviewText", reviewText, Field.Store.YES));
		doc.add(new StringField("imageLink", imageLink, Field.Store.YES));
		doc.add(new TextField("productName", productName, Field.Store.YES));
		//productUrl,reviewTitle,reviewUrl,reviewDate,reviewText,imageLink,productName

		w.addDocument(doc);
	}

}
