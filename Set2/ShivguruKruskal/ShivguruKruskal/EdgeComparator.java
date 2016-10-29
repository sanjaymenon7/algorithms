package ShivguruKruskal;

import java.util.Comparator;

class EdgeComparator implements Comparator<Edge>
{
	/*to order lexicographically
	 * smaller comes first*/
	@Override
	public int compare(Edge o1, Edge o2) {
		if(o1.start != o2.start)
			return o1.start - o2.start;
		else
			return o1.end - o2.end;
	}
}
