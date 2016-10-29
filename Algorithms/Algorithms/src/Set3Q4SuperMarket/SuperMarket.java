package Set3Q4SuperMarket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class SuperMarket {
	public static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        //initialize reader and writer
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        //output string
        String outputFormat = new String("%s\n");

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for(int tc=0; tc<t; tc++) {
                String[] nodesAndEdges = bufferReader.readLine().split("\\s+");
                int numOfNodes = Integer.parseInt(nodesAndEdges[0]);
                int numOfEdges = Integer.parseInt(nodesAndEdges[1]);
                int numOfsuperMarkets=Integer.parseInt(nodesAndEdges[2]);
                int leaCity=Integer.parseInt(nodesAndEdges[3])-1;
                int peterCity=Integer.parseInt(nodesAndEdges[4])-1;
                int[][] adjMatrix = new int[numOfNodes][numOfNodes];
                int[] superMrkt=new int[numOfNodes];
               

                for (int i = 0; i < numOfEdges; i++) {
                    String[] input = bufferReader.readLine().split("\\s+");
                    int source = Integer.parseInt(input[0]) - 1;
                    int destination = Integer.parseInt(input[1]) - 1;
                    int wgt = Integer.parseInt(input[2]);
                    if(adjMatrix[source][destination]>wgt || adjMatrix[source][destination]==0)
                    {
                    	adjMatrix[source][destination] = wgt;
                    	adjMatrix[destination][source] = wgt;
                    }
                }
              //  printMatrix(adjMatrix);
               // System.out.println("No of super Markets "+sM.length);
                
                for(int i=0;i<numOfsuperMarkets;i++)
                {
                	 String[] input = bufferReader.readLine().split("\\s+");
                	 int city=Integer.parseInt(input[0])-1;
                	 int Time=Integer.parseInt(input[1]);
                	 if(superMrkt[city]>Time || superMrkt[city]==0)
                		 superMrkt[city]=Time;
                }
                
                if (tc != (t-1)) {
                    bufferReader.readLine();
                }
                if(numOfsuperMarkets==0)
                {
                	bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": impossible"));
                    bufferWriter.flush();
                    continue;
                }
               // System.out.println("Super market Array");
              //  for(int i=0;i<numOfNodes;i++)
               // 	System.out.print(sM[i]+" ");
               // System.out.println();
                //printMatrix(adjMatrix);
                
                int shortestTime=Integer.MAX_VALUE;
                int shortestI=0;
                //System.out.println("IntegerMax: "+Integer.MAX_VALUE);
                Vertex[] distances1 = getShortestPaths(adjMatrix,leaCity);
                Vertex[] distances2 = getShortestPaths(adjMatrix,peterCity);
                if(distances1[peterCity].distance==INFINITY)
                {
                	bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": impossible"));
                    bufferWriter.flush();
                    continue;
                }
                
                boolean impossible=true;
                for(int i=0;i<superMrkt.length;i++)
                {
                	if(superMrkt[i]!=0)
                	{
                		if(distances1[i].distance==INFINITY || distances2[i].distance==INFINITY)
                			continue;
                		
                			int cost=distances1[i].distance+distances2[i].distance+superMrkt[i];
                			impossible=false;
                			if(cost<shortestTime)
                			{
                				shortestTime=cost;
                				shortestI=i;
                			}
                		
                	}
                	
                }
               // System.out.println("Distance1 "+distances1[shortestI].distance+" Distance 2 "+distances2[shortestI].distance+" Weight "+sM[shortestI]);
                /*for(int i=0;i<sM.length;i++)
                {
                
                	
                	// find shortestpath from each super market to destination
                	
                	
                	if(sM[i]!=0)
                	{
                		int distance1=distances1[i].distance;
                		Node[] distances2 = getShortestPaths(adjMatrix,i);
                		if(distances2[dest].distance==Integer.MAX_VALUE)
                		{
                			impossible=true;
                            //continue;
                		}
                		else
                		{
                			impossible=false;
                		int distance2=distances2[dest].distance;
                		if((distance1+distance2+sM[i])<shortestTime)
                			shortestTime=distance1+distance2+sM[i];
                		}
                	}
                	
                } */

                if(impossible==true)
                {
                	bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": impossible"));
                    bufferWriter.flush();
                }
                else
                {
                	int hrs=shortestTime/60;
                	int min=shortestTime-(hrs*60);
                	if(min>=10)
                	{
                		bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": " + hrs+":"+min));
                		bufferWriter.flush();
                	}
                	else if(min<10)
                	{
                		bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": " + hrs+":0"+min));
                		bufferWriter.flush();
                	}
                }

               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vertex[] getShortestPaths(int[][]adjMatrix,int src) {
        int numOfNodes = adjMatrix.length;
        PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
        Vertex[] nodes = new Vertex[numOfNodes];
        for (int i=0; i<numOfNodes; i++) {
            nodes[i] = new Vertex(i, INFINITY);
        }

        nodes[src].distance = 0;
        queue.add(nodes[src]);

        while (!queue.isEmpty()) {
        	Vertex n = queue.poll();

            int nodeIndex = n.node;
            for (int i=0; i<numOfNodes; i++) {
                if (adjMatrix[nodeIndex][i] != 0) {
                    if (nodes[i].distance > (nodes[nodeIndex].distance + adjMatrix[nodeIndex][i])) {
                        nodes[i].distance =  nodes[nodeIndex].distance + adjMatrix[nodeIndex][i];
                        queue.add(nodes[i]);
                    }
                }
            }
        }
        return nodes;
    }
    
    public static void printMatrix(int[][] adjMatrix)
    {
    	System.out.println("Aadj Matrix is");
    	for(int i=0;i<adjMatrix.length;i++)
    	{
    		for(int j=0;j<adjMatrix.length;j++)
    		{
    			System.out.print(adjMatrix[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
}
