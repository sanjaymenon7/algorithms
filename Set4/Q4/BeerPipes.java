import java.io.*;
import java.util.*;
import java.text.*;
public class BeerPipes 
{
	public static void main(String[] args)
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			DirectionalFlowGraph[] graphs = new DirectionalFlowGraph[caseCount];
			
			/*read inputs for each test case*/
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++)
			{
				String[] input = bufferReader.readLine().split("\\s+");
				int innerVertices = Integer.parseInt(input[0]);
				int edgeCount = Integer.parseInt(input[1]);
				DirectionalFlowGraph graph = new DirectionalFlowGraph(innerVertices);
				graphs[caseNumber] = graph;
				/*read the edges*/
				for (int edgeNumber = 0; edgeNumber < edgeCount; edgeNumber++) 
				{
					String[] edgeDetail = bufferReader.readLine().split("\\s+");
					int start = Integer.parseInt(edgeDetail[0]);
					int end = Integer.parseInt(edgeDetail[1]);
					int numberOfSides = Integer.parseInt(edgeDetail[2]);
					double length = Double.parseDouble(edgeDetail[3]);
					if(length != 0)
					{
						double capacity = 0;
						if(numberOfSides == 0)
						{
							capacity = length * length * Math.PI;
						}
						else
						{
							capacity = (length * length * numberOfSides) / (4 * Math.tan(Math.PI / numberOfSides));
						}
						
						//System.out.printf("Capacity of %d to %d :  %f\n", start, end, capacity);
						graph.vertices[start].incidentOnEdge.add(new Edge(start, end, capacity, graph.vertices[end].incidentOnEdge.size()));
						graph.vertices[end].incidentOnEdge.add(new Edge(end, start, capacity, graph.vertices[start].incidentOnEdge.size() - 1));
					}
				}
				
				graph.DinitzMaxFlow();
				/*read empty line if not the last case*/
				if(caseNumber != caseCount - 1)
					bufferReader.readLine();
			}
			
			/*output results*/
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++)
			{
				DirectionalFlowGraph graph = graphs[caseNumber];
				if(graph.maxFLow != 0.0D)
				{
					bufferWriter.write(String.format("Case #%d: %s\n", caseNumber + 1, new DecimalFormat("#0.00000000").format(graph.maxFLow)));
				}
				else
				{
					bufferWriter.write(String.format("Case #%d: %s\n", caseNumber + 1, "impossible"));
				}
				
			}
			bufferWriter.flush();
		}
		catch(Exception e)
		{
			//System.out.println(e.getMessage());
		}
	}

}
class DirectionalFlowGraph
{
	int numberOfVertices;
	Vertex[] vertices;
	int sourceVertex, targetVertex;
	
	double maxFLow;
 	public DirectionalFlowGraph(int innerVertices) 
	{
		this.numberOfVertices = innerVertices;
		vertices = new Vertex[innerVertices + 1];
		for (int vertexId = 1; vertexId <= innerVertices ; vertexId++)
		{
			vertices[vertexId] = new Vertex(vertexId, 0);
		}
		sourceVertex = 1;
		targetVertex = innerVertices;
		
		maxFLow = 0;
	}
 
 	/*Inspiration drawn from 
 	 * https://sites.google.com/site/indy256/algo/dinic_flow*/
 	public void DinitzMaxFlow()
 	{
 		/*flow of each edge already set to 0*/
 		/*Construct Gl from Gf of G*/
 		int[] dist = new int[vertices.length];
 		while (BFS(sourceVertex, targetVertex, dist))
 		{
 			//System.out.println("New BFS");
 			int[] ptr = new int[vertices.length];
 			while(true)
 			{
 				double df = getPrecisionDouble(DFSForBlockingFLow(ptr, dist, targetVertex, sourceVertex, getPrecisionDouble(Double.MAX_VALUE)));
 		        if (df == getPrecisionDouble(0.0D))
 		          break;
 		        maxFLow += df;
 			}
 		}
 	}
 	
 	private boolean BFS(int sourceId, int targetId, int[] dist)
 	{
 		Arrays.fill(dist, -1);
 		dist[sourceId] = 0;
 		int[] Q = new int[vertices.length];
 		int sizeOfQ = 0;
 		Q[sizeOfQ++] = sourceId;
 		for (int i = 0; i < sizeOfQ; i++)
 		{
 			int u = Q[i];
			for(Edge e:vertices[u].incidentOnEdge)
			{
				if(dist[e.end] < 0 && e.getFlow() < e.getCapacity())
				{
					dist[e.end] = dist[u] + 1;
					Q[sizeOfQ++] = e.end;
				}
			}
		}
 		return dist[targetId] >= 0;
 	}
 	
 	private double DFSForBlockingFLow(int[] ptr, int[] dist, int targetId, int u, double flow)
 	{
 		if(u == targetId)
 		{
 			return flow;
 		}
 		for (; ptr[u] < vertices[u].incidentOnEdge.size(); ++ptr[u])
 		{
 			Edge e = vertices[u].incidentOnEdge.get(ptr[u]);
 			if(dist[e.end] == dist[u] + 1 && e.getFlow() < e.getCapacity())
 			{
 				double df = getPrecisionDouble(DFSForBlockingFLow(ptr, dist, targetId, e.end, Math.min(flow, e.getCapacity() - e.getFlow())));
 				if(df > getPrecisionDouble(0.0D))
 				{
 					e.flow = e.getFlow() + df;
 					//System.out.printf("capacity of edge %d to %d now: %f\n", e.start, e.end, e.getFlow());
 					vertices[e.end].incidentOnEdge.get(e.reverse).flow = vertices[e.end].incidentOnEdge.get(e.reverse).getFlow() - df;
 					return df;
 				}
 			}		
 		}
 		return 0.0D;
 	}
 	
 	private double getPrecisionDouble(double target)
 	{
 		double result = Double.parseDouble(new DecimalFormat("#0.00000000").format(target));
 		return result;
 	}
}

class Vertex
{
	int id;
	int distance;
	List<Edge> incidentOnEdge;
	
	public Vertex(int id, int distance)
	{
		this.id= id;
		this.distance = distance;
		this.incidentOnEdge = new ArrayList<Edge>();
	}
}

class Edge
{
	int start;
	int end;
	double capacity;
	double flow;
	int reverse;
	double getCapacity()
	{
		return getPrecisionDouble(capacity);
	}
	
	double getFlow()
	{
		return getPrecisionDouble(flow);
	}
	public Edge(int start, int end, double capacity, int rev) 
	{
		this.start = start;
		this.end = end;
		this.capacity = capacity;
		this.flow = 0;
		this.reverse = rev;
	}
	private double getPrecisionDouble(double target)
 	{
 		double result = Double.parseDouble(new DecimalFormat("#0.00000000").format(target));
 		return result;
 	}
}