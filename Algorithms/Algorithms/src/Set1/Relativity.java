package Set1;
import java.util.Scanner;

public class Relativity {

	public static void main(String[] args) {
		long  e =0;
		long c = 299792458;
		// create scanner object
		Scanner s = new Scanner(System.in);
		// loop over all test cases
		int n = s.nextInt();
		if (n>=1 && n<=1000){
			for (int i = 1; i <= n; i++) {
				// read several types of input
				int m = s.nextInt();
				if(m>=1 && m<=100){
					e = m*c*c;
					// output : use the possibility you like more
					System.out.println("Case  #" + i + ": " +e);
				}
				e=0;
			}
			
		}
		
		s.close();
	}
}
