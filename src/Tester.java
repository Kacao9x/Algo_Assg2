import java.io.IOException;
import java.util.ArrayList;


public class Tester {
	public static void main (String[] args ) throws IOException {
		ArrayList<String> topics = new ArrayList<>();
		topics.add("Iowa State");
		topics.add("Cyclones");
		WikiCrawler crawler = new WikiCrawler("/wiki/Complexity_theory", 
				20, new ArrayList<>(), "WikiCC.txt");
		crawler.crawl();
		
		GraphProcessor graphPro = new GraphProcessor("WikiCC.txt");
	}
}
