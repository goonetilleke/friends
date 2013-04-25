package Friend;

public class Neighbor {
  public String name;
	public Neighbor next;

	public Neighbor(String name, Neighbor nbr) {
		this.name = name;
		next = nbr;
	}
}
