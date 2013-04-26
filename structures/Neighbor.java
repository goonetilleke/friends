package Friend;

public class Neighbor {
	public String name;
	public String school;
	public String inSchool; 
	public Neighbor next;

	public Neighbor(String name, Neighbor nbr) {
		this.name = name;
		next = nbr;
	}
}
