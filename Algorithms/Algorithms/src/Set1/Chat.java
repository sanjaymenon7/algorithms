package Set1;

import java.util.Scanner;

public class Chat {
	public static void main(String[] args) {
		// create scanner object
		Scanner s = new Scanner(System.in);
		// loop over all test cases
		int t = s.nextInt();
		s.nextLine();
		//if (t >= 1 && t <= 100000) {
			for (int i = 1; i <= t; i++) {
				// read several types of input
				String str = s.nextLine();
					// output : use the possibility you like more
					String[] inputstr= str.split("#", 2);
					int x =Integer.parseInt(inputstr[0]);
					String msg= inputstr[1];
					if(msg.length()>=2 && msg.length()<=160){
						//if(x>=1 && x< msg.length()){
							String partone = msg.substring(0,x);
							String parttwo = msg.substring(x, msg.length());
							String revmsg= parttwo.concat(partone);
							System.out.println("Case #" + i + ": " + revmsg);
						//}
					}

			}

		//}

		s.close();
	}
}
