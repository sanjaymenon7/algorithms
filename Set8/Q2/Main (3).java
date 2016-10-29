package commander;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

/**
 * Created by sreenath on 18.06.15.
 */
public class Main {

    public static void main(String[] args) {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for (int tc=1; tc<=t; tc++) {
                int numOfArmies = Integer.parseInt(bufferReader.readLine());
                String[] inputString = bufferReader.readLine().split("\\s+");

                BigInteger gcd = BigInteger.valueOf(Integer.parseInt(inputString[0]));
                for (int i=1; i<numOfArmies; i++) {
                    BigInteger army = BigInteger.valueOf(Integer.parseInt(inputString[i]));
                    gcd = gcd.gcd(army);
                }

                bufferWriter.write("Case #" + tc + ": " + gcd);
                bufferWriter.write("\n");
                bufferWriter.flush();
                if (t != tc) {
                    bufferReader.readLine();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
