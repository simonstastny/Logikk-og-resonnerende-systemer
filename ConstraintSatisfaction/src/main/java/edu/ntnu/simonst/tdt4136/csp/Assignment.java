package edu.ntnu.simonst.tdt4136.csp;

/**
 * 13.10.2012
 * @author Simon Stastny
 */
public abstract class Assignment {

  /**
   * prints the currently explored state of the solution
   */
  public abstract void printCurrent();
  
  /**
   * Checks for the completeness and validity of solution.
   * @return true when solution is complete and valid
   */
  public abstract boolean isComplete();
  
  /**
   * Returns first unassigned variable.
   * @return unassigned variable from assignment
   */
  public abstract Variable selectUnassignedVariable();
  
  /**
   * recomputes inferences from current state of solution
   */
  public abstract void recomputeInferences();
}
