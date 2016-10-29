package ShivguruKruskal;
// Copied from http://www.mathblog.dk/disjoint-set-data-structure/
class UnionFind {
	private int[] p;

	public UnionFind(int numberOfNodes) 
	{
		p = new int[numberOfNodes];
		/*initialize all nodes in their own disjoint sets*/
		for (int i = 0; i < p.length; i++) {
			p[i] = i;
		}
	}

	// Finds the representative of the set that i is an element of
	public int find(int i)
	{
		// If i is the parent of itself
		if (p[i] == i)
		{

			// Then i is the representative of his set
			return i;
		}
		else 
		{ // Else if i is not the parent of itself

			// Then i is not the representative of his set,
			// so we recursively call Find on its parent
			return find(p[i]);
		}
	}

	// Unites the set that includes i and the set that includes j
	public void union(int i, int j)
	{

		// Find the representatives (or the root nodes) for the set that includes i
		int irep = this.find(i);
		// And do the same for the set that includes j
		int	jrep = this.find(j);

		// Make the parent of i's representative be j's representative
		// (effectively moving all of i's set into j's set)
		this.p[irep] = jrep;
	}
	
	public int[] getDataStructure()
	{
		return p;
	}
}
