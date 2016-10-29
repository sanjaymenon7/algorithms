package Q3Set2;

import java.util.Scanner;

public class RoadDestruction {
    static int weight[][];

    public static void main(String[] args) {
        // create scanner object
        Scanner sc = new Scanner(System.in);

        // get number of testcases
        int t = sc.nextInt();
        for (int testCase = 1; testCase <= t; testCase++) {
            int numOfNodes = sc.nextInt();
            Graph g = new Graph(numOfNodes);
            int numOfEdges = sc.nextInt();
            for (int i = 0; i < numOfEdges; i++) {
                int source = sc.nextInt() - 1;
                int destination = sc.nextInt() - 1;
                int Cost = sc.nextInt() * sc.nextInt();
                Edge e = new Edge(source, destination, Cost);
                g.allEdges.add(e);
                g.addedgeCosts(Cost);
            }

            //print test case
            System.out.print("Case #" + testCase + ": ");

            MST k = new MST();
            k.getMinimumSpanningTree(g, numOfNodes);

            if (k.getMstDone() == 0)
                System.out.println("impossible");
            else {
                System.out.println(g.getTotalCost() - k.getMstWeight());
            }


        }
    }

}