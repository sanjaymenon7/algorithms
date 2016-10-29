package Set1;

import java.util.Scanner;

public class NewChat {
	
	public static void main(String[] args) {
		// create scanner object
		Scanner s = new Scanner(System.in);
		// loop over all test cases
		int t = s.nextInt();
		String inputvalue[] = new String[t];
		s.nextLine();
		for (int i = 0; i < t; i++) {
			inputvalue[i] = s.nextLine();
		}
		
		for (int i = 0; i < t; i++) {
			String[] inputstr= inputvalue[i].split("#", 2);
			int x =Integer.parseInt(inputstr[0]);
			String msg= inputstr[1];
			String partone = msg.substring(0,x);
			String parttwo = msg.substring(x, msg.length());
			String revmsg= parttwo.concat(partone);
			System.out.println("Case #" + (i+1) + ": " + revmsg);
		}

		s.close();
	}

}