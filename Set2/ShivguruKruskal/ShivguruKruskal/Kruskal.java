package ShivguruKruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// copied from http://www.keithschwarz.com/interesting/code/?dir=kruskal
public class Kruskal {
	/*minimum spanning tree of the graph represented as an list of edges*/
	private List<Edge> st;
	
	
	public List<Edge> getMinimumSpanningTree(Graph g,int n)
	{
		/*if minimum spanning tree has already been generated then return*/
		if(st != null)
		{
			return st;
			
		}
		else
		{
			/*generate the spanning tree*/
			
			/* Begin by building up a collection of all the edges of the graph.
	         */
	        List<Edge> edges = g.getEdges();
	        /*sort the edges by increasing weight*/
	        Collections.sort(edges);
	        
	        /*create the Unionfind class object*/
	        UnionFind unionFind = new UnionFind(n);
	        
	        /*keep track of how many edges have been added to the spanning tree
	         * only N - 1 edges are required*/
	        int edgesAdded = 0;
	        st = new ArrayList<Edge>();
	        for(Edge edge:edges)
	        {
	        	/*if the edges are connected then skip the process*/
	        	if(unionFind.find(edge.start) == unionFind.find(edge.end))
	        	{
	        		continue;
	        	}
	        	/*else update the spanning tree
	        	 * end is added as the parent of start*/
	        	unionFind.union(edge.start, edge.end);
	        	
	        	/*add edge to graph*/
	        	st.add(edge);
	        	if(++edgesAdded == n - 1)
	        		break;
	        }
			return st;
		}
	}

}
