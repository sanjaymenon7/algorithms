package Set1;

import java.util.Arrays;
import java.util.Scanner;

public class Chess {

	public static void main(String[] args) {
		int[][] teams;
		// create scanner object
		Scanner s = new Scanner(System.in);
		int t = s.nextInt();
		//loop over each test case
		//if (t >= 1 && t <= 20) {// test case number check
			for (int k = 0; k < t; k++) {
				int n = s.nextInt();
				//if (n >= 2 && n <= 1000) {// number of teams check
					teams = new int[n][5];
					//getting all the skill values into a 2D array
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < 5; j++) {
							teams[i][j] = s.nextInt();
						}
					}
					// Checking all skill values are within the constraint
					/*int count = 0;
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < 5; j++) {
							if (teams[i][j] >= 1 && teams[i][j] <= 1000){
								
							}
							else
							{
								count++;
								break;
							}
						}
					}*/
					//if (count == 0){
						//sorting each row
						for (int row = 0; row < teams.length; row++) {
							for (int column = 0; column < teams[0].length; column++) {
								for (int columnTwo = column + 1; columnTwo < teams[0].length; columnTwo++) {
									if (teams[row][columnTwo] > teams[row][column])
									{
										int temp = teams[row][column];
										teams[row][column] = teams[row][columnTwo];
										teams[row][columnTwo] = temp;
									}

								}

							}

						}
						//re-arranging the rows according to the skill values.
						for(int row =0 ; row < teams.length; row++){
							for (int rowTow = row +1; rowTow < teams.length; rowTow++) {
								for (int column = 0; column < 5; column++) {
									if (teams[rowTow][column]>teams[row][column]){
										for (int i = 0; i < 5; i++) {
											int temp = teams[row][i];
											teams[row][i] = teams[rowTow][i];
											teams[rowTow][i] = temp;
										}
										break;
									}
								}
							}
						}
						//Printing the result
						int no = k+1;
						System.out.println("Case #"+no+":");
						for (int i = 0; i < n; i++) {
							for (int j = 0; j < 5; j++) {
								if(j !=4)
									System.out.print(teams[i][j]+" ");
								else
									System.out.print(teams[i][j]);
							}
							System.out.print("\n");
						}
						
					//}
					//else{
					//	break;
					//}


				//}
				//else{
				//	break;
				//}
				
			}


		//}

		s.close();
	}

}