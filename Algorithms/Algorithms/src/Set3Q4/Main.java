package Set3Q4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main {

	 public static final int INFINITY = 9999;

	    public static void main(String[] args) {
	        //initialize reader and writer
	        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
	        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

	        //output string
	        String outputFormat = new String("%s\n");

	        try {
	            int t = Integer.parseInt(bufferReader.readLine());

	            for(int tc=0; tc<t; tc++) {
	            	int shortestDistance =INFINITY;
	                String[] routeDetails = bufferReader.readLine().split("\\s+");
	                int numOfCities = Integer.parseInt(routeDetails[0]);
	                int numOfRoads = Integer.parseInt(routeDetails[1]);
	                int numOfSuperMrkt = Integer.parseInt(routeDetails[2]);
	                if (numOfSuperMrkt ==0 || numOfRoads ==0){
	                	bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": impossible"));
	                	 bufferWriter.flush();
	                	break;
	                }
		        	int leaCity = Integer.parseInt(routeDetails[3]);
		        	int peterCity = Integer.parseInt(routeDetails[4]);
	                int[][] adjMatrix = new int[numOfCities][numOfCities];

	                for (int i = 0; i < numOfRoads; i++) {
	                    String[] roadDetails = bufferReader.readLine().split("\\s+");
	                    int source = Integer.parseInt(roadDetails[0]) - 1;
	                    int destination = Integer.parseInt(roadDetails[1]) - 1;
	                    int wgt = Integer.parseInt(roadDetails[2]);

	                    adjMatrix[source][destination] = wgt;
	                    adjMatrix[destination][source] = wgt;
	                }
	                
	                	//Node[] distances = getShortestPaths(adjMatrix,leaCity);
	                	//int shortestDistancebetweencities = distances[peterCity-1].distance;
	                	for (int k = 0; k < numOfSuperMrkt; k++) {
	                	String[] sprMrktDetails = bufferReader.readLine().split("\\s+");
	                	int city = Integer.parseInt(sprMrktDetails[0]) - 1;
	                	int time = Integer.parseInt(sprMrktDetails[1]) ;
	                	
	                	for (int j = 0; j < adjMatrix.length; j++) {
							adjMatrix[city][j] = adjMatrix[city][j]+time;
						}
	                	 Node[] distancesWithSuprMrkt = getShortestPaths(adjMatrix,city);
	 	                 int shortestDistancetoPeter = distancesWithSuprMrkt[peterCity-1].distance;
	 	                 int shortestDistancetoLea = distancesWithSuprMrkt[leaCity-1].distance;
	 	                 int currentShortestDistance = shortestDistancetoPeter+shortestDistancetoLea;
	 	                 if(currentShortestDistance<shortestDistance)
	 	                	 shortestDistance=currentShortestDistance;
	 	                for (int j = 0; j < adjMatrix.length; j++) {
							adjMatrix[city][j] = adjMatrix[city][j]-time;
						}
					}
	                if(shortestDistance< INFINITY){
	                	String hoursStr = Integer.toString(shortestDistance / 60);
	                	String minStr;
	                	int mins =  shortestDistance% 60;
	                	if (mins<10){
	                		 minStr = "0"+Integer.toString(mins);
	                	}
	                	else
	                	  minStr = Integer.toString(mins);
	                	
	                	bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": " +hoursStr+":"+minStr));
	                }
	                else
	                	bufferWriter.write(String.format(outputFormat, "Case #" + (tc+1) + ": impossible"));
	                bufferWriter.flush();

	                if (tc != t) {
	                    bufferReader.readLine();
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static Node[] getShortestPaths(int[][]adjMatrix, int source) {
	        int numOfNodes = adjMatrix.length;
	        PriorityQueue<Node> queue = new PriorityQueue<Node>();
	        Node[] nodes = new Node[numOfNodes];
	        for (int i=0; i<numOfNodes; i++) {
	            nodes[i] = new Node(i, INFINITY);
	        }

	        nodes[source].distance = 0;
	        queue.add(nodes[source]);

	        while (!queue.isEmpty()) {
	            Node n = queue.poll();

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
}
