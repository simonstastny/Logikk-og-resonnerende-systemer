package edu.ntnu.simonst.tdt4136.sa;

/**
 *
 * @author Simon Stastny
 */
public abstract class Solution {

  public abstract String getIdentifier();

  public abstract int energy();

  public abstract Solution mutate();

  public abstract void print();
}
