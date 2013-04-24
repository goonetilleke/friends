package Friend;
 
import java.io.*;
import java.util.Scanner;

public class Friends{
  //Scanner sc;
  Vertex[] adjLL; 
  
  HashMap<String, Vertex> ppl; 
  
  public Friends(int size){
    //this.sc=sc;
      //initiallized the adjLL with the int from the 1st line read by the scanner
      adjLL = new Vertex[size]; 
      //initializes a new hashmap to keep track of people
      ppl = new HashMap<String, Vertex>(1000, 2.0f);

  }
      //This method will take the input from the scanner and put it into a node.
      //After creating a node, we should call the build method to build the adjLL.
      //So every time we make a new node we will insert it into the adjLL
      //w.c big O = O(n) i think...
      public void makeVertex(String s){
    	 String input=s;
    	 String splitVal="|";
    	 String[] subSplits =  input.split(splitVal);
    	 String name= subSplits[1];
    	 boolean inSchool;
    	 if (subSplits[2].equals("y")){
    		 inSchool=true;
    	 } else{
    		 inSchool=false;
    	 }
    	 String school=null;
    	 if (inSchool==true){
    		school= subSplits[3];
    	 }
    	  Vertex v=new Vertex(name, inSchool,school);
    	  //calls build to fill the adjLL with the vertexs. 
    	  //We have to figure out what to do with the relationships though.
    	  //Maybe have a hastable that has the person's name and then a vertex object of all their
    	  //information like what school they go to, etc.
    	  build(v);
 
      }

      //This method builds the adjacency Linked List
      public void build(Vertex friend){
  
  	//gets vertex and fills adjLL with the name of each person
  	for(int i = 0; i<adjLL.length; i++){
  		adjLL[i] = friend;
  	}
      }
      
      //This method will get the subgraph from the built adjLL
      public  Vertex[] subgraph(){
		return adjLL;
        
      }
      
      //This method gets the shortest Path from two friends and returns the head of the linked list
      public Vertex shortestPath(){
		return null;
        
      }
      
      //Gets the cliques from the original graph
      public Vertex[] cliques(String school){
		return adjLL;
        
      }
      
      //This method checks to see if a person is a "connecot" and then it returns the name of that person.
      public String connectors(){
		return null;
        
      }
      }
	
