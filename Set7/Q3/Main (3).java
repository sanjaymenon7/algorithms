package rpg;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by sreenath on 11.06.15.
 */
public class Main {

    public static void main(String[] args) {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for (int tc=0; tc<t; tc++) {

                String[] inputArray = bufferReader.readLine().split("\\s+");
                int minScore = Integer.parseInt(inputArray[0]);
                String[] dices = inputArray[1].split("\\+");
                ArrayList<Integer> dieFaces = new ArrayList<Integer>();
                BigInteger totalOutComes = BigInteger.ONE;
                int maxScore = 0;

                for (String dice : dices) {
                    String[] oneDie = dice.split("d");
                    int num = Integer.parseInt(oneDie[0]);
                    int face = Integer.parseInt(oneDie[1]);
                    for (int i = 0; i < num; i++) {
                        dieFaces.add(face);
                        totalOutComes = totalOutComes.multiply(BigInteger.valueOf(face));
                        maxScore = maxScore + face;
                    }
                }

                BigInteger favOutcomes = findNumberOfWays(maxScore, dieFaces, minScore);
                BigInteger gcd = totalOutComes.gcd(favOutcomes);
                BigInteger numrtr = favOutcomes.divide(gcd);
                BigInteger denomtr = totalOutComes.divide(gcd);

                bufferWriter.write("Case #" + (tc+1) + ": " + numrtr + "/" + denomtr);
                bufferWriter.write("\n");
                bufferWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Parts of the code taken from http://www.geeksforgeeks.org/dice-throw-problem/
    public static BigInteger findNumberOfWays(int maxScore, ArrayList<Integer> dices, int minScore) {
        int n = dices.size();
        // Create a table to store results of subproblems.  One extra
        // row and column are used for simpilicity (Number of dice
        // is directly used as row index and sum is directly used
        // as column index).  The entries in 0th row and 0th column
        // are never used.
        BigInteger[][] table = new BigInteger[n + 1][maxScore + 1];
        // memset(table, 0, sizeof(table)); // Initialize all entries as 0

        // Table entries for only one dice
        for (int j = 1; j <= dices.get(0); j++) {
            if (j <= maxScore) {
                table[1][j] = BigInteger.ONE;
            } else {
                table[1][j] = BigInteger.ZERO;
            }
        }

        // Fill rest of the entries in table using recursive relation
        // i: number of dice, j: sum
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= maxScore; j++) {
                for (int k = 1; k <= dices.get(i-1) && k < j; k++) {
                    if (table[i - 1][j - k] == null) {
                        table[i-1][j-k] = BigInteger.ZERO;
                    }

                    if (table[i][j] == null){
                        table[i][j] = table[i - 1][j - k];
                    } else {
                        table[i][j] = table[i][j].add(table[i - 1][j - k]);
                    }
                }
            }
        }

        BigInteger[] finalArray = table[n];
        BigInteger totalCount = BigInteger.ZERO;
        for (int i=minScore; i<=maxScore; i++) {
            if (finalArray[i] != null) {
                totalCount = totalCount.add(finalArray[i]);
            }
        }
        return totalCount;
    }
}
