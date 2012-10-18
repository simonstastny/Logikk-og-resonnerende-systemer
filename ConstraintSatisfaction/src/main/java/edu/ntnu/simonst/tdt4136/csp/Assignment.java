package edu.ntnu.simonst.tdt4136.csp;

import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * 13.10.2012
 *
 * @author Simon Stastny
 */
public abstract class Assignment<DomainValueType> {
  
  protected Variable[] variableArray;

  /**
   * prints the currently explored state of the solution
   */
  public abstract void printCurrent();
  
  public abstract String printState();

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

  void popConflicts() {
    for(Variable var : variableArray) {
      if(//var.isUnassigned() && 
              var.getConflicts() != null &&
              !var.getConflicts().isEmpty()) {
        Set<DomainValueType> recharge = (Set<DomainValueType>) var.getConflicts().pop();
        var.domain.addAll(recharge);        
      }
      
      if(var.isUnassigned()) {
        var.domain = var.generateDomain();
        var.conflicts = new Stack();
        var.setValue(null);
      }
    }
  }
}
