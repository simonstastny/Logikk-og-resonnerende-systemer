package edu.ntnu.simonst.tdt4136.csp;

/**
 *
 * @author Simon Stastny
 */
public abstract class Assignment {

  public abstract void printCurrent();
  
  public abstract boolean isComplete();
  
  public abstract Variable selectUnassignedVariable();
  
  public abstract void recomputeInferences();
}
