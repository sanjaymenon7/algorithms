package set4q2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class RobberyDinic {
	
	  static class Edge {
		    int t, rev, cap, f;

		    public Edge(int t, int rev, int cap) {
		      this.t = t;
		      this.rev = rev;
		      this.cap = cap;
		    }
		  }

		  public static List<Edge>[] createGraph(int nodes) {
		    List<Edge>[] graph = new List[nodes];
		    for (int i = 0; i < nodes; i++)
		      graph[i] = new ArrayList<>();
		    return graph;
		  }

		  public static void addEdge(List<Edge>[] graph, int s, int t, int cap) {
		    graph[s].add(new Edge(t, graph[t].size(), cap));
		    graph[t].add(new Edge(s, graph[s].size() - 1, 0));
		  }

		  static boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
		    Arrays.fill(dist, -1);
		    dist[src] = 0;
		    int[] Q = new int[graph.length];
		    int sizeQ = 0;
		    Q[sizeQ++] = src;
		    for (int i = 0; i < sizeQ; i++) {
		      int u = Q[i];
		      for (Edge e : graph[u]) {
		        if (dist[e.t] < 0 && e.f < e.cap) {
		          dist[e.t] = dist[u] + 1;
		          Q[sizeQ++] = e.t;
		        }
		      }
		    }
		    return dist[dest] >= 0;
		  }

		  static int dinicDfs(List<Edge>[] graph, int[] ptr, int[] dist, int dest, int u, int f) {
		    if (u == dest)
		      return f;
		    for (; ptr[u] < graph[u].size(); ++ptr[u]) {
		      Edge e = graph[u].get(ptr[u]);
		      if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
		        int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
		        if (df > 0) {
		          e.f += df;
		          graph[e.t].get(e.rev).f -= df;
		          return df;
		        }
		      }
		    }
		    return 0;
		  }

		  public static int maxFlow(List<Edge>[] graph, int src, int dest) {
		    int flow = 0;
		    int[] dist = new int[graph.length];
		    while (dinicBfs(graph, src, dest, dist)) {
		      int[] ptr = new int[graph.length];
		      while (true) {
		        int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
		        if (df == 0)
		          break;
		        flow += df;
		      }
		    }
		    return flow;
		  }

		  // Usage example
		  public static void main(String[] args) {
				// int[][] capacity = { { 0, 3, 2 }, { 0, 0, 2 }, { 0, 0, 0 } };

				int t;
				// output string
				String outputFormat = new String("%s\n");

				// initialize reader and writer
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
				BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

				try {

					t = Integer.parseInt(bufferReader.readLine());
					for (int testcase = 1; testcase <= t; testcase++) {
						
						String[] cityDetails = bufferReader.readLine().split("\\s+");
						int numOfCops = Integer.parseInt(cityDetails[0]);
						int numOfJunctions = Integer.parseInt(cityDetails[1]);
						int numOfRoads = Integer.parseInt(cityDetails[2]);
						List<Edge>[] graph = createGraph(numOfJunctions+1);
						
						
						for (int road = 0; road < numOfRoads; road++) {
							String[] roadDetails = bufferReader.readLine().split("\\s+");
							int source = Integer.parseInt(roadDetails[0]);
							int dest = Integer.parseInt(roadDetails[1]);
							int capacity = Integer.parseInt(roadDetails[2]);
							addEdge(graph,source, dest, capacity);
							addEdge(graph,dest, source, capacity);										
						}
						
						int maxFlow = maxFlow(graph,1, numOfJunctions);
						if(maxFlow<=numOfCops)
							System.out.println("Case #"+testcase+": yes");
						else
							System.out.println("Case #"+testcase+": no");
						
						if (testcase != t)
							bufferReader.readLine();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
		  }

}
