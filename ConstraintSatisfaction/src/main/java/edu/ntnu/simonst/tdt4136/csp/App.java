package edu.ntnu.simonst.tdt4136.csp;

import edu.ntnu.simonst.tdt4136.csp.kqueens.QueensAssignment;

/**
 * 13.10.2012
 * @author Simon Stastny
 */
public class App {

  public static void main(String[] args) {
    BacktrackSearch<Integer> csp = new BacktrackSearch<Integer>();

    Assignment<Integer> solution = csp.backtrack(new QueensAssignment(8));

    if (solution != null) {
      solution.printCurrent();
    } else {
      System.out.println("Solutions does not exist.");
    }
  }
}
