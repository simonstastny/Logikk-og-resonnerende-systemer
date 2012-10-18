package edu.ntnu.simonst.tdt4136.csp;

/**
 *
 * @author Simon
 */
public abstract class Constraint implements Cloneable {

  protected Variable master;

  protected Variable slave;

  /**
   * Checks consistency of constraint between variables master and slave
   *
   * @return true if constraint between variables master and slave is satisfied
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
   * @return the master
   */
  public Variable getMaster() {
    return master;
  }

  /**
   * @param master the master to set
   */
  public void setMaster(Variable master) {
    this.master = master;
  }

  /**
   * @return the slave
   */
  public Variable getSlave() {
    return slave;
  }

  /**
   * @param slave the slave to set
   */
  public void setSlave(Variable slave) {
    this.slave = slave;
  }

}
