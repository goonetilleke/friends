package assignment5;

import java.lang.*;
import java.util.*;



public class FriendGraph {

  ArrayList<User> adjList;
	String connectors="";
	HashMap<String,Integer> nameToIndex;
	public static int cliqueNum;
	private int dfscount;
	private boolean[] Connectors;
	private int initial;
	
	private int[] dfsnum;// inside is the order given out. index is the place in adjList
	private int[] back;

	public FriendGraph(ArrayList<User> adjList, HashMap<String,Integer> nameToIndex) {
		this.adjList = adjList;
		this.nameToIndex = nameToIndex;
		Connectors = new boolean[adjList.size()];
	}


	/**
	 * Input: Name of school (case insensitive), e.g. "penn state"
	 * Result: Subgraph of original graph, vertices are all students at the given school, 
	 * edges are a subset of the edges of the original graph such that both endpoints 
	 * are students at the school. The subgraph must be in stored in the adjacency linked
	 * lists form, just as for the original graph. 
	 * Output: Print the subgraph in the same format as the input in the Graph build section above. 
	 */
	public FriendGraph subgraph(String school) 
	{
		// null is checked in friends, so this should never be reached
		if(school == null)
			return null;

		school = school.toLowerCase();

//		FriendGraph newGraph = new FriendGraph(null, null);
//		newGraph.adjList = new ArrayList<User>();
//		newGraph.nameToIndex = new HashMap<String,Integer>(1000, 2.0f);
		
		// from above, maybe change to:
		FriendGraph newGraph = new FriendGraph(new ArrayList<User>(), new HashMap<String,Integer>(1000, 2.0f));

		User user1;
		User user2;
		User tmpUser;
		Neighbor neigh;
		Neighbor tmp;
		Neighbor tmp2;
		int newNIndex;
		int newVIndex;

		//loop through each vertex in the arrayList for vertices
		for (int i = 0; i < adjList.size(); i++) 
		{
			user1 = adjList.get(i);

			if (school.equalsIgnoreCase(user1.school)) 
			{
				//make a duplicate of the vertex and put it into the return arrayList
				tmpUser = new User(user1.name);// firstNeighbor is lost!!!!!! BAD
				tmpUser.school = user1.school;
				newGraph.adjList.add(tmpUser);
				newGraph.nameToIndex.put(tmpUser.name, newGraph.adjList.size()-1);
			}
		}

		//loop through each vertex in the arrayList to check for edges
		for(int i=0; i<adjList.size(); i++) 
		{
			user1 = adjList.get(i);
			neigh = user1.firstNeighbor;

			//check if the user is a student at the school
			if(school.equalsIgnoreCase(user1.school)) 				// added equalsIgnoreCase
			{
				//go through neighbors and check if they are students at input school
				//if so, then create a new edge for them and add them to the linked list in the new arrayList
				while(neigh != null) 
				{
					user2 = adjList.get(neigh.neighborIndex);

					if(school.equals(user2.school)) {
						newNIndex = newGraph.nameToIndex.get(user2.name);
						newVIndex = newGraph.nameToIndex.get(user1.name); 

						tmp2 = newGraph.adjList.get(newVIndex).firstNeighbor; //first neighbor of new LL
						tmp = new Neighbor(newNIndex, tmp2);
						newGraph.adjList.get(newVIndex).firstNeighbor = tmp;
					}

					neigh = neigh.next;
				}

			}

		}

		if(newGraph.adjList.isEmpty())
			return null;
		return newGraph;
	}

