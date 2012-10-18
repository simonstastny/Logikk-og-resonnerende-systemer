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

  protected int gameSize;

  public QueensAssignment(int size) {
    this.gameSize = size;
    this.variables = new Queen[size];

    for (int i = 0; i < size; i++) {
      variables[i] = new Queen(i, size);
    }
  }

  public void printCurrent() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < gameSize; row++) {
      Queen queen = (Queen) variables[row];
      for (int column = 0; column < gameSize; column++) {
        if (queen.getValue() == null) {
          sb.append("? ");
        } else {
          sb.append(queen.getValue() == column ? "X " : "_ ");
        }
      }
      sb.append("\n");
    }
    sb.append("\n");
    System.out.println(sb.toString());
  }

  @Override
  public boolean isComplete() {
    for (int i = 0; i < gameSize; i++) {
      Queen queen = (Queen) variables[i];
      if (queen.getValue() == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Variable<Integer> selectUnassignedVariable() {
    // selects first queen which is not assigned a column
    // i.e. first variable without value
    for (int i = 0; i < gameSize; i++) {
      Queen queen = (Queen) variables[i];
      if (queen.isUnassigned()) {
        return queen;
      }
    }
    return null;
  }

  @Override
  public List<Constraint> setupConstraints() {
    // create constraints on arcs (between all variables)
    List<Constraint> constraints = new ArrayList<Constraint>();

    for (int i = 0; i < gameSize; i++) {
      for (int j = 0; j < gameSize; j++) {
        if (i != j) {
          constraints.add(new AttackConstraint(variables[i], variables[j]));
        }
      }
    }

    return constraints;
  }

  @Override
  public void printState() {
    StringBuilder sb = new StringBuilder();
    
    for (Variable var : variables) {
      Queen queen = (Queen) var;
      sb.append("value: ").append(queen.getValue()).append(" domain: ").append(queen.getOrderedDomainValues()).append(" conflicts: ").append(queen.getConflicts()).append("\n");
    }
    
    System.out.println(sb.toString());
  }
}
