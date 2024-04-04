package accidentpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	public static void main(String args[]) throws FileNotFoundException {
		File f = new File(args[0]);
		Scanner scnr1 = new Scanner(f);
		//int reportCount = Report.findReportCount(scnr1);
		int reportCount = 25000;
		scnr1.close();
		Scanner scnr2 = new Scanner(f);
		scnr2.nextLine();
		TreeMap<String, MyAVL> stateAVLs = generateAVLs(scnr2, reportCount);
		generateMenu(stateAVLs);
	}
	
	//much more than 20 lines. I should get around to fixing this
	public static void generateMenu(TreeMap<String, MyAVL> stateAVLs) {
		Scanner inputScanner = new Scanner(System.in);
		boolean validInput = false;
		String state;
		String dateAsString;
		MyDate date;
		do {
		System.out.println("Enter the state (e.g., IL): ");
		state = inputScanner.next(); 
		if ((state.length() != 2) || !Character.isLetter(state.charAt(0)) || !Character.isLetter(state.charAt(1))) {
			System.out.println("Invalid state code. Re-enter");
		} else {
			validInput = true;
		}
		} while (validInput == false);
		
		if (stateAVLs.containsKey(state)) {
			validInput = false;
			System.out.println("Enter a date in the YYYY/MM/DD format, to find the number of accidents occuring on or after that date");
			do {
				dateAsString = inputScanner.next();
				if ((dateAsString.length() != 10) || !Character.isDigit(dateAsString.charAt(0)) || !Character.isDigit(dateAsString.charAt(1)) || !Character.isDigit(dateAsString.charAt(2))
						|| !Character.isDigit(dateAsString.charAt(3)) || !Character.isDigit(dateAsString.charAt(5)) || !Character.isDigit(dateAsString.charAt(6))||
						!Character.isDigit(dateAsString.charAt(8)) || !Character.isDigit(dateAsString.charAt(9))){
					System.out.println("Invalid date. Re-enter");
				} else {
					validInput = true;
				}
			} while (!validInput);
		int year = Integer.parseInt(dateAsString.substring(0, 4));
		int month = Integer.parseInt(dateAsString.substring(5, 7));
		int day = Integer.parseInt(dateAsString.substring(8, 10));
		date = new MyDate(year, month, day);
		int accidentsOnOrAfter = stateAVLs.get(state).getAccidentsAfterDate(date);
		System.out.println(accidentsOnOrAfter + " accidents occured on or after " + dateAsString + "in the state of " + state);
		} else {
			System.out.println("No matching state for: " + state);
		}
		
		inputScanner.close();
	}
	
	public static TreeMap<String, MyAVL> generateAVLs(Scanner scnr, int reportCount) {
		long startTime = System.nanoTime();
		TreeMap<String, MyAVL> MasterTree = new TreeMap<String, MyAVL>();
		for (int i = 0; i < reportCount; i++) {
			Report r = Report.createReport(scnr);
			if (!(MasterTree.containsKey(r.getState()))){
				MyAVLNode newRoot = new MyAVLNode(new ArrayList<Report>());//creates a node with an empty arraylist
				newRoot.add(r);//adds the report to this arraylist
				MasterTree.put(r.getState(), new MyAVL(newRoot));//puts the node in the tree
			} else {
				MasterTree.get(r.getState()).add(r);
			}
		}
		
		long endTime = System.nanoTime();
		double runTime = (endTime - startTime)/1000000;
		System.out.println(runTime + " milliseconds to build the AVL trees");
		
		return MasterTree;
	}
}
