package BF;

public class Graph {
	int V, E;       // V-> Number of vertices, E-> Number of edges  
    Edge[] edge;    // graph is represented as an array of edges.  
    public Graph(){  
    }  
    public Graph(int V_, int E_){  
        V = V_;  
        E = E_;  
    }  
}
