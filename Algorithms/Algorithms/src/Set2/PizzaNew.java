package Set2;

import java.util.Scanner;

public class PizzaNew {
	
	public static void main(String[] args) {
		// create scanner object
		Scanner s = new Scanner(System.in);
		// Get number of test cases 
		int t = s.nextInt();
		
		for (int k = 0; k < t; k++) {
			
			int NoOfToppings = s.nextInt();
			int badCombinations = s.nextInt();
			int[][] toppingCombinations =new int[NoOfToppings][NoOfToppings];
			
			for (int j = 0; j < badCombinations; j++) {
				int topping1 = s.nextInt();
				int topping2 = s.nextInt();
				toppingCombinations[topping1-1][topping2-1] =1;
				toppingCombinations[topping2-1][topping1-1] =1;
				
			}
			BipartiteBfs bipartite = new BipartiteBfs(NoOfToppings);
			int[] visited = new int[NoOfToppings];

			boolean allVisited = false, isBipartite = false, Isprinted = false;

			int vpointer = 0;
			while (allVisited == false)
			{
				for (int i = 0; i < visited.length; i++)
				{
					if (visited[i] == 0)
					{
						vpointer = i;
						break;
					}
					if (i == visited.length - 1)
						allVisited = true;
				}
				if (bipartite.isBipartite(toppingCombinations, vpointer, visited)){
					isBipartite = true;
					
				}
				else
				{
					System.out.println("Case #" + (k+1) + ": no");
					Isprinted = true;
					break;
				}
			}
			if (isBipartite && !Isprinted)
			{
				System.out.println("Case #" + (k+1) + ": yes");
			}
		}
		
		s.close();
	}

}


