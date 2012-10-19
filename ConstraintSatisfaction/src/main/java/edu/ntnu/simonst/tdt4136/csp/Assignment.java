package edu.ntnu.simonst.tdt4136.csp;

import java.util.List;

/**
 *
 * @author Simon Stastny
 */
public abstract class Assignment<DomainValueType> {
  
  protected Variable<DomainValueType>[] variables;

  /**
   * prints the currently explored state of the solution
   */
  public abstract void print();
  
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
  public abstract Variable<DomainValueType> selectUnassignedVariable(boolean smallestDomainMode);
 
  /**
   *
   * @return list of generates constraints
   */
  public abstract List<Constraint<DomainValueType>> setupConstraints();

  
  /**
   * pops conflict stack and puts top items back to domain
   */
  protected void popConflicts() {
    for(Variable<DomainValueType> var : getVariables()) {
      if(var.getConflicts() != null && !var.getConflicts().isEmpty()) {
        var.domain.addAll(var.getConflicts().pop());        
      }
      
      // if this variable is not assigned clear its contents
      if(var.isUnassigned()) {
        var.clear();
      }
    }
  }

  /**
   * @return the variables
   */
  protected Variable<DomainValueType>[] getVariables() {
    return variables;
  }
}
