package Set3Q2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.Vector;

// Code modified from http://www.sanfoundry.com/java-program-find-longest-path-dag/

public class LongestPathinDAG {

	int    n;                      // number of nodes
    int    target;                 // destination node
    int    minLength;              // the minimal length of each path
    Node[] v;                      // used to store Nodes
    Edge[] e;                      // used to store Edges
    int[]  path;                   // used to store temporary path
    int    length       = 0;       // length of the path
    int    distance     = 0;       // distance of the path
    int[]  bestPath;               // used to store temporary path
    int    bestLength   = 0;       // length of the longest path
    int    bestDistance = -1000000; // distance of the longest path
    int[]  visited;                // used to mark a node as visited if set as
                                    // 1
    int sinkNodeWeight =0;
    public LongestPathinDAG() throws NumberFormatException, IOException
    {	
    	BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		
        //Scanner sc = new Scanner(System.in);
        int t = Integer.parseInt(bufferReader.readLine());
        for (int testcase = 1; testcase <=t; testcase++) {
        	int edgeCounter =0;
			int numOfNodes = Integer.parseInt(bufferReader.readLine());
			v = new Node[numOfNodes];
	        e = new Edge[numOfNodes*numOfNodes];
	       // for (int i = 0; i < numOfNodes; i++)
	       //     v[i] = new Node(i);
			for (int node = 0; node < numOfNodes; node++) {
				String[] nodeDetails = bufferReader.readLine().split("\\s+");
				int weight =Integer.parseInt(nodeDetails[0]);
				int numOfNeighbours = Integer.parseInt(nodeDetails[1]);
				for (int neighbour = 2; neighbour < nodeDetails.length; neighbour++) {
					
					Edge edge = new Edge(edgeCounter);
					int adjNode = Integer.parseInt(nodeDetails[neighbour]);
		            edge.start = node;
		            edge.end = adjNode -1;
		            edge.capacity = weight;
		            edge.flow = 0;
		            e[edgeCounter] = edge;
		            if(v[node] == null){
		            	v[node] =new Node(node);
		            	v[node].fors.add(edgeCounter);
		            }
		            else
		            	v[node].fors.add(edgeCounter);
		            if(v[adjNode-1] == null){
		            	v[adjNode-1] =new Node(adjNode-1);
		            	v[adjNode-1].backs.add(edgeCounter);
		            }
		            else
		            	v[adjNode-1].backs.add(edgeCounter);
		            edgeCounter++;
				}
				
				if (numOfNeighbours ==0)
					sinkNodeWeight = weight;
				
			}
			visited = new int[v.length];
	        path = new int[v.length];
	        bestPath = new int[v.length];
	        if (findLongestPath(0, numOfNodes - 1, 1)){
	        	bestDistance = bestDistance +sinkNodeWeight;
	            System.out.println("Case #"+testcase+": "+ bestDistance);
	            }
	        else
	            System.out.println("No path from v0 to v" + (numOfNodes - 1));
	        if(testcase  != t)
				bufferReader.readLine();
		}
        //sc.close();
        
    }
 
    /*
     * this function looks for a longest path starting from being to end,
     * using the backtrack depth-first search.
     */
    public boolean findLongestPath(int begin, int end, int minLen)
    {
        /*
         * compute a longest path from begin to end
         */
        target = end;
        bestDistance = -100000000;
        minLength = minLen;
        dfsLongestPath(begin);
        if (bestDistance == -100000000)
            return false;
        else
            return true;
    }
 
    private void dfsLongestPath(int current)
    {
        visited[current] = 1;
        path[length++] = current;
        if (current == target && length >= minLength)
        {
            if (distance > bestDistance)
            {
                for (int i = 0; i < length; i++)
                    bestPath[i] = path[i];
                bestLength = length;
                bestDistance = distance;
            }
        }
        else
        {
            Vector<Integer> fors = v[current].fors;
            for (int i = 0; i < fors.size(); i++)
            {
                Integer edge_obj = (Integer) fors.elementAt(i);
                int edge = edge_obj.intValue();
                if (visited[e[edge].end] == 0)
                {
                    distance += e[edge].capacity;
                    dfsLongestPath(e[edge].end);
                    distance -= e[edge].capacity;
                }
            }
        }
        visited[current] = 0;
        length--;
    }
 
    public String toString()
    {
        String output = "v" + bestPath[0];
        for (int i = 1; i < bestLength; i++)
            output = output + " -> v" + bestPath[i];
        return output;
    }
 
    public static void main(String arg[])
    {
        try {
			LongestPathinDAG lp = new LongestPathinDAG();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /*
         * find a longest path from vertex 0 to vertex n-1.
         */
        /*if (lp.findLongestPath(0, lp.n - 1, 1))
            System.out.println("Longest Path is " + lp
                    + " and the distance is " + lp.bestDistance);
        else
            System.out.println("No path from v0 to v" + (lp.n - 1));*/
       
    }
}
