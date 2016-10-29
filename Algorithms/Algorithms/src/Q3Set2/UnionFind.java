package Q3Set2;
// Source code taken from http://www.mathblog.dk/disjoint-set-data-structure/
class UnionFind {
    private int[] parent;

    public UnionFind(int numberOfNodes) {
        parent = new int[numberOfNodes];
        /*initialize all nodes in their own disjoint sets*/
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
    }

    // Finds the representative of the set that i is an element of
    public int find(int i) {
        // If i is the parent of itself
        if (parent[i] == i) {

            // Then i is the representative of his set
            return i;
        } else { // Else if i is not the parent of itself

            // Then i is not the representative of his set,
            // so we recursively call Find on its parent
            return find(parent[i]);
        }
    }

    // Unites the set that includes i and the set that includes j
    public void union(int i, int j) {

        // Find the representatives (or the root nodes) for the set that includes i
        int irep = this.find(i);
        // And do the same for the set that includes j
        int jrep = this.find(j);

        // Make the parent of i's representative be j's representative
        // (effectively moving all of i's set into j's set)
        this.parent[irep] = jrep;
    }

    public int[] getDataStructure() {
        return parent;
    }
}