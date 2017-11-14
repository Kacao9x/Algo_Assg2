// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.


import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.URL;


public class WikiCrawler {
    private static final String BASE_URL = "https://en.wikipedia.org";
    
    // other member fields and methods
    private String seedURL;
    private int max;
    private ArrayList<String> topics;
    private String fileName;

    //private int requestCounter = 0;
    
    public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName) throws IOException {
        this.seedURL = seedUrl;
        this.max = max;
        this.topics = topics;
        this.fileName = fileName;
        //requestCounter = 0;
    }

    // NOTE: extractLinks takes the source HTML code, NOT a URL
    public ArrayList<String> extractLinks(String doc) throws IOException {
        ArrayList<String> links = new ArrayList<>();
        Pattern pattern = Pattern.compile("<p>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(doc);
        int startIndex = -1;
        if (matcher.find()) {
            startIndex = matcher.start();
        }
        if (startIndex != -1) {
            String content = doc.substring(startIndex);
            StringTokenizer st = new StringTokenizer(content);
            HashSet<String> visited = new HashSet<>();
            while (st.hasMoreTokens()) {
                String toCheck = st.nextToken();
                int index = toCheck.indexOf("/wiki/");
                if (index != -1) {
                    if (validLink(toCheck)) {
                        String toAdd = toCheck.substring(index, toCheck.indexOf("\"", index + 1));
                        if (!visited.contains(toAdd)) {
                            visited.add(toAdd);
                            links.add(toAdd);
                        }
                    }
                }
            }
        }
        return links;
    }

    private boolean validLink(String toCheck) {
        return !toCheck.contains("#") && !toCheck.contains(":");
    }

    public void crawl() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
        HashSet<String> tobeVisited = new HashSet<>();
        HashSet<String> visited = new HashSet<>();
        ArrayDeque<String> queue = new ArrayDeque<>();

        out.write(String.valueOf(max));
        int numVisited = 0;
        String seedURL = this.seedURL;
        String content = getContent(seedURL);
        if (containTopics(content)) {
            tobeVisited.add(seedURL);
            queue.offer(seedURL);
            numVisited++;
        }
        while (queue.size() != 0) {
            seedURL = queue.poll();
            visited.add(seedURL);
            content = getContent(seedURL);
            ArrayList<String> links = extractLinks(content);
            for (String link : links) {
                if (link.equals(seedURL))
                    continue;
                if (numVisited < max) {
                    String contentURL = getContent(link);
                    if (!visited.contains(link)) {
                        if (containTopics(contentURL)) {
                            tobeVisited.add(link);
                            queue.offer(link);
                            numVisited++;
                            writeToFile(out, seedURL, link);
                        }
                    } else {
                        writeToFile(out, seedURL, link);
                    }
                } else {
                    if (tobeVisited.contains(link)) {
                        writeToFile(out, seedURL, link);
                    }
                }
            }
        }
        out.close();
    }

    private String getContent(String seedURL) throws IOException {
    	
    	if(seedURL == null) return "";
    	
    	//requestCounter++;
    	StringBuilder content = new StringBuilder();
        try {
        	URL url = new URL(BASE_URL + seedURL);
            InputStream inputStream = url.openStream();
            
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            	String LinetoRead;
                
                while ((LinetoRead = bufferedReader.readLine()) != null) {
                    content.append(LinetoRead).append("\n");
                    content.append(System.getProperty("line.separator"));
                }
            }
            
        } catch (Exception e) {} //do something to handle exception here
    	
        
        /* TAs recommend this to encapsulate the right content */
    	int start = content.toString().indexOf("<p>");
    	int end = content.toString().lastIndexOf("</p>");
    	
        return content.toString().substring(start, end);
    }


    private boolean containTopics(String content) {
        for (String topic : topics) {
            if (!Pattern.compile(Pattern.quote(topic), Pattern.CASE_INSENSITIVE).matcher(content).find()) {
                return false;
            }
        }
        return true;
    }

    private void writeToFile(BufferedWriter out, String from, String to) throws IOException {
        out.newLine();
        out.write(from + " " + to);
    }
}



