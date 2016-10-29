package ShivBellFord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class PartyPlanning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int t;
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		try {
 			t = Integer.parseInt(bufferReader.readLine());
 			for(int tc=1;tc<=t;tc++)
			{
 				
 				int N = Integer.parseInt(bufferReader.readLine());
 				//d=new ArrayList<Integer>();
				List<Edge> edges=new ArrayList<Edge>();
				
				for(int i=0;i<N;i++)
				{
					String[] input = bufferReader.readLine().split("\\s+");
					int cost=Integer.parseInt(input[0]);
					if(i==(N-1))
					{
						edges.add(new Edge(N-1,N,cost));
						break;
					}
					int noEdges=Integer.parseInt(input[1]);
					for(int j=2;j<input.length;j++)
					{
						int dest=Integer.parseInt(input[j]);
						edges.add(new Edge(i,dest-1,cost));
						//M++;
					}
					
				}
				
				if(tc!=t)
					bufferReader.readLine();
				
				BellmanFord b=new BellmanFord(N+1,edges);
				int MaxCost=b.algo(0);
				System.out.println("Case #"+tc+": "+(MaxCost));			
                }
            }
  		
 		catch (IOException ex){
 			ex.printStackTrace();
 		}
	

	}

}
