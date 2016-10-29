package chocolates;

import java.io.*;

public class Main {

    /**
     * Get the minimum CCC values
     *
     * @param maxValue
     * @param alreadyTaken
     * @param previous
     * @param packages
     * @param b
     * @param a
     * @return
     */
    private static int getMinimum(int maxValue, int alreadyTaken, int[] previous, int[] packages, int b, int a) {
        int ccValue = -1;
        for (int ab = alreadyTaken; ab <= maxValue; ab++) {
            int newCcValue = getCcc(ab, maxValue, packages, b, a);
            if (previous[ab - 1] == -1) {
                return ccValue;
            }
            if (newCcValue == -1) {
                continue;
            }
            newCcValue = newCcValue + previous[ab - 1];
            if (ccValue == -1 || newCcValue < ccValue) {
                ccValue = newCcValue;
            }
        }
        return ccValue;
    }

    /**
     * Initialize the current and previous arrays
     *
     * @param previous
     * @param b
     * @param packages
     * @param a
     * @return
     */
    private static boolean initValues(int[] previous, int b, int[] packages, int a) {
        int sum = 0;
        int counter = 0;
        while (counter < previous.length) {
            sum = sum + packages[counter];
            if (sum > b) {
                break;
            }
            previous[counter] = getCcc(sum, a, b);
            counter++;
        }
        if (counter == previous.length) {
            return true;
        }
        while (counter < previous.length) {
            previous[counter] = -1;
            counter++;
        }
        return false;
    }

    private static int getCcc(int x1, int x2, int[] packages, int b, int a) {
        int x = 0;
        for (int i = x1; i <= x2; i++) {
            x = x + packages[i];
            if (x > b) {
                return -1;
            }
        }
        return getCcc(x, a, b);
    }

    private static int getCcc(int x, int a, int b) {
        if (x == 0) {
            return -1;
        } else if (x <= a) {
            return 0;
        } else if (x > b) {
            return -1;
        }
        return (x - a)*(x - a);
    }

    public static void main(String[] args) {

        // initialize reader and writer
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int tc = Integer.parseInt(bufferReader.readLine());
            String[] result = new String[tc];

            for (int t = 0; t < tc; t++) {
                String[] inputValues = bufferReader.readLine().split(" ");
                int chocolates = Integer.parseInt(inputValues[0]);
                int a = Integer.parseInt(String.valueOf(inputValues[1]));
                int b = Integer.parseInt(inputValues[2]);
                inputValues = bufferReader.readLine().split(" ");
                int[] packages = new int[inputValues.length];

                boolean isInputOK = true;
                for (int i = 0; i < packages.length; i++) {
                    packages[i] = Integer.parseInt(inputValues[i]);
                    if (packages[i] > b) {
                        isInputOK = false;
                        result[t] = "impossible";
                        if (t + 1 < tc) {
                            bufferReader.readLine();
                        }
                        break;
                    }
                }
                int[] prev = new int[chocolates];
                if (!isInputOK) {
                    continue;
                }

                isInputOK = initValues(prev, b, packages, a);
                if (isInputOK) {
                    int minCcc = prev[prev.length - 1];
                    result[t] = String.valueOf(minCcc);
                    if (t + 1 < tc) {
                        bufferReader.readLine();
                    }
                    continue;
                }

                int count = 1;
                int minCcc = -1;
                int[] curr;
                while (minCcc == -1) {
                    curr = new int[chocolates];
                    for (int i = count; i < chocolates; i++) {
                        curr[i] = getMinimum(i, count, prev, packages, b, a);
                    }
                    prev = curr;
                    minCcc = prev[chocolates - 1];
                    count++;
                    if (count == chocolates) {
                        break;
                    }
                }
                result[t] = String.valueOf(minCcc);
                if (t + 1 < tc) {
                    bufferReader.readLine();
                }
            }

            for (int i=0; i<tc; i++) {
                bufferWriter.write(String.format("Case #%d: %s\n", i+1, result[i]));
                bufferWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}