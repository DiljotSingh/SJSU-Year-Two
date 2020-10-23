/**
 * Implements a Binary Search Tree used to list a Person's list of friends in sorted order (inorder walk)
 * 
 * @author Diljot Singh
 *
 */
public class BinarySearchTree {
	private Node root; // The root of the tree
	public static String inorderTraversal; // The inorder traversal of this tree

	/**
	 * Constructs an empty BST, root is initialized to null
	 */
	public BinarySearchTree() {
		root = null;
		inorderTraversal = "";
	}

	/**
	 * Inserts a new node into the tree
	 * 
	 * @param obj the object to insert
	 */
	public void add(Comparable obj) {

		Node newNode = new Node(); // Create a new node

		// Set the data, left, and right pointers
		newNode.data = obj;
		newNode.left = null;
		newNode.right = null;

		// If the root is null (meaning tree is empty), then we set this new node as the
		// root
		if (root == null) {
			root = newNode;
		}

		// Otherwise we must insert the node into the appropriate positioning by
		// comparing its data
		else {
			root.treeInsert(newNode);
		}
	}

	/**
	 * Prints the contents of the tree in sorted order Calls the inorderTreeWalk
	 * method on the root (initial call)
	 */
	public void print() {
		inOrderTreeWalk(root);
		System.out.println();
	}

	/**
	 * Returns the contents of the tree in sorted order (for the GUI) Calls the
	 * inorderTreeWalk method on the root (initial call)
	 * 
	 * @return inorderTraversal the String that contains the inorder traversal
	 */
	public String printGUI() {
		inOrderTreeWalkGUI(root);
		return inorderTraversal; // Returns a String with the inorder traversal of this tree
	}

	/**
	 * Private static helper method to help print the tree in sorted order
	 * (recursive)
	 * 
	 * @param parameter is the root of the current subtree (initial call on root)
	 */
	private static void inOrderTreeWalk(Node parent) {
		if (parent == null) {
			return;
		}

		// Inorder walk is left subtrees, root, right subtrees
		inOrderTreeWalk(parent.left); // Print the left subtrees
		System.out.print(parent.data + " "); // Print the root
		inOrderTreeWalk(parent.right); // Print the right subtrees
	}

	/**
	 * Private static helper method to help print the tree in sorted order
	 * (recursive), for the GUI
	 * 
	 * @param parameter is the root of the current subtree (initial call on root)
	 */
	private static void inOrderTreeWalkGUI(Node parent) {
		if (parent == null) {
			return;
		}

		// Inorder walk is left subtrees, root, right subtrees
		inOrderTreeWalkGUI(parent.left); // Traverse left subtrees
		inorderTraversal = inorderTraversal + parent.data + " "; // Update the inorder traversal
		inOrderTreeWalkGUI(parent.right); // Traverse right subtrees
	}

	/**
	 * Inner class Node that has Comparable data, and pointers to left/right
	 */
	class Node {
		public Comparable data;
		public Node left;
		public Node right;

		/**
		 * Inserts a new node into the tree in the corresponding position
		 * 
		 * @param newNode the new node to insert
		 */
		public void treeInsert(Node newNode) {
			// Compares the current node's data to the new node's data
			int comp = newNode.data.compareTo(data);

			// If compareTo was negative (newNode is smaller), then we go to the left
			// subtree
			if (comp < 0) {
				// If the left pointer of the current node is empty, we insert the new node
				// there
				if (left == null) {
					left = newNode;
				}
				// Otherwise we keep searching for the appropriate position in the left subtree
				// of the current node
				else {
					left.treeInsert(newNode);
				}
			}
			// If compareTo was positive (newNode is larger), then we go to the right
			// subtree
			else if (comp > 0) {

				// If the right pointer of the current node is empty, we insert the new node
				// there
				if (right == null) {
					right = newNode;
				}
				// Otherwise we keep searching for the appropriate position in the left subtree
				// of the current node
				else {
					right.treeInsert(newNode);
				}
			}
		}
	}
}