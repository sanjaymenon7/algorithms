package Set1;

import java.util.Scanner;

public class ScrambledChatlog {
	static String chats[];

	public static void main(String[] args) {

		// create scanner object

		Scanner s = new Scanner(System.in);

		// loop over all test cases

		int testcases = s.nextInt();

		chats = new String[testcases];

		s.nextLine();

		for (int i = 0; i < testcases; i++)

		{

			// read input strings

			chats[i] = s.nextLine();

		}

		// split the string based on the number provided

		for (int i = 0; i < testcases; i++)

		{

			String parts[] = chats[i].split("#");

			int splitpos = Integer.parseInt(parts[0]);

			System.out.println("Case #" + (i + 1) + ": "
					+ parts[1].substring(splitpos)
					+ parts[1].substring(0, splitpos));

		}

	}

}
