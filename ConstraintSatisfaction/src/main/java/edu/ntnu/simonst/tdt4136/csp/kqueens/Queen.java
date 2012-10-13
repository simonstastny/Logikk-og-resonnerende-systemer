package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Variable;
import java.util.HashSet;

/**
 *
 * @author Simon Stastny
 */
public class Queen extends Variable<Integer> {

  int size;

  int row;

  public Queen(int row, int size) {
    this.row = row;
    this.size = size;
  }

  public int getRow() {
    return row;
  }

  protected void inferInRow() {
    if (!this.getOrderedDomainValues().isEmpty()) {
      addConflicts(domain);
      domain = new HashSet<Integer>();
    }
  }
}
