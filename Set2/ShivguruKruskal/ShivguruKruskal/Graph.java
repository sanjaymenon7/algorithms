package ShivguruKruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class Graph {
private int numberOfNodes;
	
	/*
	 * since the weight of edges can always be represented as a diagonally symmetric array
	 * we can use 1D array to represent 2D array 
	 *  http://www.codeguru.com/cpp/cpp/algorithms/general/article.php/c11211/TIP-Half-Size-Triangular-Matrix.htm */
	private int[] weightOfEdges;
	
	
	Graph(int numberOfNodes)
	{
		this.numberOfNodes = numberOfNodes;
		/*
		 * assuming that diagonal elements are allowed
		 * i.e. there can be edge from self*/
		int size = (int)Math.ceil((numberOfNodes*(numberOfNodes + 1)) / 2d);
		
		weightOfEdges = new int[size];
	}
	
	public int getNumberOfNodes()
	{
		return numberOfNodes;
	}
	public int getValueAtPositions(int row, int col)
	{
		
		int position = (row <= col) ? row *numberOfNodes - (row -1)*((row -1) + 1)/2 + col -row  : col*numberOfNodes - (col-1)*((col-1) + 1)/2 + row  - col;
		
		return weightOfEdges[position];
	}
	
	public void setValueAtPositions(int row, int col, int value)
	{
		
		int position = (row <= col) ? row *numberOfNodes - (row -1)*((row -1) + 1)/2 + col -row  : col*numberOfNodes - (col-1)*((col-1) + 1)/2 + row  - col;
		
		weightOfEdges[position] = value;
	}
	
	
	
	List<Edge> getEdges()
	{
		List<Edge> edges = new ArrayList<Edge>();
		for (int i = 0; i < this.numberOfNodes; i++) 
		{
			for (int j = i; j < this.numberOfNodes; j++) 
			{
				/*since the matrix is diagonally symmetrical we would not consider the positions in the
				 * bottom half of the matrix
				 * We do not take the diagonal elements into consideration*/
					//if(getValueAtPositions(i, j)!=0)
				if(i<j)
						edges.add(new Edge(i, j, getValueAtPositions(i, j)));
				
			}
		}
		
		return edges;
	}
	
	public void displayWOE()
	{
		System.out.println("Weight of edges is");
		for(int i:weightOfEdges)
			System.out.print(i+" ");
		System.out.println();
	}
	
	
}



	



