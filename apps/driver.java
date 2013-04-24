package Friend;

import java.io.*;
import java.util.Scanner;

public class driver {
	
	static BufferedReader br; 

	public static  void main(String[] args)
    	throws IOException{
      
      
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	      System.out.print("Enter text file name");
	      
	      Scanner sc = new Scanner(new File(br.readLine()));
	    
	      /*This method will call the make vertex method for each line
	      *The make vertex method should turn the string into a node and then input the node into the adjLL
	      */
	      int size = sc.nextInt(); 
	      //I put friends after so we can take the size given and put it as a paremeter 
	      Friends friend = new Friends(size);
	      for(int i = 1; i<size; i++){
	      	//take next line from scanner as parameter so its a string and not giving the scanner hope its ok
	          friend.makeVertex(sc.nextLine());
	          System.out.println("sending scanner for loop");
	      }
	      
	      br = new BufferedReader(new InputStreamReader(System.in));
	      int choice;
	  	  while ((choice = getChoice()) != 5) {
	  		  switch(getChoice()) {
	  		  case 1: friend.subgraph(); break;
	  		  case 2: friend.shortestPath(); break;
	  		  //check cliques later!!!!!!!!!!!!!!!!!
	  		  case 3: friend.cliques(null); break;
	  		  case 4: friend.connectors(); break;
	  		  }
	  	  }
		}
	  	
      
      public static int getChoice() throws NumberFormatException, IOException{
          System.out.println();
          System.out.println("1. subgraph");
          System.out.println("2. shortest path");
          System.out.println("3. cliques");
          System.out.println("4. connectors");
          System.out.println("5. to quit");
          System.out.print("\tEnter Choice => ");
    	  int n = Integer.parseInt(br.readLine());
          while (n < 1 || n > 5) {
    		System.out.print("\tYour choice must be between 1 and 5, reenter => ");
		  }
		    System.out.println();
		    return n;
      }
	}
    
