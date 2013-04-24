package Friend;
 
import java.io.*;
import java.util.Scanner;

public class Friends{
  //Scanner sc;
  Vertex[] adjLL; 
  
  public Friends(){
    //this.sc=sc;
      //initiallized the adjLL with the int from the 1st line read by the scanner
      adjLL = new Vertex[15]; 

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

      }

      //This method builds the adjacency Linked List
      public void build(Vertex friend){
  
  
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
	
