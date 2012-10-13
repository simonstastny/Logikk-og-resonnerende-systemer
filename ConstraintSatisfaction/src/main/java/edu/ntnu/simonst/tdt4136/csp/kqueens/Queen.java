package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Variable;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 13.10.2012
 * @author Simon Stastny
 */
public final class Queen extends Variable<Integer> {

  int size;

  int row;

  public Queen(int row, int size) {
    this.row = row;
    this.size = size;

    this.domain = generateDomain();
    this.conflicts = new Stack<Set<Integer>>();
  }

  public int getRow() {
    return row;
  }

  /**
   * adding all remaining values (apart from assigned value) from domain to conflicts
   */
  protected void inferInRow() {
    if (!this.getOrderedDomainValues().isEmpty()) {     
      // vytvarim opis, protoze nechci predavat odkaz na puvodni mnozinu
      addConflicts(new HashSet<Integer>(getOrderedDomainValues()));
    }
  }

  @Override
  public Set<Integer> generateDomain() {
    Set<Integer> values = new HashSet<Integer>();

    // oborem hodnot jsou zde cisla od 0 do (SIZE-1) znazornujici sloupce
    // domain is a set of numbers from 0 to (SIZE-1) representing columns
    for (int i = 0; i < size; i++) {
      values.add(i);
    }
    
    return values;
  }
}
