package test;

import org.yooreeka.config.YooreekaConfigurator;
import org.yooreeka.examples.search.LuceneIndexer;
import org.yooreeka.examples.search.MySearcher;
import org.yooreeka.util.internet.crawling.FetchAndProcessCrawler;

public class sss {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//
		//-- Data (default URL list)
		//
		String yHome = YooreekaConfigurator.getHome();
		FetchAndProcessCrawler crawler = new FetchAndProcessCrawler(yHome+"/data/ch02",5,200);
		crawler.setDefaultUrls(); 
		crawler.run(); 

		//
		//-- Lucene
		//
		LuceneIndexer luceneIndexer = new LuceneIndexer(crawler.getRootDir());
		luceneIndexer.run(); 

		MySearcher oracle = new MySearcher(luceneIndexer.getLuceneDir());

		oracle.search("armstrong",5); 
	}

}
