package ShivguruKruskal;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Networking {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		// get number of testcases
		int t = sc.nextInt();
		for (int tc = 1; tc <= t; tc++)
		{
			
			int n = sc.nextInt();
			Graph g=new Graph(n);
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					g.setValueAtPositions(i, j, sc.nextInt());
				}
			}
			
			Kruskal k=new Kruskal();
			List<Edge> spanningTree=k.getMinimumSpanningTree(g, n);
			Collections.sort(spanningTree, new EdgeComparator());
			System.out.println("Case #"+tc+":");
			for(Edge edge:spanningTree)
			{
				/*the person number is always plus 1 of the array index*/
				System.out.println( (edge.start + 1)+" "+ (edge.end + 1));
				
			}
			if(tc!=t)
			{
				System.out.println();
			}
			
			
		}
	}

}
