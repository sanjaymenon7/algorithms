package Set3Q4;

public class Node implements Comparable<Node> {
	
	int node;
    int distance;

    public Node(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(distance, o.distance);
    }

}
