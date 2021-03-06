import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

class groupFormations {
	
	public static void main(String args[]) {
		Scanner scnr = new Scanner(System.in);
		ArrayList<String> studentList = new ArrayList<String>(); //The ArrayList we are adding the students to
		
		/* Read In a List of Students */
		try {
			//Import a file to use with the program
			System.out.println("\nPlease enter the name of the file you wish to import:");
			String filename = scnr.nextLine().trim();
			Scanner filescanner = new Scanner(new File(filename)); //Used to read the file
		
			//Set limit for number of students to load into program
			String firstLine = filescanner.nextLine().trim(); //We assume the first line will always be different (containing the # of students to read in)
			int studentLimit = Integer.parseInt(firstLine); //Parses the string into an int to use in the following for loop
		
			//Load (limit #) of students into program
			int countAdded = 0; //Used for confirming program runs successfully
			for (int i = 0; i < studentLimit; i++) { //Until we reach the number of students to read in...
				String line = filescanner.nextLine().trim(); //Read in the next line
				studentList.add(line); //Add the student to the list
				countAdded++;
			}
			System.out.println("\nDone! " + countAdded + " students have been added."); //Confirms the number of students that have been successfully read in
		}
		catch (Exception e) { //If an exception occurs in the try block
			System.out.println("Could not open student list file: " + e);
		}
		
		
		/* Prompt the User for Students per Group */
		boolean done = false;
		String input = ""; //Initialized to fix error. Replaced with user input
		
		while (!done) { //Runs until valid input is entered
			System.out.println("\nHow many students will be in each group? Must be 2, 3, or 4.");
			input = scnr.nextLine().trim();
			
			if (input.equals("2") || input.equals("3") || input.equals("4")) { //If input is 2, 3, or 4...
				done = true; //break loop/continue through program
			}
			else { //If input is not 2, 3, or 4...
				System.out.println("\nInvalid input. Please try again."); //Try again
			}
		}
		int studentsPerGroup = Integer.parseInt(input); //Once a valid input is entered, it is parsed into an int for future use
		int groupsPerSet = (studentList.size() / studentsPerGroup); //Calculates the number of groups per each set
		
		
		/* Prompt the User for Number of Group Sets */
		System.out.println("\nHow many group sets would you like?");
		
		int numberOfGroupSets = scnr.nextInt();
		scnr.nextLine(); //Consumes return/enter
		
		int totalNumberOfGroups = (numberOfGroupSets * groupsPerSet); //Gets the total number of groups for later
		
		
		/* Begin Making Sets */
		
		ArrayList<ArrayList<String[]>> allSets = new ArrayList<ArrayList<String[]>>(); //Permanent ArrayList of sets. This is where everything will eventually live.
		
		int numOfStudents = studentList.size(); //Used when creating a random number generator to prevent weirdness.
		int setsCreated = 0; //Used when writing out to file. In case setsCreated != specified number of Group Sets, program will only try to output number of sets actually created
		long failCounter = 0; //Used to stop looping if impossible to complete
		
		for (int i = 0; i < numberOfGroupSets; i++) { //Creates a new set
			ArrayList<String[]> newGroupSet = new ArrayList<String[]>();
			
			//By default, we do not expect this to be the first set
			boolean firstSet = false;
			
			for (int j = 0; j < groupsPerSet; j++) { //Creates a new group
			
				//Check if failCounter has hit limit
				if (failCounter == 1500000) {
					break;
				}
			
				//By default, we do not expect this to be the first group
				boolean firstGroup = false;
				
				//Create a new group
				String[] newGroup = new String[studentsPerGroup];
				
				//Create a new random number generator for student index
				Random r = new Random();
				
				//Add Students to newGroup
				for (int k = 0; k < studentsPerGroup; k++) {
					newGroup[k] = studentList.get(r.nextInt(numOfStudents)); //Adds student at random index to newGroup
				}
				
				/* Validate New Created Group */
				
				boolean pass = true; //Default, group will be flagged as fail if it fails any validation
				
				//Is this the first set?
				if (i == 0) {
					firstSet = true;
				}
				
				//Is this the first group?
				if (j == 0) {
					firstGroup = true;
				}
				
				//Check for repeated students in same group
				for (int m = 0; m < studentsPerGroup; m++) { //Index 1
					for (int n = 0; n < studentsPerGroup; n++) { //Index 2
						if (newGroup[m].equals(newGroup[n]) && (m != n)) { //If index 1 and 2 equal the same value (and are not pointing at the same student), fail
							pass = false; //Group fails
						}
					}
				}
				
				//Check if this individual is present more than once in the same set
				if (firstGroup == false && pass == true) { //If there is more than one group in the set
					for (int a = 0; a < j; a++) { //Iterate through each group in current (building) set
						for (int b = 0; b < studentsPerGroup; b++) { //Iterate through each student in group
								for (int c = 0; c < studentsPerGroup; c++) { //Used to iterate through newGroup
								String[] testGroup = newGroupSet.get(a);
								if (testGroup[b].equals(newGroup[c])) { //If the individual appears in another group, fail
									pass = false;
								} 
							}
						}
					}
				}
				
				
				//Check if the individuals share groups in different sets
				if (firstSet == false && pass == true) {
					
					ArrayList<String> studentsWorkedWith; //This will act as a "whitelist" to compare what students the base has worked with
					
					for (int a = 0; a < studentsPerGroup; a++) {
						studentsWorkedWith = new ArrayList<String>(); //Create new whitelist
						studentsWorkedWith.add(newGroup[a]); //Base student to see who they've worked with
						
						//Check across sets to find student
						for (int b = 0; b < allSets.size(); b++) {
							ArrayList<String[]> testSet = allSets.get(b);
							
							//Check across groups in set to find student
							for (int c = 0; c < groupsPerSet; c++) {
								String[] testGroup = testSet.get(c);
								
								//Check for student in group
								for (int d = 0; d < studentsPerGroup; d++) {
									
									//If student is in group
									if (testGroup[d].equals(studentsWorkedWith.get(0))) {
										
										//If student is not base, add to whitelist
										for (int e = 0; e < studentsPerGroup; e++) {
											if (!testGroup[e].equals(studentsWorkedWith.get(0))) {
												studentsWorkedWith.add(testGroup[e]);
											}
										}
									}
								}
							}
						}
						
						//Compare newGroup to students in whitelist
						for (int b = 0; b < studentsPerGroup; b++) { //For loop to iterate through newGroup
							for (int c = 0; c < studentsWorkedWith.size(); c++) { //For loop to iterate through the "whitelist"
								if (!newGroup[b].equals(studentsWorkedWith.get(0))) { //If the student identified in newGroup is NOT the base, continue
									if (newGroup[b].equals(studentsWorkedWith.get(c))) { //If the student is someone on the whitelist, fail
										pass = false;
									}
								}
							}
							
						}
						
					}
				}
				
				//Determine whether to add group to set
				if (pass == true) {
					newGroupSet.add(newGroup);
				}
				else {
					j--; //Decrement group counter, requiring the loop to run an additional time
					failCounter++; //Increment failCounter
				}
				
			}
			
			//Check if failCounter limit has been reached
			if (failCounter == 1500000) {
				break; //exits the for loop for new sets
			}
			
			//Once a set is completed, add it to the permanent ArrayList of sets (allSets)
			allSets.add(newGroupSet);
			setsCreated++; //Increment setsCreated, used for when sets actually created != specified sets by user
		}
		
		//Display message if failCounter was reached
		if (failCounter == 1500000) {
			System.out.println("\nNote: The specified number of sets was unable to be created due to the constraint of no two students co-occurring in the same group more than once.");
		}
		
		/* Output Sets to CSV file */
		
		//Create new date for outfile name
		Date date = new Date();  
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd_HHmmss");  
		String outDate = simpleDate.format(date);  
		String outfile = "sets-" + outDate + ".csv";
		
		//Read out sets
		try {
			//Create outfile and header row
			PrintWriter out = new PrintWriter(new File(outfile));
			
			String output = "";
			
			//Set header row based on number of students per group
			if (studentsPerGroup == 2) {
				output += "Group,Member1,Member2\n";
			}
			else if (studentsPerGroup == 3) {
				output += "Group,Member1,Member2,Member3\n";
			}
			else { //should only run if studentsPerGroup == 4. Program will not continue if user doesn't input 2, 3, or 4 to start.
				output += "Group,Member1,Member2,Member3,Member4\n";
			}
			
			//Create rows
			for (int i = 1; i <= setsCreated; i++) { //used to identify the current set
				for (int j = 1; j <= groupsPerSet; j++) { //used to identify the current group
					
					output += i + "-" + j; //first column, set-group
					
					for (int k = 0; k < studentsPerGroup; k++) { //iterate through students in group
						output += "," + allSets.get(i-1).get(j-1)[k]; //outputs a student, preceded by a comma. i-1 and j-1 is to prevent from index out of bounds
					}
					
					output += "\n"; //at the end of a row (group), a new line is added
				}
			}
			
			//Print outfile
			out.println(output);
			
			out.close();
			System.out.println("\nThe file " + outfile + " has been written."); //Confirmation of file output
		}
		catch (Exception e) { //In case an exception occurs in the try block
			System.out.println("Something went wrong. " + e);
		}
	}
}