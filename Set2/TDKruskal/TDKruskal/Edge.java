package TDKruskal;

class Edge implements Comparable<Edge>
{
	public final int start, end;  // The edge's endpoints
    public final int cost;   // The edge's cost
    /* When sorting edges, we need some way to break ties if two edges
     * have the same cost.  This value, the "tiebreaker" is unique for
     * each edge and serves solely to give some way to distinguish
     * between edges.
     */
    public final int tiebreaker;
    public static int nextTiebreaker = 0;
    
    public Edge(int start, int end, int cost) 
    {
		this.start = start;
		this.end = end;
		this.cost = cost;
		/* Use the next tiebreaker here. */
        tiebreaker = nextTiebreaker++;
	}
	@Override
	public int compareTo(Edge other) {
		/* Check how the costs compare. */
        if (cost < other.cost) return +1;
        if (cost > other.cost) return -1;

        /* If they have equal costs, use the tiebreaker to make the
         * decision.
         */
        return tiebreaker - other.tiebreaker;
	}
	
	
}