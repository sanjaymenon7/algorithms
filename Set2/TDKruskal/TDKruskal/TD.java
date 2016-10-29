package TDKruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class TD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int t;
		//output string
		String outputFormat = new String("%s\n");
		
		//initialize reader and writer
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		try {
			t = Integer.parseInt(bufferReader.readLine());
			//Graph[] graphs=new Graph[t];
			for(int tc=1;tc<=t;tc++)
			{
				String[] nodesEdges = bufferReader.readLine().split("\\s+");
				int nodes = Integer.parseInt(nodesEdges[0]);
				int edges = Integer.parseInt(nodesEdges[1]);
				List<Edge> masterList=new LinkedList();
				Graph g=new Graph(nodes);
				
				for(int i=0;i<edges;i++)
				{
					String[] input = bufferReader.readLine().split("\\s+");
					Edge newEdge = new Edge(Integer.parseInt(input[0]) - 1, Integer.parseInt(input[1]) - 1, 1);
					g.allEdges.add(newEdge);
					masterList.add(newEdge);
					
				}
				if(tc  != t)
					bufferReader.readLine();
				int x=0;
				StringBuilder op=new StringBuilder("Case #");
				op.append(tc);
				op.append(":");
				for(int i=0;i<edges;i++)				{
					Edge e = masterList.get(i);
					g.allEdges.remove(e);
					//System.out.println(g.toString());
					Kruskal k=new Kruskal();
					List<Edge> spanningTree=k.getMinimumSpanningTree(g, nodes);
					
					if(k.getStatusMST()==0)
					{
						op.append(" ");
						op.append(i+1);
					}
					g.allEdges.add(e);
				}
				bufferWriter.write(String.format(outputFormat, op));
				bufferWriter.flush();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	}

}
