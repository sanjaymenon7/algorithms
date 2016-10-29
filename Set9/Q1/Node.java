package meteorite;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sreenath on 25.06.15.
 */
public class Node {

    int x;
    int y;
    boolean isVisited;
    public List<Node> adjList;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        adjList = new LinkedList<Node>();
    }
}
