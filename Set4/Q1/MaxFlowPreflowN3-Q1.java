package pushandrelable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.ObjectInputStream.GetField;

public class MaxFlowPreflowN3 {

	int[][] cap;

	public void init(int nodes) {
		cap = new int[nodes][nodes];
	}

	public void addEdge(int s, int t, int capacity) {
		cap[s][t] = capacity;
	}
	
	public int getEdge(int s, int t) {
		
		return cap[s][t];
	}

	public int maxFlow(int s, int t) {
		int n = cap.length;

		int[] h = new int[n];
		h[s] = n - 1;

		int[] maxh = new int[n];

		int[][] f = new int[n][n];
		int[] e = new int[n];

		for (int i = 0; i < n; ++i) {
			f[s][i] = cap[s][i];
			f[i][s] = -f[s][i];
			e[i] = cap[s][i];
		}

		for (int sz = 0;;) {
			if (sz == 0) {
				for (int i = 0; i < n; ++i)
					if (i != s && i != t && e[i] > 0) {
						if (sz != 0 && h[i] > h[maxh[0]])
							sz = 0;
						maxh[sz++] = i;
					}
			}
			if (sz == 0)
				break;
			while (sz != 0) {
				int i = maxh[sz - 1];
				boolean pushed = false;
				for (int j = 0; j < n && e[i] != 0; ++j) {
					if (h[i] == h[j] + 1 && cap[i][j] - f[i][j] > 0) {
						int df = Math.min(cap[i][j] - f[i][j], e[i]);
						f[i][j] += df;
						f[j][i] -= df;
						e[i] -= df;
						e[j] += df;
						if (e[i] == 0)
							--sz;
						pushed = true;
					}
				}
				if (!pushed) {
					h[i] = Integer.MAX_VALUE;
					for (int j = 0; j < n; ++j)
						if (h[i] > h[j] + 1 && cap[i][j] - f[i][j] > 0)
							h[i] = h[j] + 1;
					if (h[i] > h[maxh[0]]) {
						sz = 0;
						break;
					}
				}
			}
		}

		int flow = 0;
		for (int i = 0; i < n; i++)
			flow += f[s][i];

		return flow;
	}

	// Usage example
	public static void main(String[] args) {
		// int[][] capacity = { { 0, 3, 2 }, { 0, 0, 2 }, { 0, 0, 0 } };

		int t;
		// output string
		String outputFormat = new String("%s\n");

		// initialize reader and writer
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
				System.in));
		BufferedWriter bufferWriter = new BufferedWriter(
				new OutputStreamWriter(System.out));

		try {

			t = Integer.parseInt(bufferReader.readLine());
			for (int testcase = 1; testcase <= t; testcase++) {
				
				String[] factoryDetails = bufferReader.readLine().split("\\s+");
				int numOfFountains = Integer.parseInt(factoryDetails[0]);
				int numofDistCtr = Integer.parseInt(factoryDetails[1]);
				int numOfWrapCtr = Integer.parseInt(factoryDetails[2]);
				int numOfBelts = Integer.parseInt(factoryDetails[3]);
				
				String[] nodeDetails = new String[numOfFountains+numofDistCtr+numOfWrapCtr+1];
				int[] nodeCapacity = new int[numOfFountains+numofDistCtr+numOfWrapCtr+1];
				int node; 
				for (node = 1; node <=numOfFountains; node++) {
					nodeDetails[node] ="F";
				}
				//node = node+1;
				for ( ; node <=numOfFountains+numofDistCtr; node++) {
					nodeDetails[node] ="D";
				}
				//node = node+1;
				for ( ; node <=numOfFountains+numofDistCtr+numOfWrapCtr; node++) {
					nodeDetails[node] ="W";
				}
				MaxFlowPreflowN3 flow = new MaxFlowPreflowN3();
				int[][] capacityMatrix = new int[numOfFountains+numofDistCtr+numOfWrapCtr+2][numOfFountains+numofDistCtr+numOfWrapCtr+2];
				int n = capacityMatrix.length;
				flow.init(n);
				for (int belt = 0; belt < numOfBelts; belt++) {
					String[] beltDetails = bufferReader.readLine().split("\\s+");
					int source = Integer.parseInt(beltDetails[0]);
					int dest = Integer.parseInt(beltDetails[1]);
					int capacity = Integer.parseInt(beltDetails[2]);
					capacityMatrix[source][dest] = capacity;
					capacityMatrix[dest][source] = capacity;
					flow.addEdge(source, dest, capacity);
					flow.addEdge(dest, source, capacity);
					//if(nodeDetails[source] == "F")
					//	nodeCapacity[source]= nodeCapacity[source]+capacity;
					//if(nodeDetails[dest] == "W")
					//	nodeCapacity[dest]= nodeCapacity[dest]+capacity;
				}
				
				//boolean requireSingleSource = false;
				//boolean requireSingleSink = false;
				
				//if(numOfFountains > 1)
				//	requireSingleSource =true;
				//if(numOfWrapCtr > 1)
				//	requireSingleSink =true;
				
				//if(requireSingleSource){
					for (int fountain = 1; fountain <= numOfFountains; fountain++) {
						//flow.addEdge(0, fountain, nodeCapacity[fountain]);
						flow.addEdge(0, fountain, 10000);
						capacityMatrix[0][fountain] = nodeCapacity[fountain];
					}
				//}
				//if(requireSingleSink){
					for (int wrapStn = numOfFountains+numofDistCtr+1; wrapStn <= numOfFountains+numofDistCtr+numOfWrapCtr; wrapStn++) {
						//flow.addEdge(wrapStn, numOfFountains+numofDistCtr+numOfWrapCtr+1, nodeCapacity[wrapStn]);
						flow.addEdge(wrapStn, numOfFountains+numofDistCtr+numOfWrapCtr+1, 10000);
						capacityMatrix[wrapStn][numOfFountains+numofDistCtr+numOfWrapCtr+1] = nodeCapacity[wrapStn];
					}
				//}
				//if(requireSingleSink == true && requireSingleSource ==true)
					System.out.println("Case #"+testcase+": "+flow.maxFlow(0, numOfFountains+numofDistCtr+numOfWrapCtr+1));
				//else if(requireSingleSource)
				//	System.out.println("Case #"+testcase+": "+flow.maxFlow(0, numOfFountains+numofDistCtr+numOfWrapCtr));
				//else if (requireSingleSink)
					//System.out.println("Case #"+testcase+": "+flow.maxFlow(1, numOfFountains+numofDistCtr+numOfWrapCtr+1));
				//System.out.println(flow.maxFlow(0, numOfFountains+numofDistCtr+numOfWrapCtr+1));
				if (testcase != t)
					bufferReader.readLine();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		/*int[][] capacity = new int[9][9];

		int n = capacity.length;
		MaxFlowPreflowN3 flow = new MaxFlowPreflowN3();
		flow.init(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (capacity[i][j] != 0)
					flow.addEdge(i, j, capacity[i][j]);
		System.out.println(flow.maxFlow(0, 8));*/
	}
}
