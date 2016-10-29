package bankrobbery;

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
                int numOfPolice = Integer.parseInt(nodesAndEdges[0]);
                int numOfEdges = Integer.parseInt(nodesAndEdges[2]);
                int numOfNodes = Integer.parseInt(nodesAndEdges[1]);

                List<Dinic.Edge>[] graph = Dinic.createGraph(numOfNodes);

                for (int i=0; i<numOfEdges; i++) {
                    String[] inputString = bufferReader.readLine().split("\\s+");
                    int src = Integer.parseInt(inputString[0]) - 1;
                    int dest = Integer.parseInt(inputString[1]) - 1;
                    int capacity = Integer.parseInt(inputString[2]);
                    Dinic.addEdge(graph, src, dest, capacity);
                    Dinic.addEdge(graph, dest, src, capacity);
                }

                int maxFlow = Dinic.maxFlow(graph, 0, numOfNodes - 1);

                if (maxFlow <= numOfPolice) {
                    bufferWriter.write(String.format(outputFormat, "Case #" + (tc + 1) + ": " + "yes"));
                } else {
                    bufferWriter.write(String.format(outputFormat, "Case #" + (tc + 1) + ": " + "no"));
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
}
