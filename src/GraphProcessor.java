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
		ArrayList<String> path = new ArrayList<>();
		//end of line
		if (u == null || v == null)
			return path;
		
		Queue<String> queue = new LinkedList<>();		//stack to store vertices to 
		Set<String> visited = new HashSet<>();			//Hash map to store visited node 
		Map <String,String> nextVertex = new HashMap <String,String>(); // a new updated Map with visited node
		
		queue.add(u);
		visited.add(u);
		while( !queue.isEmpty() )
		{
			String current = queue.remove();
			if(current.equals(visited))
				break;
			
			for( String vi : graph.get(current) ) {
				if( !visited.contains(vi) ) {
					queue.add(vi);
					visited.add(vi);
					
					nextVertex.put(current,  vi);		//update map with visited nodes.
				}
			}
		}
		
		//REconstruct the path here
		
		
		return path;
	}

	public int diameter()
	{
		int max = 0;
		for( String uu : graph.keySet() ) {
			for( String vv : graph.get(uu) ) {
				int sz = bfsPath(uu,vv).size();
				if ( max < sz)
					max = sz;
			}
		}
		
		return max;
	}

	public int centrality(String v)
	{
		int n = 0;
		/* Lets count centrality as IN and OUT edges 
		(at least once these edges will be used for path) */
		for (String vv : graph.keySet()) {
			for (String ee : graph.get(vv)) {
				if (ee.equals(v))
					n++;
			}
		}
		return n + outDegree(v);
	}

}