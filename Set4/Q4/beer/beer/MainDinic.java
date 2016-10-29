package beer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by sreenath on 13.05.15.
 */
public class MainDinic {

    public static void main(String[] args) {
        //initialize reader and writer
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        //output string
        String outputFormat = new String("%s\n");

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for(int tc=0; tc<t; tc++) {
                String[] nodesAndEdges = bufferReader.readLine().split("\\s+");
                int numOfNodes = Integer.parseInt(nodesAndEdges[0]);
                int numOfEdges = Integer.parseInt(nodesAndEdges[1]);

                List<Dinic.Edge>[] graph = beer.Dinic.createGraph(numOfNodes);

                for (int i=1; i<=numOfEdges; i++) {
                    String[] inputArray = bufferReader.readLine().split("\\s+");
                    int start = Integer.parseInt(inputArray[0]) - 1;
                    int end = Integer.parseInt(inputArray[1]) - 1;
                    int sides = Integer.parseInt(inputArray[2]);
                    double sideValue = Double.parseDouble(inputArray[3]);
                    double capacity;
                    if (sides == 0) {
                        capacity = getCylinderArea(sideValue);
                    } else {
                        capacity = getPolygonArea(sides, sideValue);
                    }
                    Dinic.addEdge(graph, start, end, capacity);
                    Dinic.addEdge(graph, end, start, capacity);
                }

                double maxFlow = Dinic.maxFlow(graph, 0, numOfNodes - 1);

                if (maxFlow == 0) {
                    bufferWriter.write(String.format(outputFormat, "Case #" + (tc + 1) + ": " + "impossible"));
                } else {
                    bufferWriter.write(String.format(outputFormat, "Case #" + (tc + 1) + ": " + maxFlow));
                }
                bufferWriter.flush();

                if (tc != t) {
                    bufferReader.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double getCylinderArea(double radius) {
        double area = Math.PI * radius * radius;
        //return new BigDecimal(area).setScale(16, BigDecimal.ROUND_HALF_UP).doubleValue();
        return area;
    }

    private static double getPolygonArea(int sides, double sideValue) {
        double denominator = 4 * Math.tan(Math.toRadians(180/sides));
        double numerator = sideValue * sideValue * sides;
        double value = numerator / denominator;
        //return new BigDecimal(value).setScale(16, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }


}
