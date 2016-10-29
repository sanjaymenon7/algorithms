/**
 * Created by sreenath on 28.04.15.
 */
public class Edge implements Comparable<Edge> {
    int source;
    int destination;
    int weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        if (this.weight < o.weight) {
            return 1;
        } else {
            return -1;
        }
    }
}
