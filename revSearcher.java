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
//import org.apache.commons.lang.StringEscapeUtils;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.File;

//import org.apache.lucene.analysis.SimpleAnalyzer;
//import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.util.Scanner;

public class revSearcher {

	public static void main(String[] args) throws Exception {
		String query  = new String();
//		System.out.println("The arguments given are: ");
		int i;
		for (i=0;i<args.length-1 ;i++){
//			System.out.println(args[i]);
			query+=(args[i]+" ");
		}
//		System.out.println(args[i]);
		query+=args[i];
//		System.out.println("the query: "+ query);

		File indexDir = new File("./index/");
		Scanner scanner = new Scanner(System.in);
		//String query = "lucene";
		// String query = scanner.nextLine();
		
		//query = StringEscapeUtils.escapeJava(query);

		int hits = 100;

		revSearcher searcher = new revSearcher();

		searcher.searchIndex(indexDir, query, hits);

	}

	private void searchIndex(File indexDir, String queryStr, int maxHits) 
		throws Exception {
			queryStr= "\""+queryStr+"\"";
			//Set<String> setNames = new HashSet<String>();
			Directory directory = FSDirectory.open(indexDir);
			IndexReader reader = DirectoryReader.open(directory);
			//IndexSearcher searcher = new IndexSearcher(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			/*QueryParser parser = new QueryParser(Version.LUCENE_30, 
					"contents", new SimpleAnalyzer());*/
			QueryParser parser = new QueryParser(Version.LUCENE_40,"productName", new StandardAnalyzer(Version.LUCENE_40));

			
			//Query query = parser.parse(QueryParser.escape(queryStr));

			Query query = parser.parse(queryStr);

			TopDocs topDocs = searcher.search(query, maxHits);

			ScoreDoc[] hits = topDocs.scoreDocs;



			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				if(i==0){
					System.out.println(d.get("productName"));
					System.out.println(d.get("imageLink"));
					System.out.println(d.get("productUrl"));
					System.out.println(d.get("reviewUrl"));
				}   
				//setNames.add(d.get("productName"));
				System.out.println(d.get("reviewTitle"));
				System.out.println(d.get("reviewDate"));
				System.out.println(d.get("reviewText"));
				//System.out.println("--------------------------------------------------------");
			}
			//System.out.println("Found " + hits.length);
			//System.out.println("hello\n");
			/*Iterator<String> itr = setNames.iterator();
			while(itr.hasNext()){
				System.out.println(itr.next());
			}*/

		}

}

