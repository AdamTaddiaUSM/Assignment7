package accidentpack;

import java.util.Scanner;

public class MyAVL {
	
	public int imbalanceCount = 0;
	
	public String state;
	public MyAVLNode root;
	
	public int getAccidentsAfterDate(MyDate md) {
		long startTime = System.nanoTime();
		int numAccidents = this.root.getAccidentsAfterDateRecursive(md);
		long endTime = System.nanoTime();
		double length = (endTime - startTime);
		System.out.println(length + " nanoseconds to find " + numAccidents + " reports on or after the specified date");
		
		return numAccidents;
	}
	
	public void add(Report r) {
		this.root.add(r);
		this.root.checkImbalanceRecursion();
	}
	
	public MyAVL(MyAVLNode root) {
		this.root = root;
	}
	
	public void correctBalance() {
		this.root.correctImbalanceRecursion();
	}
	
	public void checkBalance() {
		this.imbalanceCount = this.root.checkImbalanceRecursion();
	}
	
	
	//called on a node with a left unbalance of 2 or more
	public static void rightRotation(MyAVLNode node) {
		node.left.parent = node.parent;//the node's left child has its parent set to the node's parent
		if (node.parent.right == node) {
			node.parent.right = node.left;//if node was a right child, node's left child becomes the right child of node's parent
		} else {
			node.parent.left = node.left;//if node was a left child, node's left child becomes the left child of node's parent
		}
		node.parent = node.left;//the node has its parent set to what was its left node
		node.left.right = node;//what was the left child of node now has node as a right child
		node.left = null;//the node's left field is now null
		
		
	}
	
	//called on a node with a right unbalance of 2 or more
		public static void leftRotation(MyAVLNode node) {
			node.right.parent = node.parent;//the node's right child has its parent set to the node's parent
			if (node.parent.right == node) {
				node.parent.right = node.right;//if node was a right child, node's right child becomes the right child of node's parent
			} else {
				node.parent.left = node.right;//if node was a left child, node's right child becomes the left child of node's parent
			}
			node.parent = node.right;//the node has its parent set to what was its right node
			node.right.left = node;//what was the right child of node now has node as a left child
			node.right = null;//the node's right field is now null
			
			
		}
}
