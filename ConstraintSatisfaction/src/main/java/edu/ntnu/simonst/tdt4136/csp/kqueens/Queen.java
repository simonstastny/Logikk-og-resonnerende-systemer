package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Variable;
import java.util.HashSet;
import java.util.Set;

/**
 * 13.10.2012
 * @author Simon Stastny
 */
public final class Queen extends Variable<Integer> {

  private int gameSize;
  private int row;

  public Queen(int row, int size) {
    this.row = row;
    this.gameSize = size;
    super.clear();
  }

  public int getRow() {
    return row;
  }

  @Override
  public Set<Integer> generateDomain() {
    Set<Integer> values = new HashSet<Integer>();

    // oborem hodnot jsou zde cisla od 0 do (SIZE-1) znazornujici sloupce
    // domain is a set of numbers from 0 to (SIZE-1) representing columns
    for (int i = 0; i < gameSize; i++) {
      values.add(i);
    }
    
    return values;
  }
}
