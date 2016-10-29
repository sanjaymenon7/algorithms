import java.awt.font.NumericShaper;
import java.io.*;
import java.util.*;
public class FootballChampion
{

	public static void main(String[] args)
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			Tournament[] tournaments = new Tournament[caseCount];
			/*read inputs for each test case*/
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++)
			{
				String[] input = bufferReader.readLine().split("\\s+");
				int numberOfTeams = Integer.parseInt(input[0]);
				int numberOfMatches = Integer.parseInt(input[1]);
				Tournament tournament = new Tournament(numberOfTeams);
				tournaments[caseNumber] = tournament;
				/*read existing team scores*/
				String[] scores = bufferReader.readLine().split("\\s+");
				for (int teamNumber = 1; teamNumber <= numberOfTeams; teamNumber++)
				{
					tournament.teams[teamNumber - 1] = new Team(teamNumber, Integer.parseInt(scores[teamNumber - 1]));
				}
				/*read the match details*/
				for (int matchNumber = 0; matchNumber < numberOfMatches; matchNumber++) 
				{
					String[] match = bufferReader.readLine().split("\\s+");
					int teamA = Integer.parseInt(match[0]);
					int teamB = Integer.parseInt(match[1]);
					tournament.matches[teamA][teamB] +=1;
					tournament.matches[teamB][teamA] +=1;
					tournament.teams[teamA - 1].maxWin +=1;
					tournament.teams[teamB - 1].maxWin +=1;
				}
				
				tournament.determineElimination();
				/*read empty line if not the last case*/
				if(caseNumber != caseCount - 1)
					bufferReader.readLine();
			}
			
			/*output result*/
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++)
			{
				Tournament tournament = tournaments[caseNumber];
				bufferWriter.write(String.format("Case #%d:", caseNumber + 1));
				for(Team t:tournament.teams)
				{
					bufferWriter.write(String.format(" %s", t.madeTheCut ? "yes" : "no"));
				}
				bufferWriter.write("\n");
			}
			bufferWriter.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
class Tournament
{
	int[][] matches;
	int numberOfTeams;
	Team[] teams;
	
	public Tournament(int numberOfTeams) 
	{
		this.numberOfTeams = numberOfTeams;
		matches = new int[numberOfTeams + 1][numberOfTeams + 1];
		for (int i = 0; i < matches.length; i++)
		{
			Arrays.fill(matches[i], 0);
		}
		teams = new Team[numberOfTeams];		
	}
	
	public void determineElimination()
	{
		/*copy teams to temp array*/
		Team[] tempTeams = new Team[numberOfTeams];
		System.arraycopy(teams, 0, tempTeams, 0, numberOfTeams);
		/*order the teams in descending order based on their maximum achievable score*/
		Arrays.sort(tempTeams);
		/*find the maximum current win*/
		int maximumCurrentWin = 0;
		for(Team thisTeam: tempTeams)
		{
			maximumCurrentWin = thisTeam.currentWin > maximumCurrentWin ? thisTeam.currentWin : maximumCurrentWin;
		}
		/*check if team can win*/
		for(Team t:tempTeams)
		{
			if(t.maxWin < maximumCurrentWin)
			{
				t.madeTheCut = false;
				continue; //change it to break
			}
			int teamId = t.id;
			int numberOfMatchesBetweenOtherTeams = 0;
			Map<Integer, Integer> remainingPlayingTeams = new HashMap<Integer, Integer>();
			for (int i = 1; i < matches.length; i++) 
			{
				for (int j = 1; j < matches.length; j++)
				{	
					if(i != teamId && j != teamId && matches[i][j] != 0 && i < j)
					{
							numberOfMatchesBetweenOtherTeams += matches[i][j];
							/*add the team ids to the participating team dictionary*/
							//remainingPlayingTeams.put(i, i);
							//remainingPlayingTeams.put(j, j);
					}
				}
			}
			/*since all mathches were added twice*/
			//numberOfMatchesBetweenOtherTeams /=2;
			/*int totalScoreOfOtherTeams = 0;
			for(Team otherTeam:tempTeams)
			{
				if(otherTeam.id != teamId)
				{
					totalScoreOfOtherTeams += otherTeam.currentWin;
				}
			}
			float avgScore = (numberOfMatchesBetweenOtherTeams + totalScoreOfOtherTeams) /(float)(numberOfTeams - 1);
			if((float)t.maxWin < avgScore)
			{
				break;
			}
			else
			{*/
				/*out of the teams that made the cut there can still be teams that actually do not make the cut
				 * for these teams only form the max flow graph*/
	
				int sourceId = 0;
				int targetId = 600;
				Vertex source = new Vertex(0); //source
				Vertex target = new Vertex(targetId); // arbitrary sink
				FlowGraph fGraph = new FlowGraph(0, targetId);
				fGraph.vertices.put(0,source);
				fGraph.vertices.put(targetId,target); 
				int nodeCount = 199;
				for (int i = 1; i < matches.length; i++) 
				{
					if(i != teamId)
					{
						Vertex teamNode = new Vertex(i);
						fGraph.vertices.put(i,teamNode);
						teamNode.incidentOnEdge.add(new Edge(i, targetId, teams[teamId-1].maxWin - teams[i-1].currentWin, target.incidentOnEdge.size()));
						target.incidentOnEdge.add(new Edge(targetId, i, 0, teamNode.incidentOnEdge.size() - 1));
					}
					for (int j = 1; j < matches.length; j++)
					{
						if(i != teamId && j != teamId)
						{
							if(i>j && matches[i][j] > 0)
							{
								Vertex matchNode = new Vertex(++nodeCount);
								fGraph.vertices.put(nodeCount,matchNode);
								/*connect source to match node*/
								matchNode.incidentOnEdge.add(new Edge(nodeCount, 0, 0, matchNode.incidentOnEdge.size()));
								source.incidentOnEdge.add(new Edge(0, nodeCount, matches[i][j], matchNode.incidentOnEdge.size() - 1));
								/*connect match node to the team nodes*/
								matchNode.incidentOnEdge.add(new Edge(nodeCount, i, Integer.MAX_VALUE, fGraph.vertices.get(i).incidentOnEdge.size()));
								fGraph.vertices.get(i).incidentOnEdge.add(new Edge(i, nodeCount, 0, matchNode.incidentOnEdge.size() - 1));
								
								matchNode.incidentOnEdge.add(new Edge(nodeCount, j, Integer.MAX_VALUE, fGraph.vertices.get(j).incidentOnEdge.size()));
								fGraph.vertices.get(j).incidentOnEdge.add(new Edge(j, nodeCount, 0, matchNode.incidentOnEdge.size() - 1));
							}
						}
					}
				}
				
				fGraph.DinitzMaxFlow();
				if(fGraph.maxFlow == numberOfMatchesBetweenOtherTeams)
				{
					/*team is not eliminated
					 * proceed with rest of the teams*/

					t.madeTheCut = true;
				}
				else
				{
					/*team did not make the cut
					 * the teams with lower max win have no chance either*/
					t.madeTheCut = false;
					//break;
				}
			//}
		}
	}
}


