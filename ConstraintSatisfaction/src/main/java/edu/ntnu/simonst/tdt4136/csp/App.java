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
  public static final int[] QUEEN_COUNT = { 8, 16}; // { 4, 8, 10, 16, 30, 40, 50, 60, 70, 80, 90, 100, 105, 113};
  
  public static final boolean SMALLEST_DOMAIN = true;

  public static void main(String[] args) {
    BacktrackSearch<Integer> csp = new BacktrackSearch<Integer>();

    Assignment<Integer> solution;
    for (int count : QUEEN_COUNT) {
      Long time = System.currentTimeMillis();
      solution = csp.backtrack(new QueensAssignment(count), SMALLEST_DOMAIN);

      if (solution != null) {
        solution.print();
        System.out.println("size: " + count + " time: " + (System.currentTimeMillis()-time));
      } else {
        System.out.println("Solution does not exist.");
      }
    }
  }
}
