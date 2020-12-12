package Project4;

/**
* This class is designed to act as a binary search tree. It offers many common methods such as find, delete, and insert. It allows for preorder, inorder, and postorder traversal.
* It has a Node class so that it can function, and it has two custom methods called printBottomTwenty and printTopTwenty where the bottom twenty Nodes and the top twenty Nodes respectively are 
* found recursively and stored in Node arrays and then printed.
*
* @author Nathanial Smith
* @version 11/15/2020
*/
public class BinarySearchTree {
	/**
	* This is the Node class, It acts as the node for the binary search tree class. It has 4 qualities/values, name, CFR, leftchild, and rightchild. The first two is the data that the "node" stores and 
	* the leftchild and rightchild act as pointers to their respective "nodes". The constructor takes in the data and creates a node that stores that data. It also has a method that prints the node data in a
	* specific format.
	*
	* @author Nathanial Smith
	* @version 11/13/2020
	*/
	private class Node {
		String name;
		double CFR;
		Node leftChild;
		Node rightChild;
		
		/**
		* This is the constructor of the Node class and it is used to create the Node objects that will be used create the binary search tree. It takes in two parameters, name and CFR, that are then
		* used in the constructor to update this Node's name and CFR.
		*
		* @param name - acts as the data used to represent the name of the country data that is being passed into this Node.
		* @param CFR - acts as the data used to represent the CFR of the country data that is being passed into this Node.
		*/
		public Node(String name, double CFR) {
			this.name = name;
			this.CFR = CFR;
		}
		
		/**
		*This method prints the Node data for name and CFR in a formated manner that allows the Nodes to be printed one after the other in a clean formatted way.
		*/
		public void printNode() {
		System.out.printf("%-40s%,-30.6f\n", name, CFR);
		}
	}
	private Node root;
	private Node[] array1 = new Node[20];
	private Node[] array2 = new Node[20];
	
	private int counting;
	
	/**
	*This is the constructor for the BinarySearchTree class, this allows a BinarySearchTree object to be created and it sets the value of root == to null so that the tree starts of "empty".
	*/
	BinarySearchTree() {
		root = null;
	}
	
	/**
	* This method passes in the name and CFR of the country and that will be used to create a new Node for the binary search tree. Once the node is created the node then needs to be inserted into the tree.
	* First if the root is == to null, we know the tree is empty and we can set the root == to the node we just created. If it's not null then we check if the name of the node we just created is less than
	* the "current" name. And that keeps happening until null is hit or the value is greater than or equal to at which point it starts going right and the loop may repeat until a null value is hit. Once the null value
	* is hit, the node that was made is set == either the parent.leftchild or the parent.rightchild, which ever direction the Node just traversed from.
	* 
	* @param name - acts as the data used to represent the name of the country data that is being passed into the Node that is being created.
	* @param CFR - acts as the data used to represent the CFR of the country data that is being passed into the Node that is being created.
	*/
	public void insert(String name, double CFR) {
		Node node = new Node(name, CFR);
		if(root == null) {
			root = node;
		} else {
			Node current = root;
			Node parent;
			while(true) {
				parent = current;
				if(current.name.compareToIgnoreCase(name) > 0) { //go left
					current = current.leftChild;
					if(current == null) {
						parent.leftChild = node;
						return;
					}
				} else { //go right
					current = current.rightChild;
					if(current == null) {
						parent.rightChild = node;
						return;
					}
				}
			}
		}
	}
	
	/**
	* This method's job is to find a specific Node based on the name associated with that Node. It starts by setting the current node == to the root so we start at the top of the tree. 
	* then we create a counter so we can keep track of the number of nodes visited. We the n enter a while loop and ensure that the name passed in is not == to the current Node which is the root node.
	* If it was, then it would just return the root's CFR and since no nodes were visited, the counter would remain at 0. What the while loop does is check each node until the correct node is found, or if null is reached
	* and if null is reached, -1 is returned so that an error message can be made and since no CFR can be negative. If it is found then the CFR is returned. Regardless of whether the Node was found, the number of Nodes visited is 
	* printed.
	* 
	* @param name - the name being searched for that is compared to the "name" value of Node. 
	* @return -1 - if the Node is not found, -1 is returned so it can be used as a marker and because there cannot be a negative CFR.
	* @return current.CFR - this is returned because this is the node at which the correct Node is, and the value that is needed is the CFR.
	*/
	public double find(String name) {
		Node current = root;
		int counter = 0;
		while(current.name.compareToIgnoreCase(name) != 0) {
			counter++;
			if(0 < current.name.compareToIgnoreCase(name)) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
			if(current == null) {
				System.out.println("\nNodes visited:" + counter);
				return -1;
			}
		}
		System.out.println("\nNodes visited:" + counter);
		return current.CFR;
	}
	
