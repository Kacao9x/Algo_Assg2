// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.io.*;
import java.util.*;


public class GraphProcessor
{
	// other member fields and methods
	private Map<String, ArrayList<String>> graph = new LinkedHashMap<>();
	

	// NOTE: graphData should be an absolute file path
	public GraphProcessor(String graphData)
	{
		try{
			BufferedReader input = new BufferedReader(new FileReader(graphData));
			//skim number of vertices
			input.readLine();
			//read edges
			String[] chunks = input.readLine().split(" ");
			String u = chunks[0];
			String v = chunks[1];
			addVertex(u);
			addEdge(u,v);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void addVertex(String v) {
		if(v == null || graph.containsKey(v))
			return;
		
		graph.put(v, new ArrayList<>());
		
	}
	
	
	private void addEdge(String u, String v) {
		if(u == null || v == null 
				|| u != null && !graph.containsKey(u)
				|| u != null && graph.containsKey(u))
			return;
		
		graph.get(u).add(v);
	}

	public int outDegree(String v)
	{
		if (v == null || !graph.containsKey(v))
			return -1;
		
		return graph.get(v).size();
		
	}

	public ArrayList<String> bfsPath(String u, String v)
	{
		// implementation
	}

	public int diameter()
	{
		// implementation
	}

	public int centrality(String v)
	{
		// implementation
	}

}