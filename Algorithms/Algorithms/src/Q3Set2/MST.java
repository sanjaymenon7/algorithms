
package Q3Set2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// copied from http://www.keithschwarz.com/interesting/code/?dir=kruskal
public class MST {
    private List<Edge> mstEdges;

    private int mstDone;

    private long mstWeight;

    public int getMstDone() {
        return mstDone;
    }

    public long getMstWeight() {
        return mstWeight;
    }

    public List<Edge> getMinimumSpanningTree(Graph g, int n) {
        if (mstEdges != null) {
            return mstEdges;
        } else {
            List<Edge> edges = g.getAllEdges();
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(n);

            int edgesAdded = 0;
            mstEdges = new ArrayList<Edge>();
            for (Edge edge : edges) {
                if (unionFind.find(edge.source) == unionFind.find(edge.destination)) {
                    continue;
                }
                unionFind.union(edge.source, edge.destination);
	        	mstEdges.add(edge);
                mstWeight += edge.weight;
                if (++edgesAdded == n - 1)
                    break;
            }
            if (edgesAdded < n - 1) {
                mstDone = 0;
            } else {
                mstDone = 1;
            }
            return mstEdges;
        }
    }
}