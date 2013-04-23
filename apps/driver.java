import java.io.BufferedReader;

public class driver{
    
    public staatic void main(String[] args)
    throws IOExcepetion{
      
      Friends friend = new friend();
      
      BufferedReader br = new BufferReader(new InputStreamReader(System.in));
      System.out.print("Enter deck file name");
      
      Scanner sc = new Scanner(new File(br.readLine()));
    
      /*This method will call the make vertex method for each line
      *The make vertex method should turn the string into a node and then input the node into the adjLL
      */
      while(sc.hasNext(){
         friend.makeVertex(sc.next());
      }
  }
}
