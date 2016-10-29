package TDKruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;







public class Graph {
private int numberOfNodes;
//total cost of all edges
private long edgeCosts;
public List<Edge> allEdges;
	
	/*
	 * since the weight of edges can always be represented as a diagonally symmetric array
	 * we can use 1D array to represent 2D array 
	 *  http://www.codeguru.com/cpp/cpp/algorithms/general/article.php/c11211/TIP-Half-Size-Triangular-Matrix.htm */
	private int[] weightOfEdges;
	
	
	Graph(int numberOfNodes)
	{
		this.numberOfNodes = numberOfNodes;
		this.allEdges = new LinkedList<Edge>();
		/*
		 * assuming that diagonal elements are allowed
		 * i.e. there can be edge from self*/
		int size = (int)Math.ceil((numberOfNodes*(numberOfNodes + 1)) / 2d);
		
		weightOfEdges = new int[size];
	}
	public String toString()
	{
		StringBuilder s=new StringBuilder();
		for(Edge e:allEdges)
		{
			s.append(e.start+1);
			s.append("-->");
			s.append(e.end+1);
			s.append("\n");
		}
		return s.toString();
	}
	public void addedgeCosts(int cost)
	{
		edgeCosts+=cost;
	}
	public long getTotalCost() {
		return edgeCosts;
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
	
	
	
	public List<Edge> getAllEdges() {
		return allEdges;
	}
	public void displayWOE()
	{
		System.out.println("Weight of edges is");
		for(int i:weightOfEdges)
			System.out.print(i+" ");
		System.out.println();
	}
	
	
}



	



