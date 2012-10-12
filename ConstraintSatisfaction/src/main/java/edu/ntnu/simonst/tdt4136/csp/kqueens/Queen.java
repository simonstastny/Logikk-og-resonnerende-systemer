package edu.ntnu.simonst.tdt4136.csp.kqueens;

import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Simon Stastny
 */
public class Queen {

  int column;
  int row;

  Set<Integer> domain;
  Stack<Set<Integer>> conflicts;

}
