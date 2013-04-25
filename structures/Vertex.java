package Friend;

public class Vertex {

  String name;
	boolean inSchool;
	String schoolName;
	boolean visited;
	int dfsNum;
	Neighbor neighbors;
	//int index; 

	public Vertex(String name, boolean inSchool, String schoolName, Neighbor nbr) {
		this.name = name;
		this.inSchool = inSchool;
		this.schoolName = schoolName;
		//this.next = next;
		this.dfsNum = -1;
		this.visited = false;
		//this.index = -1; 
		this.neighbors = nbr; 
		
	}
}
