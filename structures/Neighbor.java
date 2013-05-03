package Friend;

public class Neighbor {
	public String name;
	//public String school;
	//public String inSchool; 
	public Neighbor next;
	String visitedNames;
	int DFSNum;
	int back;
	String path; 

	public Neighbor(String name, Neighbor nbr) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		this.name = name;
		this.next = nbr;
		this.visitedNames=null;
		this.DFSNum=0;
		this.back=0;
		this.path = ""; 
	}
}
