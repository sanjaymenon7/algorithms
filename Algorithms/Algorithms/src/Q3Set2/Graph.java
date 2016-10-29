package Q3Set2;
import java.util.ArrayList;
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
        this.allEdges = new ArrayList<Edge>();
		/*
		 * assuming that diagonal elements are allowed
		 * i.e. there can be edge from self*/
        int size = (int)Math.ceil((numberOfNodes*(numberOfNodes + 1)) / 2d);

        weightOfEdges = new int[size];
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
}