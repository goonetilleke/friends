package Friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Friends {
	// Scanner sc;
	Vertex[] adjLL;
	// might get rid of ppl
	HashMap<String, Vertex> ppl;
	HashMap<String, Integer> index;
	HashMap<String, ArrayList<String>> students;
	int numVertex;

	public Friends(int size) {
		// this.sc=sc;
		// initiallized the adjLL with the int from the 1st line read by the
		// scanner
		numVertex = size;
		adjLL = new Vertex[size];
		// initializes a new hashmap to keep track of people
		ppl = new HashMap<String, Vertex>(1000, 2.0f);
		index = new HashMap<String, Integer>(1000, 2.0f);
		students = new HashMap<String, ArrayList<String>>(1000, 2.0f);

	}

	// This method builds the adjacency Linked List
	public void build(String s, int i) {
		String input = s;
		String[] subSplits = input.split("\\|");

		if (i < numVertex) { // adds vertex into adjLL until i == number from
								// 1st line
			String name = subSplits[0].toLowerCase();
			boolean inSchool;
			if (subSplits[1].equals("y")) {
				inSchool = true;
			} else {
				inSchool = false;
			}

			String school = null;
			if (inSchool == true) {
				school = subSplits[2];
				if (!students.containsKey(school)) { // if school name is already in the hashtable
					ArrayList<String> temp = new ArrayList<String>();
					String nameForHT = name;
					temp.add(nameForHT);
					students.put(school, temp);

				} else {// if school name is not in the hashtable
					String nameForHT = name;
					students.get(school).add(nameForHT);
				}
			}
			Vertex v = new Vertex(name, inSchool, school, null);
			ppl.put(name, v); // puts vertex into hashtable, key is person name
			index.put(name, i); // puts index of person in adjLL, key is person
								// name
			adjLL[i] = v; // puts the node into the adjLL
			return;
		}

		// adding the neighbors to the adjLL
		String name1 = subSplits[0];
		String name2 = subSplits[1];

		int v1 = index.get(name1); // name1
		int v2 = index.get(name2); // name2

		adjLL[v1].neighbors = new Neighbor(name2, adjLL[v1].neighbors);
		adjLL[v2].neighbors = new Neighbor(name1, adjLL[v2].neighbors);

	}

	// This method will get the subgraph from the built adjLL
	public ArrayList<Vertex> subgraph(String s) {
		ArrayList<Vertex> subgraph = new ArrayList<Vertex>(students.get(s).size());

		if (students.containsKey(s)) {
			// make temp array of arraylist in students hashtable
			ArrayList<String> temp = students.get(s);
			for (int i = 0; i < temp.size(); i++) {
				String key = temp.get(i);
				// int ind = index.get(key);
				// name, inschool, schoolname, nbr
				Vertex v = new Vertex(key, true, s, null);
				subgraph.add(i, v);
			}
		} else {
			return null;
		}

		for (int j = 0; j < subgraph.size(); j++) {
			// String name = subgraph[j].name;
			String name = subgraph.get(j).name;
			int curr = index.get(name);
			Neighbor ptr = adjLL[curr].neighbors;
			while (ptr != null) {
				String person = ptr.name;
				if (ppl.get(person).inSchool == false) {
					ptr = ptr.next;
					continue;
				}
				if (ppl.get(person).schoolName.equalsIgnoreCase(s)) {
					subgraph.get(j).neighbors = new Neighbor(person, subgraph.get(j).neighbors); 
				}
				ptr = ptr.next;
			}

		}
		return subgraph;

	}

	// This method gets the shortest Path from two friends and returns the head
	// of the linked list
	public String shortestPath(String start, String end) {
		if(start.equalsIgnoreCase(end)){
			System.out.println("Entered same name twice");
			return null;
		}
		
		Vertex[] copy = adjLL; 
		String soFar = ""; 
		boolean[] visited = new boolean[copy.length];
		Queue<String> q = new Queue<String>();
		String prev = start; 
		boolean flag = false; 
		int x = index.get(start);
		visited[x] = true;
		q.enqueue(start);
		while (!q.isEmpty()) {
			String curr = q.dequeue();
			int indexInAdjLL = index.get(curr);
			if(flag == false){
				flag = true; 
				copy[indexInAdjLL].path = copy[indexInAdjLL].path.concat(curr).trim(); 
			}else{
				copy[indexInAdjLL].path = copy[indexInAdjLL].path.concat(soFar + "+" + curr).trim();
			}
			soFar = copy[indexInAdjLL].path; 
			Neighbor ptr = copy[indexInAdjLL].neighbors;
			while (ptr != null) {
				//if neighbor is not visited
				int ptrIndex = index.get(ptr.name); 
				if (visited[ptrIndex]==false) {
					visited[ptrIndex] = true; 
					if ((ptr.name.equalsIgnoreCase(end))) {
						System.out.println("FOUND"); 
						copy[index.get(ptr.name)].path = copy[index.get(ptr.name)].path.concat(soFar + "+" + ptr.name).trim();
						return copy[index.get(ptr.name)].path; 
					}
					String currName = ptr.name;
					q.enqueue(currName);
				}
				ptr = ptr.next;
			}

		}

		return null;
		/*
		 * we have to add: if found, break adding the shortest path to a data
		 * structure
		 */
	}

	// Gets the cliques from the original graph
	public ArrayList<Vertex> cliques(String school) {
		ArrayList<Vertex> temp = subgraph(school);
		ArrayList<Vertex> result = new ArrayList<Vertex>(); // change this later
		boolean[] ret = new boolean[temp.size()];
		boolean[] visit = new boolean[temp.size()]; 
		HashMap<String, Integer> subgraphIndex = new HashMap<String, Integer>(1000, 2.0f); 
		
		//Makes a hashmap for the subgraph. We can easily access a person's index in the subgraph now. 
		for(int i=0; i<temp.size(); i++){
			subgraphIndex.put(temp.get(i).name, i);
			//name, inschool, schoolname, nbr
			Vertex vert = new Vertex(temp.get(i).name, true, school, null); 
			result.add(vert); 
		}
		
		//goes through every vertex and calls dfs
		for (int v=0; v < visit.length; v++) {
			if (!visit[v]) {
				dfs(v, visit, temp, result, school, ret, subgraphIndex);
			}
		}
		//dfsDriver(); 
		return result;

	}

	private void dfs(int v, boolean[] visit, ArrayList<Vertex> temp,
			ArrayList<Vertex> result, String school, boolean[] ret, HashMap<String, Integer> subgraphIndex) {
		visit[v] = true;
		for (Neighbor e = temp.get(v).neighbors; e != null; e = e.next) {
			//check this if statement
			if (!visit[subgraphIndex.get(e.name)]) {
				result.get(v).neighbors = new Neighbor(e.name, result.get(v).neighbors);
				dfs(subgraphIndex.get(e.name), visit, temp, result, school, ret, subgraphIndex);
			}
		}
	}
	
	// This method checks to see if a person is a "connector" and then it returns
	// the name of that person.
	public String connectors() {
		ArrayList<Boolean> visitedConnectors=new ArrayList<Boolean>();
		Vertex[] adjLLtemp=adjLL;
		dfsConnectors(1, visitedConnectors, adjLLtemp);
		return null;

	}
	public void dfsConnectors(int x, ArrayList<Boolean> visitedConnectors, Vertex[] adjacentLL) {
		
		for (int v=0; v < visitedConnectors.size(); v++) {
			visitedConnectors.set(v,false);
			
		}
		for (int v=0; v < visitedConnectors.size(); v++) {
			if (!visitedConnectors.get(v)) {
				adjacentLL[v].dfsNum=v;
				adjacentLL[v].back=v;
				dfsConnectors(v, visitedConnectors, adjacentLL);
			}
		}
	}
	//
	private void dfsDriver(boolean[] visited) {
		for(int i = 0; i<visited.length; i++){
			if(!visited[i]){
				dfs(i, visited, null, null, null, i); 
			}
		}
	}

	private void printBuild() {
		Vertex[] temp = adjLL;
		for (int i = 0; i < temp.length; i++) {
			Neighbor ptr = temp[i].neighbors;
			while (ptr != null) {
				System.out.println(temp[i] + "|" + ptr.name);
			}
		}
	}
}
