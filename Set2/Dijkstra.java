package Week3;
// Code copied from http://en.literateprograms.org/index.php?title=Special:DownloadCode/Dijkstra%27s_algorithm_(Java)&oldid=15444
/* The authors of this work have released all rights to it and placed it
2 in the public domain under the Creative Commons CC0 1.0 waiver
3 (http://creativecommons.org/publicdomain/zero/1.0/).
4 
5 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
6 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
7 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
8 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
9 CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
10 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
11 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
12 
13 Retrieved from: http://en.literateprograms.org/Dijkstra's_algorithm_(Java)?oldid=15444
14 */
 
 import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


 
 class Vertex implements Comparable<Vertex>
 {
     public final int name;
     public List<Edge> adjacencies;
     public int minDistance = Integer.MAX_VALUE;
     public Vertex previous;
     public Vertex(int argName) { name = argName;adjacencies=new ArrayList<Edge>(); }
     public int getName(){return this.name;}
     public String toString() { String s="";
     							s+=name;
    	 						return s; }
     public int compareTo(Vertex other)
     {
         return Integer.compare(minDistance, other.minDistance);
     }
 
 }
 
 
 class Edge
 {
     public final Vertex target;
     public final int weight;
     public Edge(Vertex v2, int argWeight)
     { target = v2; weight = argWeight; }
 }
 
 public class Dijkstra
 {
     public static void computePaths(Vertex source)
     {
         source.minDistance = 0;
         PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
 	vertexQueue.add(source);
 
 	while (!vertexQueue.isEmpty()) {
 	    Vertex u = vertexQueue.poll();
 
             // Visit each edge exiting u
             for (Edge e : u.adjacencies)
             {
                 Vertex v = e.target;
                 int weight = e.weight;
                 int distanceThroughU = u.minDistance + weight;
 		if (distanceThroughU < v.minDistance) {
 		    vertexQueue.remove(v);
 
 		    v.minDistance = distanceThroughU ;
 		    v.previous = u;
 		    vertexQueue.add(v);
 		}
             }
         }
     }
 
     public static List<Vertex> getShortestPathTo(Vertex target)
     {
         List<Vertex> path = new ArrayList<Vertex>();
         for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
             path.add(vertex);
 
         Collections.reverse(path);
         return path;
     }
 
     public static void main(String[] args)
     {
/*         Vertex v0 = new Vertex("Harrisburg");
 	Vertex v1 = new Vertex("Baltimore");
 	Vertex v2 = new Vertex("Washington");
 	Vertex v3 = new Vertex("Philadelphia"); 	Vertex v4 = new Vertex("Binghamton");
90 	Vertex v5 = new Vertex("Allentown");
91 	Vertex v6 = new Vertex("New York");
92 	v0.adjacencies = new Edge[]{ new Edge(v1,  79.83),
93 	                             new Edge(v5,  81.15) };
94 	v1.adjacencies = new Edge[]{ new Edge(v0,  79.75),
95 	                             new Edge(v2,  39.42),
96 	                             new Edge(v3, 103.00) };
97 	v2.adjacencies = new Edge[]{ new Edge(v1,  38.65) };
98 	v3.adjacencies = new Edge[]{ new Edge(v1, 102.53),
99 	                             new Edge(v5,  61.44),
100 	                             new Edge(v6,  96.79) };
101 	v4.adjacencies = new Edge[]{ new Edge(v5, 133.04) };
102 	v5.adjacencies = new Edge[]{ new Edge(v0,  81.77),
103 	                             new Edge(v3,  62.05),
104 	                             new Edge(v4, 134.47),
105 	                             new Edge(v6,  91.63) };
106 	v6.adjacencies = new Edge[]{ new Edge(v3,  97.24),
107 	                             new Edge(v5,  87.94) };
108 	Vertex[] vertices = { v0, v1, v2, v3, v4, v5, v6 };
109 
110         computePaths(v0);
111         for (Vertex v : vertices)
112 	{
113 	    System.out.println("Distance to " + v + ": " + v.minDistance);
114 	    List<Vertex> path = getShortestPathTo(v);
115 	    System.out.println("Path: " + path);
116 	}
*/
    	 
	    	 int t;
	 		//output string
	 		String outputFormat = new String("%s\n");
	 		
	 		//initialize reader and writer
	 		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
	 		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
	 		try {
	 			t = Integer.parseInt(bufferReader.readLine());
	 			for(int tc=1;tc<=t;tc++)
				{
	 				String[] nodesEdges = bufferReader.readLine().split("\\s+");
					int nodes = Integer.parseInt(nodesEdges[0]);
					int edges = Integer.parseInt(nodesEdges[1]);
					// create all nodes
					List<Vertex> v=new ArrayList<Vertex>();
					for(int x=1;x<=nodes;x++)
					{
						Vertex vert=new Vertex(x);
						v.add(vert);
						//System.out.println("Vertex created:"+vert.name);
					}
					
					for(int i=0;i<edges;i++)
					{
						String[] input = bufferReader.readLine().split("\\s+");
						//Edge newEdge = new Edge(Integer.parseInt(input[0]) - 1, Integer.parseInt(input[1]) - 1, 1);
						//g.allEdges.add(newEdge);
						//masterList.add(newEdge);
						int v1=Integer.parseInt(input[0]);
						int v2=Integer.parseInt(input[1]);
						int cost=Integer.parseInt(input[2]);
						//System.out.println(v1+" "+v2+" "+cost);
						Vertex vertex1=null,vertex2=null;
						for(Vertex ver:v)
						{
							if(ver.name==v1)
							{
								vertex1=ver;
							}
							if(ver.name==v2)
							{
								vertex2=ver;
							}
							//if(vertex1!=null && vertex2!=null)
								//break;
						}
						//System.out.println("vertex1: "+vertex1.name+"vertex2: "+vertex2.name);
						Edge e1=new Edge(vertex2,cost);
						Edge e2=new Edge(vertex1,cost);
						vertex1.adjacencies.add(e1);
						vertex2.adjacencies.add(e2);
					}
					computePaths(v.get(0));
					System.out.println("Case #"+tc+": "+v.get(nodes-1).minDistance);
					
					if(tc!=t)
						bufferReader.readLine();
					
				}
	 		}
	 		catch (IOException e){
	 			e.printStackTrace();
	 		}
 		

     }
 }
