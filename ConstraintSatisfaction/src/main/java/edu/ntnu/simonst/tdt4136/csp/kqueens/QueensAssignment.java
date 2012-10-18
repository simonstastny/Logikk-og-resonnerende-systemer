package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Assignment;
import edu.ntnu.simonst.tdt4136.csp.Constraint;
import edu.ntnu.simonst.tdt4136.csp.Variable;
import java.util.ArrayList;
import java.util.List;

/**
 * 13.10.2012
 *
 * @author Simon Stastny
 */
public class QueensAssignment extends Assignment<Integer> {

  protected int size;

  public QueensAssignment(int size) {
    this.size = size;
    this.variableArray = new Queen[size];

    for (int i = 0; i < size; i++) {
      variableArray[i] = new Queen(i, size);
    }
  }

  public void printCurrent() {
    for (int row = 0; row < size; row++) {
      Queen queen = (Queen) variableArray[row];
      for (int column = 0; column < size; column++) {
        if (queen.getValue() == null) {
          System.out.print("? ");
        } else {
          System.out.print(queen.getValue() == column ? "X " : "_ ");
        }
      }
      System.out.println("");
    }
    System.out.println("");
  }

  @Override
  public boolean isComplete() {
    for (int i = 0; i < size; i++) {
      Queen queen = (Queen) variableArray[i];
      if (queen.getValue() == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Variable<Integer> selectUnassignedVariable() {
    for (int i = 0; i < size; i++) {
      Queen queen = (Queen) variableArray[i];
      if (queen.isUnassigned()) {
        return queen;
      }
    }
    return null;
  }

  @Override
  public List<Constraint> setupConstraints() {
    // make inferences - create costraints

    List<Constraint> constraints = new ArrayList<Constraint>();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i != j) {
          constraints.add(new AttackConstraint(variableArray[i], variableArray[j]));
        }
      }
    }

    return constraints;
  }

  @Override
  public String printState() {
    
    StringBuilder sb = new StringBuilder();
    
    for (Variable var : variableArray) {
      Queen queen = (Queen) var;
      sb.append("value: " + queen.getValue() + " domain: " + queen.getOrderedDomainValues() + " conflicts: " + queen.getConflicts() + "\n");
    }
    
    return sb.toString();
  }
}
