package edu.ntnu.simonst.tdt4136.csp;

import java.util.List;
import java.util.Set;

/**
 * 13.10.2012
 *
 * @author Simon Stastny
 */
public abstract class Assignment<DomainValueType> {
  
  protected Variable[] variables;

  /**
   * prints the currently explored state of the solution
   */
  public abstract void printCurrent();
  
  /**
   * prints the state of puzzle
   */
  public abstract void printState();

  /**
   * Checks for the completeness and validity of solution.
   *
   * @return true when solution is complete and valid
   */
  public abstract boolean isComplete();

  /**
   * Returns first unassigned variable.
   *
   * @return unassigned variable from assignment
   */
  public abstract Variable<DomainValueType> selectUnassignedVariable();
 
  /**
   *
   * @return list of generates constraints
   */
  public abstract List<Constraint> setupConstraints();

  protected void popConflicts() {
    for(Variable var : variables) {
      if(var.getConflicts() != null && !var.getConflicts().isEmpty()) {
        var.domain.addAll((Set<DomainValueType>) var.getConflicts().pop());        
      }
      
      if(var.isUnassigned()) {
        var.clear();
      }
    }
  }
}
