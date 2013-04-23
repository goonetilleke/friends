package Friend;
public class Vertex{
  
  String name;
  boolean inSchool;
  String schoolName;
  boolean visited;
  int dfsNum;
  Vertex next;
  
  public Vertex(String name, boolean inSchool, String schoolName, Vertex next){
    this.name = name; 
    this.inSchool = inSchool;
    this.schoolName = schoolName;
    this.next = next;
    this.dfsNum = -1;
    this.visited = false; 

  }
}
