package edu.ntnu.simonst.tdt4136.sa;

/**
 *
 * @author Simon Stastny
 */
public abstract class Solution {

  /**
   * the smaller the energy is, the better the solutions is
   * @return energy of solution
   */
  public abstract int energy();
  
  /**
   * the smaller the energy is, the better the solutions is
   * this kind of energy is used for evaluating the probability
   * @return relative energy of solution
   */
  public abstract double energyRelative();

  public abstract Solution mutate();

  public abstract void print();
}
