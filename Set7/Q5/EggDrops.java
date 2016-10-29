import java.io.*;

/**
 * Created by shiv on 17/06/15.
 */

// adapted the code from http://www.geeksforgeeks.org/dynamic-programming-set-11-egg-dropping-puzzle/
public class EggDrops {


    /* Function to get minimum number of trails needed in worst
  case with n eggs and k floors */
    public static void eggDrop(int n, int k,int [][] memMatrix)
    {
    /* A 2D table where entery eggFloor[i][j] will represent minimum
       number of trials needed for i eggs and j floors. */
        int eggFloor[][]=memMatrix;
        int i, j, x;



        // Fill the entries in table using optimal substructure
        // property
        for (i = 1; i <= n; i++)
        {
            for (j = 1; j <= k; j++)
            {
                // We always need j trials for one egg and j floors.

                if(i==1)
                {
                    eggFloor[1][j]=j;
                }
                else {
                    int min=Integer.MAX_VALUE;
                    // We need one trial for one floor
                    if(j==1)
                    {
                        min=0;
                        eggFloor[i][1]=1;
                    }
                    else {
                        for (x = 1; x <= j; x++) {
                            int res = 0;
                            if (j - x > 0) {
                                res = Integer.max(eggFloor[i][x - 1], eggFloor[i - 1][j - x]);
                                min = Integer.min(res, min);
                            }

                        }
                        eggFloor[i][j] = min + 1;
                    }

                }
            }
        }

    }

    public static void printMatrix(int [][]a)
    {
        System.out.println(" Matrix is: ");

        for(int i=1;i<a.length;i++)
        {
            for(int j=1;j<a[0].length;j++)
            {
                System.out.print(a[i][j]);
            }
            System.out.println();

        }
    }

    public static void main(String args[])
    {

        int t;
        // output string
        String outputFormat = new String("%s\n");

        // initialize reader and writer
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
                System.in));
        BufferedWriter bufferWriter = new BufferedWriter(
                new OutputStreamWriter(System.out));
        int memMatrix[][]=new int[1001][1001];
        eggDrop(1000,1000,memMatrix);

        try {
            t = Integer.parseInt(bufferReader.readLine());

                for (int testcase = 1; testcase <= t; testcase++) {
                    String[] getNandK = bufferReader.readLine().split("\\s+");

                    int n=Integer.parseInt(getNandK[0]);
                    int k = Integer.parseInt(getNandK[1]);

                    System.out.println("Case #"+testcase+": "+memMatrix[n][k]);

                }
            } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
