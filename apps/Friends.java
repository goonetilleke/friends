package Friend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FriendsGraph {
	// Scanner sc;
	Vertex[] adjLL;
	// might get rid of ppl
	HashMap<String, Vertex> ppl;
	HashMap<String, Integer> index;
	HashMap<String, ArrayList<String>> students;
	int numVertex;

	public FriendsGraph(int size) {
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

		if (i < numVertex) { // adds vertex into adjLL until i == number from 1st line
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
		if (!students.containsKey(s)){
			return null;
		}
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
					subgraph.get(j).neighbors = new Neighbor(person,subgraph.get(j).neighbors);
				}
				ptr = ptr.next;
			}

		}

		return subgraph;

	}


	// This method gets the shortest Path from two friends and returns the head
	// of the linked list
	public String shortestPath(String start, String end) {
		if (start.equalsIgnoreCase(end)) {
			System.out.println("Entered same name twice");
			return null;
		}
		Queue q = new Queue(); 
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>(); 
		HashMap<String, String> prev = new HashMap<String, String>(); 
		String curr = start;
		q.enqueue(curr);
		while(!q.isEmpty()){
			curr = (String) q.dequeue();
			if(curr.equalsIgnoreCase(end)){
				break;
			}else{
				int ptrIndex = index.get(curr); 
				Neighbor ptr = adjLL[ptrIndex].neighbors;
				while(ptr != null){
					if(!visited.containsKey(ptr.name)){
						q.enqueue(ptr.name);
						visited.put(ptr.name, true);
						prev.put(ptr.name, curr); 
					}
					ptr = ptr.next; 
				}//end of inner while
			}
		}//end of outer while

		if(!curr.equalsIgnoreCase(end)){
			return null;
		}

		ArrayList<String> path = new ArrayList<String>(); 
		String key = prev.get(end);
		path.add(end); 
		while(!key.equalsIgnoreCase(start)){
			path.add(key);
			System.out.println(key);
			if(!prev.get(key).equalsIgnoreCase(start)){
				String tmp = key;
				key = prev.get(tmp);
				
			}
			if(prev.get(key).equalsIgnoreCase(start)){
				path.add(key);
				break; 
			}
		}
		String result = ""; 
		path.add(start);  
		for(int j = path.size()-1; j>= 0; j--){
			if(j==0){
				result = result.concat(path.get(j)); 
			}else{
				result = result.concat(path.get(j) + "--").trim();
			}

		}
		//System.out.println(result);
		return result;
	}

	// Gets the cliques from the original graph
	public void cliques(String school) {
		ArrayList<Vertex> cliques = new ArrayList<Vertex>();
		ArrayList<Vertex> subgraph = subgraph(school);
		//subgraph.retainAll(Collections.singleton(null)); 
		int count = 1; 
		//System.out.print(subgraph.size()); 
		HashMap<String, Integer> subgraphIndex = new HashMap<String, Integer>();
		HashMap<String, Neighbor> nbr = new HashMap<String, Neighbor>(); 
		int cliqueNum = 0; 
		int start = 0; 
		for(int i = 0; i < subgraph.size(); i++){
			subgraphIndex.put(subgraph.get(i).name, i);
			nbr.put(subgraph.get(i).name, subgraph.get(i).neighbors); 
		}
		boolean[] visited = new boolean[subgraph.size()]; 
		for(int v = 0; v<visited.length; v++){
			if(!visited[v]){
				//System.out.println("Starting at " + subgraph.get(v).name);
				if(start==0){
					Vertex a = new Vertex(subgraph.get(v).name, true, school, null); 
					cliques.add(start, a); 
					cliques.get(start).neighbors = nbr.get(a.name); 
					start++; 
				}
				cliquesDFS(school, v, visited, subgraph, cliques, subgraphIndex, nbr, count);
				//print the clique
				//remove all entries from clique once printed
				cliqueNum++;
				printClique(cliques, cliqueNum, count); 
				start = start -1; 
			}
		}
	}

	private void printClique(ArrayList<Vertex> clique, int cliqueNum, int count){
		System.out.println();
	//	cliqueNum=cliqueNum+2;
		//System.out.println(cliqueNum);
		System.out.println("Clique " + cliqueNum + ": ");
		HashMap<String, String> peopleinSubgraph = new HashMap<String, String>(1000, 2.0f);
		clique.trimToSize(); 

		for (int i=0; i < clique.size(); i++){
			String name=clique.get(i).name;
			String neighbor=null;
			if (clique.get(i).neighbors!=null){
			neighbor=clique.get(i).neighbors.name;
			}
			String temp=name+neighbor;
			if (!peopleinSubgraph.containsValue(temp)){
			peopleinSubgraph.put(temp,temp);
			}
			String opp=neighbor+name;

			if (peopleinSubgraph.containsValue(opp)){
				//do nothing
			}else{
				//if (subgraph.get(i).neighbors==null){

				//}else{
				System.out.println(name+"|"+neighbor);
				}
		}//end of for loop
		count = 1; 
		clique.clear(); 
		


	}

	private void cliquesDFS(String school, int v, boolean[] visited, ArrayList<Vertex> subgraph, ArrayList<Vertex> cliques, 
			HashMap<String, Integer> ind, HashMap<String, Neighbor> nbr, int count){

		visited[v] = true; 
		//System.out.println("visiting " + subgraph.get(v).name);
		for(Neighbor e = subgraph.get(v).neighbors; e != null; e=e.next){
			if(!visited[ind.get(e.name)]){
				//System.out.println(subgraph.get(v).name + "--" + subgraph.get(ind.get(e.name)).toString());
				//name, inschool, school, nbrs
				Vertex a = new Vertex(e.name, true, school, null); 
				cliques.add(a); 
				//subgraph.get(j).neighbors = new Neighbor(person,subgraph.get(j).neighbors);
				int h = ind.get(a.name); 
				cliques.get(count).neighbors = nbr.get(a.name);
				count++;
				cliquesDFS(school, ind.get(e.name), visited, subgraph, cliques, ind, nbr, count); 
			}
		}
	}



	// This method checks to see if a person is a "connector" and then it
	// returns
	// the name of that person.
	public String connectors() {
		boolean[] visited = new boolean[adjLL.length]; 
		HashMap<String, Boolean> connectors = new HashMap<String, Boolean>(); 
		ArrayList<String> c = new ArrayList<String>(); 
		Vertex[] copy = adjLL;
		int startIndex = 0;
		for(int v =0; v<visited.length;v++){
			if(!visited[v]){
				dfs(v, visited, connectors, copy, c); 
			}
		}

		if(copy[startIndex].back == copy[startIndex].dfsNum){
			dfs(startIndex+1, visited, connectors, copy, c); 
		}

		return null;
	}

	private void dfs(int v, boolean[] visited, HashMap<String, Boolean> connectors , Vertex[] copy, ArrayList<String> c){
		visited[v] = true; 
		copy[v].dfsNum = v+1; 
		copy[v].back  = copy[v].dfsNum; 
		for(Neighbor e = copy[v].neighbors; e != null; e=e.next){
			if(!visited[index.get(e.name)]){
				dfs(index.get(e.name), visited, connectors, copy, c);
			}else{
				int w = index.get(copy[v].neighbors.name); 
				copy[w].back = Math.min(copy[v].back, copy[w].dfsNum);
			}

			if(copy[v].dfsNum > copy[v].neighbors.DFSNum){
				int w = index.get(copy[v].neighbors.name); 
				copy[v].back = Math.min(copy[v].back, copy[w].back);
			}

			int w = index.get(copy[v].neighbors.name);
			if(copy[v].dfsNum <= copy[w].back){
				if(c.contains(copy[v].name)){
					System.out.println(copy[v].name + "lready in there"); 
				}else{
					c.add(copy[v].name);
				}

			}
		}

	}
	public void printSubgraph(ArrayList<Vertex> subgraph){

		//HashMap<String, String> peopleinSubgraph = new HashMap<String, String>(1000, 2.0f);
		//HashMap<String[], Boolean> visited=new HashMap<String[], Boolean>(1000,2.0f);
		HashMap<String, String> peopleinSubgraph = new HashMap<String, String>(1000, 2.0f);
		//String[] subgrapNames=new String[subgraph.size()];
	//	boolean oppositeExists=false; 

		for (int i=0; i<subgraph.size(); i++){
			String name=subgraph.get(i).name;
			String neighbor=null;
			if (subgraph.get(i).neighbors!=null){
			neighbor=subgraph.get(i).neighbors.name;
			}
			String temp=name+neighbor;
			if (!peopleinSubgraph.containsValue(temp)){
			peopleinSubgraph.put(temp,temp);
			}
			String opp=neighbor+name;

			if (peopleinSubgraph.containsValue(opp)){
				//do nothing
			}else{
				//if (subgraph.get(i).neighbors==null){

				//}else{
				System.out.println(name+"|"+neighbor);
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
