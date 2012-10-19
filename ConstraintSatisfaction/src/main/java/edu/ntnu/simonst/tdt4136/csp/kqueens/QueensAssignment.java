package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Assignment;
import edu.ntnu.simonst.tdt4136.csp.Constraint;
import edu.ntnu.simonst.tdt4136.csp.Variable;
import java.util.ArrayList;
import java.util.List;

/**
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

  @Override
  public void printBoard() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < gameSize; row++) {
      Queen queen = (Queen) getVariables()[row];
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
    // checks if all queens are assigned a column
    // zkontroluje zda vsechny damy jsou prirazeny do sloupcu
    for (int i = 0; i < gameSize; i++) {
      Queen queen = (Queen) getVariables()[i];
      if (queen.getValue() == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Variable<Integer> selectUnassignedVariable() {
    Queen smallest = null;
    // selects unassigned queen with smallest domain
    // vybere neobsazenou kralovnu s nejmensim oborem hodnot
    for (int i = 0; i < gameSize; i++) {
      Queen queen = (Queen) getVariables()[i];
      if (queen.isUnassigned() && (smallest == null || smallest.getOrderedDomainValues().size() > queen.getOrderedDomainValues().size())) {
        smallest = queen;
      }
    }
    return smallest;
  }

  @Override
  public List<Constraint<Integer>> setupConstraints() {
    // create constraints on arcs (between all variables)
    // vytvori omezeni nad vsemi hranami
    List<Constraint<Integer>> constraints = new ArrayList<Constraint<Integer>>();

    for (int i = 0; i < gameSize; i++) {
      for (int j = 0; j < gameSize; j++) {
        if (i != j) {
          constraints.add(new AttackConstraint(getVariables()[i], getVariables()[j]));
        }
      }
    }

    return constraints;
  }

  @Override
  public void printState() {
    StringBuilder sb = new StringBuilder();
    
    for (Queen queen : (Queen[]) getVariables()) {
      sb.append("value: ").append(queen.getValue()).append(" domain: ").append(queen.getOrderedDomainValues()).append(" conflicts: ").append(queen.getConflicts()).append("\n");
    }
    
    System.out.println(sb.toString());
  }
}
