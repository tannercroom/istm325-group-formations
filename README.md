# ISTM 325 - Group Formations

<div id=introduction>
The first part of my ISTM 325 Final Exam required me to create a program that can properly create groups and group sets of "students" (unique names), with the following requirements:
<br><br>
<ul>
  <li>No student is repeated in the same group.</li>
  <li>No student shares a group with another student more than once, including across group sets.</li>
</ul>

This logic problem is reminiscent of <a href="https://en.wikipedia.org/wiki/Kirkman's_schoolgirl_problem">Kirkman's schoolgirl problem</a>, the <a href="https://en.wikipedia.org/wiki/Social_golfer_problem">social golfer problem</a>, and other similar combinatorial-design problems.

The detailed instructions for how the program should function are as follows:

<ol>
  <li>Read in a specified number of unique students via a pre-formatted CSV file.</li>
  <li>Prompt the user for the number of students in each group.</li>
  <li>Prompt the user for the number of group sets that should be created.</li>
  <li>Notify the user if the number of groups or sets is unable to be created.</li>
  <li>Output a formatted CSV file with the groups and sets.</li>
</ol> 
</div>

<hr>

<div id=instructions>
<h3>How to Run This Program</h3>
  
This Java program can be compiled and run in any Java Runtime Environment (JRE) the user chooses. After downloading the program, extract all files in "GroupFormations.zip" to a single location, and run <i>groupFormations.java</i> in your preferred environment. This program has no GUI, and is fully executed through the console.

<b>I. Define the student population in which to create groups.</b>

Enter the full filename of the pre-formatted CSV file of unique students you would like the program to use. The list should contain individual, unique names to adaqautely identify each student. The number of students the program reads in is defined by the integer at the beginning of the CSV file. When the program is compiled and run in the JRE of your choice, the program requests the user to enter the filename of the CSV file they would like to use.

Note: The file "<i>names.csv</i>" is included as an example and can be used when running this program.

<b>II. Define the number of students to be placed in each group.</b>

Once the students have been read in to memory, the program asks the user to specify the number students to place in each group. The user must enter a valid integer to continue.

<b>III. Define the number of group sets to be created.</b>

The program then asks the user to specify the number of group sets to be created. The user must enter a valid integer to continue. If the program cannot create the number of groups or sets required after 1,500,000 failed attempts, a message will be displayed detailing "The specified number of sets was unable to be created due to the constraint of no two students co-occurring in the same group more then once."
  
<b>IV. View outputted groups and sets in designated formatted CSV file.</b>

Once the program has completed creating groups and sets, an output file will be automatically generated with the filename "sets-<i>[yyyy-MM-dd_HHmmss]</i>.csv" in the same location all program files reside.

<i>Note: if the program was unable to create the specified number of sets, an output file will still be generated with what the program was able to come up with before reaching 1,500,000 failed attempts. </i>
</div>