	/**
	 * Input: Name of person who wants the intro, and the name of the other person,
	 * e.g. "sam" and "aparna" for the graph in the Background section.
	 * (Neither of these, nor any of the intermediate people are required 
	 * to be students, in the same school or otherwise.)
	 * Result: The shortest chain of people in the graph starting at the first 
	 * and ending at the second.
	 * Output: Print the chain of people in the shortest path.
	 */
	public void shortestPath(String intro, String end)
			throws Exception
	{

		//check for null input
		if(intro == null || end == null)
		{
			throw new Exception("Invalid input.");
		}

		//check that input users actually exist in graph
		if(nameToIndex.get(end) == null || nameToIndex.get(intro) == null)
		{
			throw new Exception("User is not in the graph.");
		}

		intro = intro.toLowerCase();
		end = end.toLowerCase();

		int endIndex;
		int frontPtr; 
		User user1; 
		Neighbor neigh;
		Queue q = new Queue();

		int [] distances = new int[adjList.size()];
		User [] path = new User[adjList.size()];

		for(int i=0; i<adjList.size(); i++) 
		{
			distances[i] = Integer.MAX_VALUE;
		}

		endIndex = nameToIndex.get(end);
		distances[endIndex] = 0;
		q.enqueue(adjList.get(endIndex));

		while(!q.isEmpty()) 
		{
			user1 = q.dequeue();
			neigh = user1.firstNeighbor;//first edge in the linked list

			while(neigh != null) 
			{
				if(distances[neigh.neighborIndex] == Integer.MAX_VALUE){
					distances[neigh.neighborIndex] = distances[nameToIndex.get(user1.name)] + 1;
					path[neigh.neighborIndex] = user1;
					q.enqueue(adjList.get(neigh.neighborIndex));
				}
				neigh = neigh.next;
			}
		}
		//throws exception if there is no path between the users
		if(distances[nameToIndex.get(intro)] == Integer.MAX_VALUE || intro.equals(end))
		{
			throw new Exception("No path exists between users.");
		}

		frontPtr = nameToIndex.get(intro);
		while(!end.equals(adjList.get(frontPtr).name)) 
		{
			System.out.print(adjList.get(frontPtr).name + "---");
			frontPtr = nameToIndex.get(path[frontPtr].name);
		}
		System.out.println(end);
	}
	
	/**
	 * Input: Name of school for which cliques are to be found, e.g. "rutgers"
	 * Result: The subgraphs for each of the cliques. 
	 * Output: Print the subgraph for each clique, in the same 
	 * format as the input described in the Graph build section. 
	 */
	public void islands(String school) 
	{

		if(school == null)
			return;

		Boolean[] visited = new Boolean[adjList.size()];
		ArrayList<String> vertices = new ArrayList<String>();
		ArrayList<String> edges = new ArrayList<String>();

		//initialize visited array to false
		for(int i=0; i<visited.length; i++) 
		{
			visited[i] = false;
		}

		cliqueNum = 0;
		for(int i=0; i<adjList.size(); i++) 
		{
			if(!visited[i]) 
			{
				islandsBFS(i, school, visited, vertices, edges);
			}
		}

		//return if the subgraph is empty
		if(vertices.isEmpty())
			return;
	}

	/**
	 * Helper method to accompany islands(). Uses BFS to traverse the graph and extract 
	 * the edges and vertices of the subgraph. Uses queue class to implement BFS.
	 */
	private void islandsBFS(int startIndex, String school, Boolean[] visited,
			ArrayList<String> arr1, ArrayList<String> arr2)
	{
		boolean alone=true;
		Queue queue = new Queue();
		User user1;
		User user2;
		User user3;
		Neighbor neigh;
		IslandEdges tmp= null; 
		IslandEdges front= null;

		//visit the first vertex and mark it as visited
		user1 = adjList.get(startIndex);
		if(!school.equals(user1.school)) 
		{
			visited[startIndex] = true;
			return;
		}

		//first vertex belongs to the school
		cliqueNum++;
		System.out.println("Clique " + cliqueNum);

		//user1 visited
		visited[startIndex] = true;
		queue.enqueue(user1);

		//continues checking the neighbors of objects in the queue
		while(!queue.isEmpty()) 
		{
			user2 = queue.dequeue();
			neigh = user2.firstNeighbor;

			while(neigh != null) 
			{
				alone=false;
				if(!visited[neigh.neighborIndex]) 
				{
		
					user3 = adjList.get(neigh.neighborIndex);

					if(!school.equals(user3.school)) 
					{
						visited[neigh.neighborIndex] = true;
						continue;
					}

					//neigh visited
					visited[neigh.neighborIndex] = true;

					//add neigh to the queue
					queue.enqueue(user3);

					//adjust linked list of edges to reflect the fact that user3 and user2 are neighbors
					if(front == null)
					{  
						front = new IslandEdges();
						front.edge = user3.name + "|" + user2.name;
					}
					else
					{
						tmp = front;
						front = new IslandEdges();
						front.edge= user3.name + "|" + user2.name;
						front.next = tmp;
					}
				}
				neigh = neigh.next;
			}
			
			if(alone)
			{
				front=new IslandEdges();
				front.edge=user2.name;
			}

		}
		//prints out the edges from the linked list
		tmp = front;
		while(tmp != null){
			System.out.println(tmp.edge);
			tmp = tmp.next;
		}
	}

