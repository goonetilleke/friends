package Friend;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Friends {
	// Scanner sc;
	Vertex[] adjLL;

	HashMap<String, Vertex> ppl;
	HashMap<String, Integer> index;
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
		if (i <= numVertex) { // adds vertex into adjLL until i == number from
								// 1st line
			String name = subSplits[1].toLowerCase();
			boolean inSchool;

			if (subSplits[2].equals("y")) {
				inSchool = true;
			} else {
				inSchool = false;
			}

			String school = null;
			if (inSchool == true) {
				school = subSplits[3];
			}

			Vertex v = new Vertex(name, inSchool, school, null);
			ppl.put(name, v); // puts vertex into hashtable, key is person name
			index.put(name, i); // puts index of person in adjLL, key is person
								// name
			adjLL[i] = v; // puts the node into the adjLL
		}
		
		//adding the neighbors to the adjLL
		String name1 = subSplits[1];
		String name2 = subSplits[2];
		
		int v1 = index.get(name1); //name1
		int v2 = index.get(name2); //name2
		
		adjLL[v1].neighbors = new Neighbor(name2, adjLL[v1].neighbors);
		adjLL[v2].neighbors = new Neighbor(name1, adjLL[v2].neighbors);

	}

	// This method will get the subgraph from the built adjLL
	public Vertex[] subgraph() {
		return adjLL;

	}

	// This method gets the shortest Path from two friends and returns the head
	// of the linked list
	public Vertex shortestPath() {
		return null;

	}

	// Gets the cliques from the original graph
	public Vertex[] cliques(String school) {
		return adjLL;

	}

	// This method checks to see if a person is a "connecot" and then it returns
	// the name of that person.
	public String connectors() {
		return null;

	}
}
