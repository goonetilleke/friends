package Friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class Friends {
	// Scanner sc;
	Vertex[] adjLL;
	//might get rid of ppl 
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

	}

	// This method builds the adjacency Linked List
	public void build(String s, int i) {
		String input = s;
		String splitVal = "|";
		String[] subSplits = input.split(splitVal);
		students = new HashMap<String, ArrayList<String>>();
		ArrayList<String> temp=new ArrayList<String>();
		
 		if (i <= numVertex) { // adds vertex into adjLL until i == number from
								// 1st line
			String name = subSplits[0].toLowerCase();
			boolean inSchool;
			if(subSplits[1].equals("y")) {
				inSchool = true;
			} else {
				inSchool = false;
			}

			String school = null;
			if (inSchool == true) {
				school = subSplits[2];
			}
			if (inSchool==true){
			temp.add(name);
			students.put(school, temp);
			
			}
			Vertex v = new Vertex(name, inSchool, school, null);
			ppl.put(name, v); // puts vertex into hashtable, key is person name
			index.put(name, i); // puts index of person in adjLL, key is person
								// name
			adjLL[i] = v; // puts the node into the adjLL
		}

		//adding the neighbors to the adjLL
		String name1 = subSplits[0];
		String name2 = subSplits[1];

		int v1 = index.get(name1); //name1
		int v2 = index.get(name2); //name2

		adjLL[v1].neighbors = new Neighbor(name2, adjLL[v1].neighbors);
		adjLL[v2].neighbors = new Neighbor(name1, adjLL[v2].neighbors);

	}

	// This method will get the subgraph from the built adjLL
	public Vertex[] subgraph(String s) {
		Vertex[] subgraph = new Vertex[students.get(s).size()];
		if (students.containsKey(s)){
			//make temp array of arraylist in students hashtable
			ArrayList<String> temp = students.get(s);
			for(int i = 0; i <= temp.size(); i++){
				String key = temp.get(i);
				//int ind = index.get(key);
				//name, inschool, schoolname, nbr
				Vertex v = new Vertex(key, true, s, null);
				subgraph[i] = v;
			}
		} else{
			return null;
		}
		
		for(int j = 0; j<subgraph.length; j++){
			String name = subgraph[j].name;
			int curr = index.get(name);
			Neighbor ptr = adjLL[curr].neighbors; 
			while(ptr != null){
				String person = ptr.name;
				if(ppl.get(person).schoolName.equalsIgnoreCase(s)){
					if(subgraph[j].neighbors == null){
						subgraph[j].neighbors = ptr;
					}else{
						subgraph[j].neighbors.next = ptr; 
					}
				}
				ptr = ptr.next; 
			}
			
			
		}
		return adjLL;

	}

	// This method gets the shortest Path from two friends and returns the head
	// of the linked list
	public String shortestPath(String start, String end) {
		ArrayList<Integer> counters = new ArrayList<Integer>();
		ArrayList<Boolean> visited = new ArrayList<Boolean>();
		ArrayList<String> shortestPaths=new ArrayList<String>();
		Queue<String> q = new Queue<String>();
		boolean flag=false;
		
		int x = index.get(start);
		visited.set(x, true); 
		q.enqueue(start);
		while(!q.isEmpty()){
			String w = q.dequeue();
			int j = index.get(w);
			Neighbor ptr = adjLL[j].neighbors;
			while(ptr != null){
				if(!visited.get(x)){
					
					int temp = counters.get(x);
					temp = temp++;
					counters.set(x, temp); 
					int currIndex = index.get(ptr); //look over
					visited.set(currIndex, true);
					if (!(ptr.name.equals(end))){
						ptr.visitedNames=w;
						if (flag==false){
							flag=true;
							shortestPaths.set(currIndex, ptr.visitedNames);
						}
						else{
						shortestPaths.set(currIndex, "--".concat(ptr.visitedNames));
						}
					}
					String currName = ptr.name; 
					q.enqueue(currName);
					ptr = ptr.next;
				}
			}
			
		}
		int smallestPathIndex=0;
		for(int z = 0; z<counters.size(); z++){
			int smallestNum = counters.get(z);
			if(counters.get(z) < smallestNum){
				smallestPathIndex=z;
				smallestNum = counters.get(z);
				
			}
		}
		
		
		String shortestPath=shortestPaths.get(smallestPathIndex);
		
		return shortestPath;
		/*we have to add:
		 * if found, break
		 * adding the shortest path to a data structure
		 * 
		 */
	}

	// Gets the cliques from the original graph
	public Vertex[] cliques(String school, boolean[] visited) {
		Vertex[] temp = subgraph(school);
		Vertex[] result = new Vertex[100]; //change this later
		int v = 0; 
		int counter = 0; 
		dfs(v, visited, temp, result, school, counter);
		return result;

	}
	
	//ask if helper methods can return stuff and be public
	private void dfs(int v, boolean[] visited, Vertex[] subgraph, Vertex[] result, String school, int counter){
		
		visited[v] = true;
		for(Neighbor e = adjLL[v].neighbors; e != null; e = e.next){
			if(!visited[index.get(e.name)]){
				//name, inschool, schoolname, neighbor
				counter++; 
				result[v] = new Vertex(subgraph[v].name, true, school, e);
				dfs(index.get(e.name), visited, subgraph, result, school, counter);
			}
		}
		
	}

	// This method checks to see if a person is a "connecot" and then it returns
	// the name of that person.
	public String connectors() {
		return null;

	}
	
	private void printBuild(){
		Vertex[] temp = adjLL; 
		for(int i =0; i< temp.length; i++){			
			Neighbor ptr = temp[i].neighbors;
			while(ptr != null){
				System.out.println(temp[i] + "|" + ptr.name);
			}
		}
	}
}
