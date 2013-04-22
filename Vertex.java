public class Vertex{
  
  String name;
  boolean inSchool;
  String schoolName;
  boolean visited;
  int dfsNum;
  Node next;
  
  public Vertex(String name, String inSchool, String schoolName, Node next){
    this.name = name; 
    this.inSchool = inSchool;
    this.schoolName = schoolName;
    this.next = next;
    this.dfsnum = -1;
    this.visited = false; 

  }
}