	private Node getSuccessor(Node node) {
		Node successorParent = node;
		Node successor = node;
		Node current = node.rightChild;
		while(current != null) {
			successorParent = successor;
			successor = current;
			current = current.leftChild;
		}
		if(successor != node.rightChild) {
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = node.rightChild;
		}
		return successor;
	}
	
	/**
	*This method deletes a Node from the tree based on the name passed in as a parameter. First, a current and parent node are made and then set equal to the root value. 
	*The while loop then changes the values of current and parent until the name passed in until the Node is found. Then that node is deleted and depending on whether the
	*Node has children or not, different steps are taken to move the children and their children to the correct position on the tree. The most difficult to delete is a Node with two children, If 
	*a Node does have two children the program will need to find the successor. Which is a private method called getSuccessor which returns the successor Node. Once the successor is found, it replaces
	*the current Node and it takes on the children of the current node.
	*
	* @param name - the string that is being compared to the "name" data value associated with the Nodes so that the Node to be deleted can be determined
	*/
	public void delete(String name) {
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		
		while(current.name.compareToIgnoreCase(name) != 0) {
			parent = current;
			if(0 < current.name.compareToIgnoreCase(name)) {
				isLeftChild = true;
				current = current.leftChild;
			} else {
				isLeftChild = false;
				current = current.rightChild;
			}
		}
		if(current.leftChild == null && current.rightChild == null) {
			if(current == root) {
				root = null;
			} else if(isLeftChild) {
				parent.leftChild = null;
			} else {
				parent.rightChild = null;
			}
		} else if(current.rightChild == null) {
			if(current == root) {
				root = current.leftChild;
			} else if(isLeftChild) {
				parent.leftChild = current.leftChild;
			} else {
				parent.rightChild = current.leftChild;
			}
		} else if(current.leftChild == null) {
			if(current == root) {
				root = current.rightChild;
			} else if(isLeftChild) {
				parent.leftChild = current.rightChild;
			} else {
				parent.rightChild = current.rightChild;
			}
		} else {
			Node successor = getSuccessor(current);
			
			if(current == root) {
				root = successor;
			} else if(isLeftChild) {
				parent.leftChild = successor;
			} else {
				parent.rightChild = successor;
			}
			successor.leftChild = current.leftChild;
		}
		System.out.println(name + " has been deleted");	
	}
	
	/**
	* This is the public method for printInorder which traverses the binary search tree inorder and prints each Node in the order that is visited. The helper method
	* "private void printInorder(Node localRoot)" is private so the main method cannot access it. The public method calls the helper method and passes the root Node in as a 
	* parameter. Then in the helper method, as long as the localroot is not == to null, it recurses through the left child, prints the node that is visited, and then recurses through the right 
	* child.
	*/
	public void printInorder() {
		printInorder(root);
	}
	
	private void printInorder(Node localRoot) {
		if(localRoot != null) {
			printInorder(localRoot.leftChild);
			localRoot.printNode();
			printInorder(localRoot.rightChild);
		}
	}
	
	/**
	* This is the public method for printPreorder which traverses the binary search tree Preorder and prints each Node in the order that is visited. The helper method
	* "private void printPreorder(Node localRoot)" is private so the main method cannot access it. The public method calls the helper method and passes the root Node in as a 
	* parameter. Then in the helper method, as long as the localroot is not == to null, it prints the node that is visited, recurses through the left child, and then recurses through the right 
	* child.
	*/
	public void printPreorder() {
		printPreorder(root);
	}
	private void printPreorder(Node localRoot) {
		if(localRoot != null) {
			localRoot.printNode();
			printPreorder(localRoot.leftChild);
			printPreorder(localRoot.rightChild);
		}
	}
	
	/**
	* This is the public method for printPostorder which traverses the binary search tree postorder and prints each Node in the order that is visited. The helper method
	* "private void printPostorder(Node localRoot)" is private so the main method cannot access it. The public method calls the helper method and passes the root Node in as a 
	* parameter. Then in the helper method, as long as the localroot is not == to null, it recurses through the left child, recurses through the right 
	* child, and then prints the node that is visited.
	*/
	public void printPostorder() {
		printPostorder(root);
	}
	
	private void printPostorder(Node localRoot) {
		if(localRoot != null) {
			printPostorder(localRoot.leftChild);
			printPostorder(localRoot.rightChild);
			localRoot.printNode();
		}
	}
	
