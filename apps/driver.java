import java.io.BufferedReader;

public class driver{
    
    public staatic void main(String[] args)
    throws IOExcepetion{
      
      Friends friend = new friend();
      
      BufferedReader br = new BufferReader(new InputStreamReader(System.in));
      System.out.print("Enter text file name");
      
      Scanner sc = new Scanner(new File(br.readLine()));
      
      while ((choice = getChoice()) != 5) {
    		switch(getChoice()) {
			case 1: subgraph(); break;
			case 2: shortestPath(); break;
			case 3: cliques(); break;
			case 4: connectors(); break;
			}
		}
    
      /*This method will call the make vertex method for each line
      *The make vertex method should turn the string into a node and then input the node into the adjLL
      */
      
      int size = sc.nextInt(); 
      for(int i = 1; i<size; i++){
          friend.makeVertex(sc);
          System.out.println("sending scanner for loop");
      }
      
      public static int getChoice(){
      throws IOException {
          System.out.println();
          System.out.println("1. subgraph");
          System.out.println("2. shortest path");
          System.out.println("3. cliques");
          System.out.println("4. connectors");
          System.out.println("5. to quit");
          System.out.print("\tEnter Choice => ");
    	  Integer n = Integer.parseInt(br.readLine());
          while (n < 1 || n > 5) {
    		System.out.print("\tYour choice must be between 1 and 5, reenter => ");
		  }
		    System.out.println();
		    return n;
      }   
  }
}
