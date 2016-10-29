package Set2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pizza {

	public static void main(String[] args) {
		
		// create scanner object
		Scanner s = new Scanner(System.in);
		// loop over all test cases
		int t = s.nextInt();
		
		s.nextLine();
		for (int i = 0; i < t; i++) {
			int NoOfToppings = s.nextInt();
			int badCombinations = s.nextInt();
			int[][] toppingCombinations =new int[NoOfToppings+1][NoOfToppings+1];
			for (int j = 0; j < badCombinations; j++) {
				int topping1 = s.nextInt();
				int topping2 = s.nextInt();
				toppingCombinations[topping1][topping2] =1;
				toppingCombinations[topping2][topping1] =1;
				
			}
			for (int row = 0; row < NoOfToppings+1; row++) {
				//if (row == column){
					toppingCombinations[row][row] =1;
					toppingCombinations[0][row] =1;
					toppingCombinations[row][0] =1;
				//}
			}
			/*for (int row = 0; row < NoOfToppings+1; row++) {
				for (int column = 0; column < NoOfToppings+1; column++) {
					if(row == 0 || column ==0){
						toppingCombinations[row][column] =1;
					}
					if (row == column){
						toppingCombinations[row][column] =1;
					}
				}
			}*/
			int result;
			boolean flag = false;
			List<int[]> toppingArray = Arrays.asList(toppingCombinations);
			for (int[] temp : toppingArray) {
				Arrays.sort(temp);
				result = Arrays.binarySearch(temp,0);
				if(result>=0){
					flag =true;
					break;
				}
			}
			if (flag == true){
				System.out.println("Case #"+(i+1)+": yes");
			}
			else if(flag == false){
				if (NoOfToppings == 2){
					System.out.println("Case #"+(i+1)+": yes");
				}
				else{
					System.out.println("Case #"+(i+1)+": no");
				}
			}
			
		
		}
		
		s.close();
	}
}
