package Project4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
* COP 3530: Project 4 – Binary Search Trees
* <p>
* This program reads in a csv file that contains 153 countries and their respective data: Name, Capitol, GDP, Population, Covid-19 Cases, Covid-19Deaths.
* When the data is read in line by line using a buffered reader, the line's data is stored in a string array so that the specific indices can be assigned easily
* to variables inside a while loop. The while loop will iterate until the exit condition is reached, and the inner while loop will iterate until the buffered reader hits a null line.
* After the data is read in, line by line, country's names and cfr are inserted into the binary search tree and are converted into nodes once inserted. Then the tree manipulation and accessing begins.
* It starts with printing the nodes after being traversed inorder. Then 3 countries are deleted and then the nodes are printed preorder. Once that is done, 4 countries are searched for by name, if they are
* found, then the message will display that they were found and it will display their cfr as well. If not found, then it will print not found. For all of these however, a message will be printed that
* states how many nodes were visited. Afterwards, 5 more countries are deleted. Then is prints the results of a postorder traversal and prints the bottom 20 countries based on cfr and the top 20 countries
* based on cfr.
* <p>
* The main function of this program is to create a binary search tree and implement many common methods associated with them, like find, delete, and insert. We also needed methods to implement traversing
* inorder, postorder, and preorder. We also were tasked to implement 2 methods, on was to find the 20 countries with the lowest Covid Fatality Rate(CFR) and the 20 countries with the highest
* Covid Fatality Rate.
* 
* @author Nathanial Smith
* @version 11/15/2020
*/
public class Project4 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("COP3530 Project4\nName: Nathanial Smith N01232886\nInstructor: Xudong Liu\nBinary Search Tree\n");
		System.out.printf("Enter the name of the file: ");
		
		BinarySearchTree tree = new BinarySearchTree();
		
		BufferedReader read = null;
		String split = ",";
		String line = "";
		
		
		String name;
		Double cfr, deaths2, cases2;
		Long cases, deaths;
		
		int i = 0;
		while(i == 0) {
			
			String file = input.nextLine();
			try {
			
				read = new BufferedReader(new FileReader(file));
				read.readLine();
				while ((line = read.readLine()) != null) {
					String[] countries = line.split(split);
				
					
					name = countries[0];
					cases = Long.parseLong(countries[4]);
					deaths = Long.parseLong(countries[5]);
					
					deaths2 = (double)deaths;
					cases2 = (double)cases;
					cfr = deaths2 / cases2;
					
					tree.insert(name, cfr);
					i++;
				}
				
				 
				
				read.close();
				input.close();
			}
			
			catch(FileNotFoundException e) {
			System.out.println("Can't open file");
			System.out.print("Enter the name of the file: ");
			}
			
			catch(IOException e) {
			System.out.println("Problem with reading file");
			}
		}
		
		System.out.println("\nInorder Traversal:\n");
		System.out.println("Name                                    CFR\r\n" + 
				"------------------------------------------------");
		tree.printInorder();
		
		
		Double value;
		String searchName;
		System.out.println("\n");
		searchName = "Greece";
		tree.delete(searchName);
		searchName = "Mongolia";
		tree.delete(searchName);
		searchName = "Norway";
		tree.delete(searchName);
		
		
		System.out.println("\nPreorder Traversal:\n");
		System.out.println("Name                                    CFR\r\n" + 
				"------------------------------------------------");
		tree.printPreorder();
		
		
		searchName = "Mongolia";
		value = tree.find(searchName);
		if(value == -1) {
			System.out.println(searchName + " not found");
		} else {
			System.out.printf("%s has been found with a CFR of: %.6f\n",searchName, value);
		}
		
		searchName = "Cyprus";
		value = tree.find(searchName);
		if(value == -1) {
			System.out.println(searchName + " not found");
		} else {
			System.out.printf("%s has been found with a CFR of: %.6f\n",searchName, value);
		}
		
		searchName = "United States";
		value = tree.find(searchName);
		if(value == -1) {
			System.out.println(searchName + " not found");
		} else {
			System.out.printf("%s has been found with a CFR of: %.6f\n",searchName, value);
		}
		
		searchName = "Norway";
		value = tree.find(searchName);
		if(value == -1) {
			System.out.println(searchName + " not found\n");
		} else {
			System.out.printf("%s has been found with a CFR of: %.6f\n",searchName, value);
		}
		
		
		searchName = "Qatar";
		tree.delete(searchName);
		searchName = "Somalia";
		tree.delete(searchName);
		searchName = "Canada";
		tree.delete(searchName);
		searchName = "Yemen";
		tree.delete(searchName);
		searchName = "New Zealand";
		tree.delete(searchName);
		
		
		System.out.println("\n\nPostorder Traversal:\n");
		System.out.println("Name                                    CFR\r\n" + 
				"------------------------------------------------");
		tree.printPostorder();
		
		
		System.out.println("\n\nBottom Twenty Countries Based on CFR\n\nName                                    CFR\r\n" + 
				"------------------------------------------------");
		tree.printBottomTwenty();
		
		
		System.out.println("\n\nTop Twenty Countries Based on CFR\n\nName                                    CFR\r\n" + 
				"------------------------------------------------");
		tree.printTopTwenty();
	}
}