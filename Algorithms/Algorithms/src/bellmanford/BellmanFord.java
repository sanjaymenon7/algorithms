package bellmanford;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Vector;
// Source modified from https://www.dropbox.com/s/ib3n6ma71nuvrjd/singlesource.txt
public class BellmanFord {

	public static int INF = Integer.MAX_VALUE;

	// this class represents an edge between two nodes
	static class Edge {
		int source; // source node
		int destination; // destination node
		int weight; // weight of the edge

		public Edge() {
		}; // default constructor

		public Edge(int s, int d, int w) {
			source = s;
			destination = d;
			weight = w;
		}
	}

	public static void main(String[] args) {
		
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		try {
			int t = Integer.parseInt(bufferReader.readLine());
			
			for (int testcase = 1; testcase <=t; testcase++) {
				Vector<Edge> edges = new Vector<Edge>();
				 int sinkNodeWeight =0;
				int numOfNodes = Integer.parseInt(bufferReader.readLine());	
				for (int node = 0; node < numOfNodes; node++) {
					String[] nodeDetails = bufferReader.readLine().split("\\s+");
					int weight =Integer.parseInt(nodeDetails[0]);
					int numOfNeighbours = Integer.parseInt(nodeDetails[1]);
					for (int neighbour = 2; neighbour < nodeDetails.length; neighbour++) {
						int adjNode = Integer.parseInt(nodeDetails[neighbour]);
						edges.add(new Edge(node, adjNode-1, (weight*-1)));
						
					}
					if (numOfNeighbours ==0)
						sinkNodeWeight = weight;
					
				}
				bellmanFord(edges, numOfNodes, 0, sinkNodeWeight, testcase);
				 if(testcase  != t)
					bufferReader.readLine();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public static void bellmanFord(Vector<Edge> edges, int nnodes, int source, int sinkNodeWeight, int testcase ) {
		int[] distance = new int[nnodes];
		Arrays.fill(distance, INF);
		distance[source] = 0;
		for (int i = 0; i < nnodes; ++i)
			for (int j = 0; j < edges.size(); ++j) {

				if (distance[edges.get(j).source] == INF)
					continue;

				int newDistance = distance[edges.get(j).source]
						+ edges.get(j).weight;

				if (newDistance < distance[edges.get(j).destination])
					distance[edges.get(j).destination] = newDistance;
			}

		/*for (int i = 0; i < edges.size(); ++i)

			if (distance[edges.get(i).source] != INF
					&& distance[edges.get(i).destination] > distance[edges
							.get(i).source] + edges.get(i).weight) {
				System.out.println("Negative edge weight cycles detected!");
				return;
			}*/
		int totalweight = ((sinkNodeWeight*-1)+distance[distance.length-1])*-1;
		System.out.println("Case #"+testcase+": "+ (totalweight));
		/*for (int i = 0; i < distance.length; ++i)
			if (distance[i] == INF)
				System.out.println("There's no path between " + source
						+ " and " + i);
			else
				System.out.println("The shortest distance between nodes "
						+ source + " and " + i + " is " + distance[i]);*/
	}
}
