package goldbach;

import java.io.*;
import java.util.Arrays;

/**
 * Created by sreenath on 18.06.15.
 */
public class Main {

    public static void main(String[] args) {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int t = Integer.parseInt(bufferReader.readLine());
            int[] input = new int[t];
            int highestInput = 0;

            for (int tc=0; tc<t; tc++) {
                int N = Integer.parseInt(bufferReader.readLine());
                input[tc] = N;
                if (highestInput < N) {
                    highestInput = N;
                }
            }

            boolean[] isNotPrime = new boolean[highestInput];
            isNotPrime[0] = true;
            isNotPrime[1] = true;

            // determine primes < N using Sieve of Eratosthenes
            for (int i = 2; i * i < highestInput; i++) {
                if (!isNotPrime[i]) {
                    for (int j = i; i * j < highestInput; j++) {
                        isNotPrime[i * j] = true;
                    }
                }
            }

            int[] result;
            for (int i=0; i<t; i++) {
                int N = input[i];
                if (N % 2 == 0) {
                    result = getSum(N, isNotPrime);
                    bufferWriter.write("Case #" + (i+1) + ": " + result[0] + " " + result[1]);
                } else {
                    if (!isNotPrime[N - 4]) {
                        result = new int[]{2, 2, N - 4};
                    } else {
                        int[] tempResult = getSum(N - 3, isNotPrime);
                        result = new int[3];
                        result[0] = tempResult[0];
                        result[1] = tempResult[1];
                        result[2] = 3;
                        Arrays.sort(result);
                    }
                    bufferWriter.write("Case #" + (i+1) + ": " + result[0] + " " + result[1] + " " + result[2]);
                }
                bufferWriter.write("\n");
                bufferWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] getSum(int N, boolean[] isNotPrime) {

        int numOfPrimes = 0;
        for (int i = 2; i < N; i++) {
            if (!isNotPrime[i]) {
                numOfPrimes++;
            }
        }

        int[] primesList = new int[numOfPrimes];
        int i=0, j=0;
        while (i<numOfPrimes) {
            if (!isNotPrime[j]) {
                primesList[i] = j;
                i++;
            }
            j++;
        }
        
        // check if N can be expressed as sum of two primes
        int left = 0, right = numOfPrimes-1;
        while (left <= right) {
            if (primesList[left] + primesList[right] == N) {
                break;
            } else if (primesList[left] + primesList[right]  < N) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] {primesList[left], primesList[right]};
    }

}