class Team implements Comparable<Team>
{
	int id;
	int currentWin;
	int maxWin;
	boolean madeTheCut;
	public Team(int id, int currentWin)
	{
		this.id = id;
		this.currentWin = currentWin;
		this.maxWin = currentWin;
		madeTheCut = false;
	}
	@Override
	public int compareTo(Team arg0) 
	{
		if(this.maxWin > arg0.maxWin)
			return -1;
		if(this.maxWin < arg0.maxWin)
			return 1;
		return 0;
	}
}

class FlowGraph
{
	int numberOfNodes;
	Map<Integer, Vertex> vertices;

	int sourceVertex, targetVertex;
	int maxFlow;
	public FlowGraph(int source, int target) 
	{
		vertices = new HashMap<Integer,Vertex>();
		this.sourceVertex = source;
		this.targetVertex = target;
		maxFlow = 0;
	}
	
	/*Inspiration drawn from 
 	 * https://sites.google.com/site/indy256/algo/dinic_flow*/
 	public void DinitzMaxFlow()
 	{
 		/*flow of each edge already set to 0*/
 		/*Construct Gl from Gf of G*/
 		Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
 		//int[] dist = new int[vertices.size()];
 		while (BFS(sourceVertex, targetVertex, dist))
 		{
 			Map<Integer, Integer> ptr = new HashMap<Integer, Integer>();
 			for(Vertex v:vertices.values())
 			{
 				ptr.put(v.id, 0);
 			}
 			while(true)
 			{
 				int df = DFSForBlockingFLow(ptr, dist, targetVertex, sourceVertex, Integer.MAX_VALUE);
 		        if (df == 0)
 		          break;
 		        maxFlow += df;
 			}
 		}
 	}
 	
 	private boolean BFS(int sourceId, int targetId, Map<Integer, Integer> dist)
 	{
 		dist.clear();
 		dist.put(sourceId, 0);
 		int[] Q = new int[vertices.size()];
 		int sizeOfQ = 0;
 		Q[sizeOfQ++] = sourceId;
 		for (int i = 0; i < sizeOfQ; i++)
 		{
 			int u = Q[i];
			for(Edge e:vertices.get(u).incidentOnEdge)
			{
				if(!dist.containsKey(e.end) && e.flow < e.capacity)
				{
					dist.put(e.end, dist.get(u) + 1);
					Q[sizeOfQ++] = e.end;
				}
			}
		}
 		return dist.containsKey(targetId);
 	}
 	
 	private int DFSForBlockingFLow(Map<Integer, Integer> ptr, Map<Integer, Integer> dist, int targetId, int u, int flow)
 	{
 		if(u == targetId)
 		{
 			return flow;
 		}
 		for (; ptr.get(u) < vertices.get(u).incidentOnEdge.size(); ptr.replace(u, ptr.get(u) + 1))
 		{
 			Edge e = vertices.get(u).incidentOnEdge.get(ptr.get(u));
 			if(dist.containsKey(e.end) && dist.containsKey(u) && (dist.get(e.end) == dist.get(u) + 1) && e.flow < e.capacity)
 			{
 				int df = DFSForBlockingFLow(ptr, dist, targetId, e.end, Math.min(flow, e.capacity - e.flow));
 				if(df > 0)
 				{
 					e.flow += df;
 					vertices.get(e.end).incidentOnEdge.get(e.reverse).flow -= df;
 					return df;
 				}
 			}		
 		}
 		return 0;
 	}
}

class Vertex
{
	int id;
	int distance;
	List<Edge> incidentOnEdge;
	
	public Vertex(int id)
	{
		this.id= id;
		this.distance = 0;
		this.incidentOnEdge = new ArrayList<Edge>();
	}
}

class Edge
{
	int start;
	int end;
	int capacity;
	int flow;
	int reverse;
	
	public Edge(int start, int end, int capacity, int rev) 
	{
		this.start = start;
		this.end = end;
		this.capacity = capacity;
		this.flow = 0;
		this.reverse = rev;
	}
}