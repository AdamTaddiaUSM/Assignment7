package accidentpack;

import java.util.ArrayList;

public class MyAVLNode {
	
	public int balance;
	public MyAVLNode parent;

	public ArrayList<Report> Data;
	
	public MyAVLNode left;
	public MyAVLNode right;
	
	public MyAVLNode(ArrayList<Report> reports) {
		this.Data = reports;
	}
	
	public int getAccidentsAfterDateRecursive(MyDate md) {
		int accidents = 0;
		if (this.Data.get(0).getDate().compareTo(md) == 0) {//if this node happened ON md
			accidents += this.Data.size();
			if (this.right != null) {
			accidents += this.right.accidentsInSubtree();}
		}else if (this.Data.get(0).getDate().compareTo(md) > 0) {//if this node happened AFTER md
			accidents += this.Data.size();
			if (this.right != null) {
			accidents += this.right.accidentsInSubtree();}
			if (this.left != null) {
			accidents += this.left.getAccidentsAfterDateRecursive(md);}
		} else {//this node happened BEFORE MD
			if (this.right != null) {
			accidents = this.right.getAccidentsAfterDateRecursive(md);}
		}
		return accidents;
	}
	
	
	
	public int accidentsInSubtree() {
		int accidents = this.Data.size();
		if (this.left!= null) {
			accidents += this.left.accidentsInSubtree();
		}
		if (this.right!= null) {
			accidents += this.right.accidentsInSubtree();
		}
		return accidents;
	}
	
	//I messed up, this method is probably useless
	public MyAVLNode getNodeForDateRecursive(MyDate date) {
		if (this.Data.get(0).getDate().compareTo(date) == 0) {//this node is on the desired date
			return this;
		}else if (this.Data.get(0).getDate().compareTo(date) >= 1) {//this date is after the desired date
			return this.left.getNodeForDateRecursive(date);
		} else {
			return this.right.getNodeForDateRecursive(date);
		}
	}
	
	public void add(Report r) {
		if (this.Data.isEmpty()) {
			Data.add(r);
		}else	if (this.Data.get(0).compareTo(r) < 0) {//this node came BEFORE r
			if (this.right == null) {
				this.right = new MyAVLNode(new ArrayList<Report>());
				this.right.Data.add(r);
			} else {
				this.right.add(r);
			}
		} else if (this.Data.get(0).compareTo(r) > 0){// this node came AFTER r
			if (this.left == null) {
				this.left = new MyAVLNode(new ArrayList<Report>());
				this.left.Data.add(r);
			} else {
				this.left.add(r);
			}
		} else {//r occured on the same date as the node
			this.Data.add(r);
		}
		
	}
	
	public void correctImbalanceRecursion() {
		while (this.balance > 1) {
			MyAVL.leftRotation(this);
			this.getBalance();
		}
		while (this.balance < -1) {
			MyAVL.rightRotation(this);
			this.getBalance();
		}
		
		this.right.correctImbalanceRecursion();
		this.left.correctImbalanceRecursion();
	}
	
	//this method should ONLY be called by the MyAVL checkBalanceMethod
	public int checkImbalanceRecursion() {
		int totalImbalance = 0;
		this.getBalance();
		if (this.balance != (1) && this.balance != (-1) && this.balance != (0)) {
			totalImbalance += 1;
		}
		
		if (this.right != null) {
		totalImbalance += (this.right.checkImbalanceRecursion());}
		
		if (this.left != null) {
		totalImbalance += this.left.checkImbalanceRecursion();}
		
		return totalImbalance;
	}

	public int getHeight() {//gets the height of a node's subtree

		if (this.right == null && this.left == null) {
			return 1;
		} else if (this.right == null && this.left != null) {
			return 1 + this.left.getHeight();
		} else if (this.right != null && this.left == null) {
			return 1 + this.right.getHeight();
		} else {
			if (this.left.getHeight() > this.right.getHeight()) {
				return this.left.getHeight() + 1;
			} else {
				return this.right.getHeight() + 1;
			}
		}
	}

	
	public int getBalance() {
		int rightLength = 0;
		int leftLength = 0;
		
		if (this.right != null) {
		rightLength = this.right.getHeight();
		}
		
		if (this.left != null) {
		leftLength = this.left.getHeight();
		}
		
		this.balance =  rightLength - leftLength;
		return this.balance;
	}
	
}
