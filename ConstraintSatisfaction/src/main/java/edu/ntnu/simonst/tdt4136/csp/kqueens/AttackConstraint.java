package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Constraint;
import edu.ntnu.simonst.tdt4136.csp.Variable;

/**
 *
 * @author Simon Stastny
 */
public class AttackConstraint extends Constraint {

  public boolean isSatisfied() {
    boolean result = false;
    
    if (getMaster() instanceof Queen && getSlave() instanceof Queen) {
      result = true;
      Queen master = (Queen) getMaster();
      Queen slave = (Queen) getSlave();

      // vertical attack
      if (master.getValue().equals(slave.getValue())) {
        result = false;
      }
      
      //horizontal attack
      if (master.getRow() == slave.getRow()) {
        result = false;
      }
      
      //diagonal attack
      if(Math.abs(master.getValue()-slave.getValue()) == Math.abs(master.getRow()-slave.getRow())) {
        result = false;
      }
    }

    return result;
  }

  public AttackConstraint(Variable a, Variable b) {
    this.master = a;
    this.slave = b;
  }
}
