package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Variable;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
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

  protected void inferInRow() {
    if (!this.getOrderedDomainValues().isEmpty()) {
      Set<Integer> newConflicts = new HashSet<Integer>();

      newConflicts.addAll(getOrderedDomainValues());
      newConflicts.remove(getValue());
      
      addConflicts(newConflicts);
      
      //FIXME
//      domain = new HashSet<Integer>();
//      domain.add(getValue());
    }
  }

  @Override
  public Set<Integer> generateDomain() {
    Set<Integer> values = new HashSet<Integer>();

    for (int i = 0; i < size; i++) {
      values.add(i);
    }
    
    return values;
  }
}
