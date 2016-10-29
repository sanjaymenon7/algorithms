package meteorite;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Created by sreenath on 25.06.15.
 */
public class Main {

    public static void main(String[] args) {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for (int tc=1; tc<=t; tc++) {
                String[] inputString = bufferReader.readLine().split("\\s+");
                int xPoint = Integer.parseInt(inputString[0]);
                int yPoint = Integer.parseInt(inputString[1]);
                int numOfLines = Integer.parseInt(inputString[2]);

                HashMap<String, Node> nodes = new HashMap<String, Node>();
                Node rootNode = null;
                for (int i=0; i<numOfLines; i++) {
                    String[] lineDesc = bufferReader.readLine().split("\\s+");
                    int x1 = Integer.parseInt(lineDesc[0]);
                    int y1 = Integer.parseInt(lineDesc[1]);
                    int x2 = Integer.parseInt(lineDesc[2]);
                    int y2 = Integer.parseInt(lineDesc[3]);

                    String key1 = x1 + "_" + y1;
                    String key2 = x2 + "_" + y2;

                    Node node1=nodes.get(key1), node2=nodes.get(key2);
                    if(node1 == null) {
                        node1 = new Node(x1, y1);
                        nodes.put(key1, node1);
                    }
                    if (node2 == null) {
                        node2 = new Node(x2, y2);
                        nodes.put(key2, node2);
                    }
                    node1.adjList.add(node2);
                    node2.adjList.add(node1);
                    if (rootNode == null) {
                        rootNode = node1;
                    }
                }

                Polygon p = constructPolygon(rootNode);

                if (p.contains(xPoint, yPoint)) {
                    bufferWriter.write("Case #" + tc + ": jackpot");
                } else {
                    bufferWriter.write("Case #" + tc + ": too bad");
                }
                bufferWriter.write("\n");
                bufferWriter.flush();
                if (tc != t) {
                    bufferReader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Polygon constructPolygon(Node n) {
        Polygon p = new Polygon();
        Stack<Node> q = new Stack<Node>();
        q.add(n);
        while (!q.isEmpty()) {
            Node node = q.pop();
            node.isVisited = true;
            p.addPoint(node.x, node.y);
            for (Node r: node.adjList) {
                if (!r.isVisited && !q.contains(r)) {
                    q.add(r);
                }
            }
        }
        return p;
    }
}
