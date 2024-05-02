///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           descriptive title of the program making use of this file
// Course:          course number, term, and year
//
// Author:          
// Email:           
// Lecturer's Name: 
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// Source or Recipient; Description
// Examples:
// Jane Doe; helped me with for loop in reverse method
// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html; 
//         counting for loop
// John Doe; I helped with switch statement in main method.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.util.Scanner;
import java.util.InputMismatchException;

public class IntException {
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      System.out.print(isDivisibleByThree(scnr));
   }
   
  /**
   * Determines if the input to scanner is evenly divisible by three, and
   * returns the result as a boolean. If the input to scanner is inappropriate,
   * catch the exception.
   * 
   * @param  scnr  a scanner object 
   * @return       true if input is evenly divisible by three, false if not
   */ 
   public static boolean isDivisibleByThree(Scanner scnr) {

      //TODO: try-catch block which catches incorrect input
   
   }

}