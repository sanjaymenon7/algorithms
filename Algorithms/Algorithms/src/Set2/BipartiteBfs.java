package Set2;

import java.util.LinkedList;
import java.util.Queue;
// used code from http://www.sanfoundry.com/java-program-check-whether-graph-bipartite-using-bfs/
public class BipartiteBfs {

	private int numberOfVertices;

	private Queue<Integer> queue;

	public static final int NO_COLOR = 0;

	public static final int RED = 1;

	public static final int BLUE = 2;

	public BipartiteBfs(int numberOfVertices)

	{

		this.numberOfVertices = numberOfVertices;

		queue = new LinkedList<Integer>();

	}

	public boolean isBipartite(int adjacencyMatrix[][], int source,
			int visited[])

	{

		int[] colored = new int[numberOfVertices];

		for (int vertex = 0; vertex < numberOfVertices; vertex++)

		{

			colored[vertex] = NO_COLOR;

		}

		colored[source] = RED;

		queue.add(source);

		visited[source] = 1;

		int element, neighbour;

		while (!queue.isEmpty())

		{

			element = queue.remove();

			neighbour = 0;

			while (neighbour < numberOfVertices)

			{

				if (adjacencyMatrix[element][neighbour] == 1
						&& colored[element] == colored[neighbour])

				{

					return false;

				}

				if (adjacencyMatrix[element][neighbour] == 1
						&& colored[neighbour] == NO_COLOR)

				{

					colored[neighbour] = (colored[element] == RED) ? BLUE : RED;

					queue.add(neighbour);

					visited[neighbour] = 1;

				}

				neighbour++;

			}

		}

		return true;

	}

}
