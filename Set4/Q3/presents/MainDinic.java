package presents;

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
                int numOfFriends = Integer.parseInt(nodesAndEdges[0]);
                int numOfPresents = Integer.parseInt(nodesAndEdges[1]);
                int numOfNodes = numOfFriends + numOfPresents + 2;
                List<Dinic.Edge>[] graph = presents.Dinic.createGraph(numOfNodes);

                for (int i=1; i<=numOfFriends; i++) {
                    String inputString = bufferReader.readLine();
                    if (inputString.isEmpty()) {
                        continue;
                    }

                    String[] inputArray = inputString.split(",");
                    for(String pref: inputArray) {
                        if (pref.contains("-")) {
                            addSectionEdges(graph, i, pref, numOfFriends);
                        } else {
                            int presentIndex = Integer.parseInt(pref);
                            Dinic.addEdge(graph, i, numOfFriends + presentIndex, 1);
                        }
                    }
                }

                // Add super source
                for (int i=1; i<=numOfFriends; i++) {
                    Dinic.addEdge(graph, 0, i, 1);
                }

                // Add super sink
                for (int i=1; i<=numOfPresents; i++) {
                    Dinic.addEdge(graph, numOfFriends + i, numOfNodes - 1, 1);
                }

                int maxFlow = Dinic.maxFlow(graph, 0, numOfNodes - 1);

                if (maxFlow == numOfFriends) {
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

    private static void addSectionEdges(List<Dinic.Edge>[] graph, int friend, String presents, int numOfFriends) {
        String[] presentArray = presents.split("-");
        int start = Integer.parseInt(presentArray[0]);
        int end = Integer.parseInt(presentArray[1]);
        for (int i=start; i<=end; i++) {
            Dinic.addEdge(graph, friend, numOfFriends + i, 1);
        }
    }
}