	/**
	 * Input: Nothing 
	 * Result: Names of all people who are connectors in the graph 
	 * Output: Print names of all people who are connectors in the 
	 * graph, comma separated, in any order. 
	 */
	public void connectors() 
	{		
		dfsnum = new int[adjList.size()];// inside is the order given out. index is the place in adjList
		back = new int[adjList.size()];
		boolean[] visited = new boolean[adjList.size()];
		connectors=""; // have to reset for each call of connectors. otherwise lists them over and over again for each repeated choice
		
		for(int i=0;i<adjList.size();i++)
		{
			initial=i;
			dfs(visited,i);
		}
		
		for(int i=0;i<Connectors.length;i++)
		{
			if(Connectors[i]==false)
				continue;
			else
			{
				if(connectors.equals(""))
					connectors=connectors+adjList.get(i).name;
				else
					connectors=connectors+", "+adjList.get(i).name;
			}
		}
		System.out.println(connectors+" are connectors.");

	}
	
	
	private void dfs(boolean[] visited, int v)
	{
		visited[v]=true;
		dfsnum[v]=dfscount;
		back[v]=dfsnum[v];
		dfscount++;
		for(Neighbor ptr=adjList.get(v).firstNeighbor;ptr!=null;ptr=ptr.next)
		{
			int w=ptr.neighborIndex;
			if(!visited[w])
			{
				dfs(visited,w);
				if(dfsnum[v]>back[w])
				{
					//set to minumum
					if(back[w]<back[v])
						back[v]=back[w];
				}
				else
				{
					if(v!=initial)
						Connectors[v]=true;
					else
						if(adjList.get(v).firstNeighbor.next!=null)
							Connectors[v]=true;
				}
			}
			else
			{
				//set to minimum
				if(dfsnum[w]<back[v])
					back[v]=dfsnum[w];
			}
		}
	}
	

	/**
	 * Simple print method for printing out contents of the FriendGraph graph representation.
	 */
	public void printGraph() // much nicer output
	{

		if(adjList == null || adjList.isEmpty())
		{
			System.out.println("The graph is empty");
			System.out.println();
		}
		
		boolean[][] edges = new boolean[adjList.size()][adjList.size()];

		for(int i = 0; i < adjList.size(); i++) 
		{
			if(adjList.get(i).firstNeighbor==null)
			{
				System.out.println(adjList.get(i).name);
			}
			for(Neighbor ptr=adjList.get(i).firstNeighbor;ptr!=null;ptr=ptr.next)
			{
				
				if(!edges[i][ptr.neighborIndex])
				{
					System.out.println(adjList.get(i).name+"|"+adjList.get(ptr.neighborIndex).name);
					edges[i][ptr.neighborIndex]=true;
					edges[ptr.neighborIndex][i]=true;
				}
			}
		}
	}

	/**
	 * Simple print method for printing out contents of the hash map.
	 */
	public void printHash() {
		System.out.println(nameToIndex.entrySet());
	}
}
