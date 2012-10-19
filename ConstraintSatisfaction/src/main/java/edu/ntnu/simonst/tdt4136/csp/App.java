package edu.ntnu.simonst.tdt4136.csp;

import edu.ntnu.simonst.tdt4136.csp.kqueens.QueensAssignment;

/**
 * This application runs the K-Queen puzzle with QUEEN_COUNT of queens.
 * 
 * @author Simon Stastny
 */
public class App {

  
  /**
   * Parameter for specifying number of queens to be placed (and dimension of the board)
   */
  public static final int QUEEN_COUNT = 80;

  public static void main(String[] args) {
    BacktrackSearch<Integer> csp = new BacktrackSearch<Integer>();

    Assignment<Integer> solution = csp.backtrack(new QueensAssignment(QUEEN_COUNT));

    if (solution != null) {
      solution.printBoard();
    } else {
      System.out.println("Solution does not exist.");
    }
  }
}
