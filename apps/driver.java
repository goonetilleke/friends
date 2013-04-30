package Friend;

import java.io.*;
import java.util.Scanner;

public class driver {

	static BufferedReader br;
	private static Scanner sc;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter text file name");

		sc = new Scanner(new File(br.readLine()));

		/*
		 * This method will call the make vertex method for each lineThe make
		 * vertex method should turn the string into a node and then input the
		 * node into the adjLL
		 */
		Friends friend = null;
		boolean flag = false;
		int counter = 0;
		while (sc.hasNextLine()) {
			if (flag == false) {
				flag = true;
				String size = sc.nextLine();
				int val = Integer.parseInt(size);
				friend = new Friends(val);
			}

			String s = sc.nextLine();
			System.out.println(s);
			friend.build(s, counter);
			counter++;
		}
		br = new BufferedReader(new InputStreamReader(System.in));
		int choice;
		while ((choice = getChoice()) != 5) {
			switch (getChoice()) {
			case 1:// subgraph
				System.out.println("Enter school");
				Scanner sc1 = new Scanner(System.in);
				String input = sc1.nextLine();
				friend.subgraph(input);
				break;
			case 2:// shortest path
				System.out.println("Enter start name");
				Scanner sc2 = new Scanner(System.in);
				String input1 = sc2.nextLine();
				System.out.println("Enter ending name");
				String input2 = sc2.nextLine();
				friend.shortestPath(input1, input2);
				break;
			// check cliques later!!!!!!!!!!!!!!!!!
			case 3: // cliques
				friend.cliques(null, null);
				break;
			case 4:// connectors
				friend.connectors();
				break;
			}
		}
	}

	public static int getChoice() throws NumberFormatException, IOException {
		System.out.println();
		System.out.println("1. subgraph");
		System.out.println("2. shortest path");
		System.out.println("3. cliques");
		System.out.println("4. connectors");
		System.out.println("5. to quit");
		System.out.print("\tEnter Choice => ");
		Scanner in = new Scanner(System.in);
		int n = in.nextInt(); 
		while (n < 1 || n > 5) {
			System.out.print("\tYour choice must be between 1 and 5, reenter => ");
		}
		System.out.println();
		return n;
	}
}
