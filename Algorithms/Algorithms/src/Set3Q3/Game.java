package Set3Q3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Game {
	public static final int INFINITY = Integer.MAX_VALUE;
	
	public static void main(String[] args) {
		
		 //initialize reader and writer
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        
      //output string
        String outputFormat = new String("%s\n");
        
        try {
        	
        	int t = Integer.parseInt(bufferReader.readLine());
        	for(int testcase=0; testcase<t; testcase++) {
        		
        		String[] boardDetails = bufferReader.readLine().split("\\s+");
        		int numOfNodes = Integer.parseInt(boardDetails[0]);
        		int numOfSnakes = Integer.parseInt(boardDetails[1]);
        		int numOfLadders = Integer.parseInt(boardDetails[2]);
        		List<Integer>snakeHead =new ArrayList<Integer>();
        		List<Integer>snakeTail =new ArrayList<Integer>();
        		List<Integer>ladderTop =new ArrayList<Integer>();
        		List<Integer>ladderBottom =new ArrayList<Integer>();
        		int[][] ladders =new int[numOfLadders][2];
        		for (int snake = 0; snake < numOfSnakes; snake++) {
        			String[] snakeDetails = bufferReader.readLine().split("\\s+");
        			snakeHead.add(Integer.parseInt(snakeDetails[0])) ;
        			snakeTail.add(Integer.parseInt(snakeDetails[1])) ;

				}
        		
        		for (int ladder = 0; ladder < numOfLadders; ladder++) {
        			String[] ladderDetails = bufferReader.readLine().split("\\s+");
        			ladderTop.add(Integer.parseInt(ladderDetails[0])) ;
        			ladderBottom.add(Integer.parseInt(ladderDetails[1]));
					
				}
        		int[][]adjMatrix = new int[numOfNodes][numOfNodes];
        		for (int node = 0; node < numOfNodes; node++) {
					for (int i = 0; i < 6; i++) {
						int snakeIndex=0;
						if(snakeHead.contains(node+i)){
							 snakeIndex = snakeHead.indexOf(node+i);
						}
						int head = snakeHead.get(snakeIndex);
						int tail = snakeTail.get(snakeIndex);
						int ladderIndex=0;
						if(ladderTop.contains(node+i)){
							ladderIndex = ladderTop.indexOf(node+i);
						}
						int top = ladderTop.get(snakeIndex);
						int bottom = ladderBottom.get(snakeIndex);
						
					}
				}
        		
        		
        		if (testcase != t) {
                    bufferReader.readLine();
                }
        	}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
