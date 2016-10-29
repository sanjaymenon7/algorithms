package SnakeNladder;
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
import java.util.LinkedList;
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
					int snakes = Integer.parseInt(nodesEdges[1]);
					int ladders= Integer.parseInt(nodesEdges[2]);
					// create all nodes
					
					int[] snakesnladders=new int[nodes];
					int[] snakesList=new int[nodes];
					int[] laddersList=new int[nodes];
					
					
					for(int i=0;i<snakes;i++)
					{
						String[] input = bufferReader.readLine().split("\\s+");
						//Edge newEdge = new Edge(Integer.parseInt(input[0]) - 1, Integer.parseInt(input[1]) - 1, 1);
						//g.allEdges.add(newEdge);
						//masterList.add(newEdge);
						int src=Integer.parseInt(input[0])-1;
						int dest=Integer.parseInt(input[1])-1;
						snakesList[src]=dest;
						snakesnladders[src]=dest;
						
					}
					for(int i=0;i<ladders;i++)
					{
						String[] input = bufferReader.readLine().split("\\s+");

						int src=Integer.parseInt(input[0])-1;
						int dest=Integer.parseInt(input[1])-1;
						snakesnladders[src]=dest;
						laddersList[src]=dest;
					}
					
					//create graph for each dice value and compute dijkstra
					//int minSteps=Integer.MAX_VALUE;
					int minDiceNumbers[]=new int[6];
					for(int i=1;i<=6;i++)
					{
						List<Vertex> v=new ArrayList<Vertex>();
						for(int x=0;x<nodes;x++)
						{
							Vertex vert=new Vertex(x+1);
							v.add(vert);
							//System.out.println("Vertex created:"+vert.name);
						}
						
						for(int j=0;j<nodes;j++)
						{
							
							if(snakesnladders[j]!=0)
							{
								Edge e=new Edge(v.get(snakesnladders[j]),0);
								v.get(j).adjacencies.add(e);
								if(snakesList[j]!=0)
									continue;
							
							}
							int destIndex=j+i;
							if(destIndex>(nodes-1))
								destIndex=nodes-1;
							Edge e=new Edge(v.get(destIndex),1);
							v.get(j).adjacencies.add(e);
							
							/*if(snakesList[j]!=0)
							{
								// it is a snake
								if(j!=0)
									continue;
								else
								{
									Edge e=new Edge(v.get(snakesList[0]),1);
									v.get(j).adjacencies.add(e);
								}
								
							}
							if(j==0 && laddersList[j]!=0 )
							{
								// ladder at 1st location 
								Edge e=new Edge(v.get(laddersList[0]),1);
								v.get(j).adjacencies.add(e);
							}
							
							int destIndex=j+i;
							if(destIndex>(nodes-1))
								destIndex=nodes-1;
							
							if(destIndex==nodes-1 && snakesList[destIndex]!=0)
							{
								//snake at the last node
								Edge e=new Edge(v.get(destIndex),1);
								v.get(j).adjacencies.add(e);
								continue;
							}
							if(snakesnladders[destIndex]!=0)
							{
								int dest =destIndex;
								while(snakesList[dest]!=0)
								{
									//int prev=dest;
									dest=snakesnladders[dest];
									if(dest==destIndex)
										break;
								}
								//rest of the cases
								Edge e=new Edge(v.get(snakesnladders[dest]),1);
								v.get(j).adjacencies.add(e);
								if(laddersList[destIndex]!=0)
								{
									//its a ladder
									Edge ed=new Edge(v.get(destIndex),1);
									v.get(j).adjacencies.add(ed);
								}
							}
							else
							{
								Edge e=new Edge(v.get(destIndex),1);
								v.get(j).adjacencies.add(e);
							} */	
						}
						
						// print graph
				/*		System.out.println("Graph");
						for(Vertex ver:v)
						{
							System.out.println("Name: "+ver.name+"Incidence");
							for(Edge ed:ver.adjacencies)
							{
								System.out.println(ed.target+" ");
							}
							System.out.println();
						}   */
						// call dijkstra
						computePaths(v.get(0));

							int minSteps=v.get(nodes-1).minDistance;
							minDiceNumbers[i-1]=(minSteps);
						
						
					}
					//print the min list
				//	System.out.println("Dice minimum list");
					
					//for(int i=0;i<6;i++)
						//System.out.print(minDiceNumbers[i]+" ");
						
					System.out.println("Case #"+tc+": "+getOutput(minDiceNumbers));
					
					if(tc!=t)
						bufferReader.readLine();
					
				}
	 		}
	 		catch (IOException e){
	 			e.printStackTrace();
	 		}
 		

     }
     public static String getOutput(int[] results) {
    	    LinkedList<Integer> maxList = new LinkedList<Integer>();
    	    int minTries=Integer.MAX_VALUE - 1;
    	    for (int i=0; i<6; i++) {
    	        if ((results[i]>=0) && (results[i] <= minTries)) {
    	            if (results[i] < minTries) {
    	                maxList.clear();
    	                minTries = results[i];
    	            }
    	            maxList.add(i+1);
    	        }
    	    }
    	    if (maxList.isEmpty()) {
    	        return "impossible";
    	    } else {
    	        StringBuilder builder = new StringBuilder("");
    	        for (int element: maxList) {
    	            builder.append(element);
    	            builder.append(" ");
    	        }
    	        return builder.toString().replaceFirst("\\s+$", "");
    	    }
    	}
 }
