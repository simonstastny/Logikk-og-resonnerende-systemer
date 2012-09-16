package edu.ntnu.simonst.tdt4136.astar;

import edu.ntnu.simonst.tdt4136.astar.checkerspuzzle.CheckersPuzzle;
import edu.ntnu.simonst.tdt4136.astar.fractionpuzzle.FractionPuzzle;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    FractionPuzzle fractions = new FractionPuzzle();
    fractions.search();

    CheckersPuzzle checkers = new CheckersPuzzle();
    checkers.search();
  }
}