	/**
	* This is the public method for printBottomTwenty which traverses the binary search tree postorder. The helper method "private void printBottomTwenty(Node root1)" 
	* is private so the main method cannot access it. The public method calls the helper method and passes the root Node in as a 
	* parameter. The main purposes of these methods are to find the bottom Nodes based on CFR(the 20 with the lowest CFR value) and put them into an array of size 20 and then display those results from
	* smallest to largest. The public method starts the recursion by passing in the root Node and then once the helper method is finished array1's contents will be printed. The helper method
	* has root1 as a parameter and that will be the value that is focused on as the root Node for every recurse. First the method will put the first 20 nodes that we encounter inside the array1 of size 20.
	* Then whenever a Node is inserted into the array, the array's contents will be sorted using selection sort. After the array1 is full (indicated by the value of counting which is global) the Node being
	* visited will then be compared to the highest value in the array, and if it is less than that value it will replace that value and the array will be sorted using selection sort. This will occur until
	* the entire tree has been traversed.
	*/
	public void printBottomTwenty() {
		printBottomTwenty(root);
		for(int i = 0; i < 20; i++) {
			array1[i].printNode();
		}
	}
	
	private void printBottomTwenty(Node root1) {
		
		if(root1 != null) {
			printBottomTwenty(root1.leftChild);
			printBottomTwenty(root1.rightChild);
			
			if(array1.length > counting) {
				array1[counting] = root1;
				counting++;
				
				Node temp;
	        	//sort
	        	for (int i = 0; i < counting; i++) {
	        		int min = i;
	        		
	        		for (int j = i + 1; j < counting; j++) {
	        			if (array1[j].CFR < array1[min].CFR) {
	        				min = j;
	        			}
	        		}
	        		
	        		temp = array1[min];
	        		array1[min] = array1[i];
	        		array1[i] = temp;
	        	}
				
				
				
			} else {
				if(root1.CFR < array1[array1.length - 1].CFR) {
					array1[array1.length - 1] = root1;
					
					//sort here
					Node temp;
					for (int i = 0; i < counting; i++) {
		        		int min = i;
		        		
		        		for (int j = i + 1; j < counting; j++) {
		        			if (array1[j].CFR < array1[min].CFR) {
		        				min = j;
		        			}
		        		}
		        		
		        		temp = array1[min];
		        		array1[min] = array1[i];
		        		array1[i] = temp;
		        	}
				}
			}
		}
	}  
	
	/**
	* This is the public method for printTopTwenty which traverses the binary search tree postorder. The helper method "private void printTopTwenty(Node root2)" 
	* is private so the main method cannot access it. The public method calls the helper method and passes the root Node in as a 
	* parameter. The main purposes of these methods are to find the top Nodes based on CFR(the 20 with the highest CFR value) and put them into an array of size 20 and then display those results from
	* largest to smallest. The public methodsets counting = to zero and starts the recursion by passing in the root Node and then once the helper method is finished, array2's contents will be printed. The helper method
	* has root2 as a parameter and that will be the value that is focused on as the root Node for every recurse. First the method will put the first 20 nodes that we encounter inside the array2 of size 20.
	* Then whenever a Node is inserted into the array, the array's contents will be sorted using selection sort. After the array2 is full (indicated by the value of counting which is global) the Node being
	* visited will then be compared to the lowest value in the array, and if it is greater than that value it will replace that value and the array will be sorted using selection sort. This will occur until
	* the entire tree has been traversed.
	*/
	public void printTopTwenty() {
		counting = 0;
		printTopTwenty(root);
		for(int i = 19; i > -1; i--) {
			array2[i].printNode();
		}
	}
	private void printTopTwenty(Node root2) {

		if(root2 != null) {
			printTopTwenty(root2.leftChild);
			printTopTwenty(root2.rightChild);
			
			if(array2.length > counting) {
				array2[counting] = root2;
				counting++;
				
				//sort array here
				Node temp;
				for (int i = 0; i < counting; i++) {
	        		int min = i;
	        		
	        		for (int j = i + 1; j < counting; j++) {
	        			if (array2[j].CFR < array2[min].CFR) {
	        				min = j;
	        			}
	        		}
	        		
	        		temp = array2[min];
	        		array2[min] = array2[i];
	        		array2[i] = temp;
	        	}
				
			} else {
				if(root2.CFR > array2[0].CFR) {
					array2[0] = root2;
					
					//sort array here
					Node temp;
					for (int i = 0; i < counting; i++) {
		        		int min = i;
		        		
		        		for (int j = i + 1; j < counting; j++) {
		        			if (array2[j].CFR < array2[min].CFR) {
		        				min = j;
		        			}
		        		}
		        		
		        		temp = array2[min];
		        		array2[min] = array2[i];
		        		array2[i] = temp;
		        	}	
				}
			}
		}
	}
}