package edu.ntnu.simonst.tdt4136.csp;

/**
 *
 * @author Simon
 */
public abstract class Constraint implements Cloneable {

  protected Variable a;

  protected Variable b;

  /**
   * Checks consistency of constraint between variables a and b
   *
   * @return true if constraint between variables a and b is satisfied
   */
  public abstract boolean isSatisfied();


  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  /**
   * @return the a
   */
  public Variable getA() {
    return a;
  }

  /**
   * @param a the a to set
   */
  public void setA(Variable a) {
    this.a = a;
  }

  /**
   * @return the b
   */
  public Variable getB() {
    return b;
  }

  /**
   * @param b the b to set
   */
  public void setB(Variable b) {
    this.b = b;
  }
}
