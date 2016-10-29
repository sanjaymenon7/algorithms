package softskills;

import java.io.*;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sreenath on 22.06.15.
 */
public class Main {

    public static void main(String[] args) {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for(int tc=0; tc<t; tc++) {
                String[] inputString = bufferReader.readLine().split("\\s+");
                int n = Integer.parseInt(inputString[0]);
                int k = Integer.parseInt(inputString[1]);

                int[] primes = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23};
                ChieneseRemainder chineseRemainder = new ChieneseRemainder();
                for (int i=0; i<primes.length; i++) {
                    List<Integer> nsList = findBaseNumbers(n, primes[i]);
                    List<Integer> ksList = findBaseNumbers(k, primes[i]);

                    BigInteger remainderVal = BigInteger.ONE;
                    for (int j=0; j<nsList.size(); j++) {
                        Integer nsDigit = nsList.get(j);
                        if (ksList.size() > j) {
                            int ksDigit = ksList.get(j);
                            remainderVal = remainderVal.multiply(BigInteger.valueOf(findCombinations(nsDigit, ksDigit)));
                        }
                    }
                    remainderVal = remainderVal.mod(BigInteger.valueOf(primes[i]));
                    Modulo modulo = new Modulo(primes[i], remainderVal.intValue());
                    chineseRemainder.moduloInputs.add(modulo);
                }

                chineseRemainder.extendedEuclideanAlgorithm();

                bufferWriter.write("Case #" + (tc+1) + ": " + chineseRemainder.solution);
                bufferWriter.write("\n");
                bufferWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List findBaseNumbers(int num, int base) {
        LinkedList<Integer> digits = new LinkedList<Integer>();
        while(num > 0) {
            int digit = num % base;
            digits.add(digit);
            num = num / base;
        }
        return digits;
    }

    private static int findCombinations(int N, int K) {
        if (N < K) {
            return 0;
        }
        int ret = 1;
        for (int k = 0; k < K; k++) {
            ret = (ret * (N-k)) / (k+1);
        }
        return ret;
    }
}
