package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Assignment;
import edu.ntnu.simonst.tdt4136.csp.Variable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Simon Stastny
 */
public class QueensAssignment extends Assignment {

  protected int size;

  protected Queen[] queens;

  public QueensAssignment(int size) {
    this.size = size;
    this.queens = new Queen[size];

    for (int i = 0; i < size; i++) {
      queens[i] = new Queen(i, size);
    }
  }

  public void printCurrent() {
    for (int row = 0; row < size; row++) {
      Queen queen = queens[row];
      for (int column = 0; column < size; column++) {
        System.out.print(queen.getValue() == column ? "X " : "_ ");
      }
    }
  }

  @Override
  public boolean isComplete() {
    for (int i = 0; i < size; i++) {
      Queen queen = queens[i];
      if (queen.getValue() == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Variable selectUnassignedVariable() {
    for (int i = 0; i < size; i++) {
      Queen queen = queens[i];
      if (queen.isUnassigned()) {
        return queen;
      }
    }
    return null;
  }

  @Override
  public void recomputeInferences() {

    // go through all rows and compute inferrences
    for (int thisQueenRow = 0; thisQueenRow < size; thisQueenRow++) {
      Queen queen = queens[thisQueenRow];

      // queen has value
      if (!queen.isUnassigned()) {
        // if queen is in the place, other places in row are in conflict
        queen.inferInRow();

        // also all rows down this one now have some inferrences from current row
        for (int inferredRow = thisQueenRow + 1; thisQueenRow < size; inferredRow++) {
          Queen qnext = queens[inferredRow];

          Integer columnUnder = queen.getValue(); // place in the same column
          Integer diagonalLeft = queen.getValue() - (inferredRow - thisQueenRow); // place on left diagonal
          Integer diagonalRight = queen.getValue() + (inferredRow - thisQueenRow); // place on right diagonal

          Integer[] inferredColumns = {columnUnder, diagonalLeft, diagonalRight};
          
          Set<Integer> setOfNewConflicts = new HashSet<Integer>();

          for (Integer column : inferredColumns) {
            if (qnext.isValueInDomain(column)) { //CHECK /*inferredValue >= 0 && inferredValue < size && */
              // remove inferred values from domain
              qnext.removeValueFromDomain(column); //CHECK
              setOfNewConflicts.add(column);
            }
          }
          
          // move inferred values to conflict stack
          qnext.putToConflicts(setOfNewConflicts);
        }
      }
    }
  }
}