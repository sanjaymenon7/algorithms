package ShivBellFord;

import java.util.ArrayList;
import java.util.List;

public class BellmanFord {
	int nodes;
	List<Edge> edges;
	int []dist;
	public BellmanFord(int nodes,List<Edge> l)
	{
		this.nodes=nodes;
		//edges=new ArrayList<Edge>();
		edges=l;
		dist=new int[nodes];
	}
	public int algo(int src)
	{
		// Step 1: initialize graph
		for(int i=0;i<nodes;i++)
		{
			if(i!=src)
				dist[i]=Integer.MIN_VALUE;
		}
		dist[src]=0;
		
		// Step 2: relax edges repeatedly
		for(int i=0;i<nodes;i++)
		{
			for(Edge e:edges)
			{
				if(dist[e.src]==Integer.MAX_VALUE)
					continue;
				
				if(dist[e.src]+e.cost>dist[e.dest])
				{
					dist[e.dest]=dist[e.src]+e.cost;
				}
			}
				
		}
		
		// Step 3: check for negative-weight cycles
		boolean flag=false;
		for(Edge e:edges)
		{
			if(dist[e.src]+e.cost>dist[e.dest])
			{
				flag=true;
			}
		}
		if(flag==false)	
			return dist[nodes-1];
		else
			return -1;
	}
	

}
