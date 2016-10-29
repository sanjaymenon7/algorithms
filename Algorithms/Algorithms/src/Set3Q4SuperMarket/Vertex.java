package Set3Q4SuperMarket;

public class Vertex implements Comparable<Vertex> {
	
	 public int node;
	    public int distance;

	    public Vertex(int node, int distance) {
	        this.node = node;
	        this.distance = distance;
	    }

	    @Override
	    public int compareTo(Vertex o) {
	        return Integer.compare(distance, o.distance);
	    }

}
