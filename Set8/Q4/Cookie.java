package cookies;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by sreenath on 19.06.15.
 */
public class Cookie {

    public static void main(String[] args) {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int tc = Integer.parseInt(bufferReader.readLine());

            for (int t=1; t<=tc; t++) {
                int numOfFriends = Integer.parseInt(bufferReader.readLine());
                String[] inputString = bufferReader.readLine().split("\\s+");

                int[] cookieNumbers = new int[numOfFriends];
                int maxSum = 0;
                for (int i=0; i<numOfFriends; i++) {
                    cookieNumbers[i] = Integer.parseInt(inputString[i]);
                    maxSum = maxSum + cookieNumbers[i];
                }

                boolean[] sumCombinations = findSumCombinations(cookieNumbers, maxSum);
                BigInteger totalLCM = BigInteger.ONE;
                for (int i=1; i<=maxSum; i++) {
                    if (sumCombinations[i]) {
                        totalLCM = findLCM(totalLCM, i+1);
                    }
                }

                totalLCM = totalLCM.mod(BigInteger.valueOf(Integer.MAX_VALUE));
                bufferWriter.write("Case #" + t + ": " + totalLCM);
                bufferWriter.write("\n");
                bufferWriter.flush();
                if (t!=tc) {
                    bufferReader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BigInteger findLCM(BigInteger aBig, int b) {
        BigInteger bBig = BigInteger.valueOf(b);
        BigInteger gcd = aBig.gcd(bBig);
        BigInteger product = aBig.multiply(bBig);
        return product.divide(gcd);
    }

    private static boolean[] findSumCombinations(int[] a, int maxSum) {
        int n = a.length;
        boolean hash[] = new boolean[maxSum + 1];
        hash[0]=true;

        //Iterate through the entire array
        for(int i=0; i<n; i++){
        //When i-th element is included in sum,minimum (sum)=a[i],maximum(sum)=SUM
            for (int j = maxSum; j >= a[i]; j--) {
                //Now,if sum=j-a[i],is a possible sum value then j would also be a possible value,just add a[i]
                if (hash[j - a[i]] == true) {
                    hash[j] = true;
                }
            }
        }
        return hash;
    }
}
