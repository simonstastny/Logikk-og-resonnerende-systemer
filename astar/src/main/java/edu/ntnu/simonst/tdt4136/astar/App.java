package edu.ntnu.simonst.tdt4136.astar;

import edu.ntnu.simonst.tdt4136.astar.checkerspuzzle.CheckersPuzzle;
import edu.ntnu.simonst.tdt4136.astar.fractionpuzzle.FractionPuzzle;

/**
 * This class runs both puzzles.
 * Please run the main method and see console output, where the solutions/paths are displayed.
 *
 */
public class App {

  public static void main(String[] args) {
    // fraction puzzle search
    FractionPuzzle fractions = new FractionPuzzle();
    fractions.search();

    // checkers puzzle search
    CheckersPuzzle checkers = new CheckersPuzzle();
    checkers.search();
  }
}
