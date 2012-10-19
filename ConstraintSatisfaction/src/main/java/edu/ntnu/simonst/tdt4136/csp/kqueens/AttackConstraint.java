package edu.ntnu.simonst.tdt4136.csp.kqueens;

import edu.ntnu.simonst.tdt4136.csp.Constraint;
import edu.ntnu.simonst.tdt4136.csp.Variable;

/**
 *
 * @author Simon Stastny
 */
public class AttackConstraint extends Constraint<Integer> {

  public boolean isSatisfied() {
    boolean result = false;
    
    if (getMaster() instanceof Queen && getSlave() instanceof Queen) {
      result = true;
      Queen masterQueen = (Queen) getMaster();
      Queen slaveQueen = (Queen) getSlave();

      // vertical attack
      if (masterQueen.getValue().equals(slaveQueen.getValue())) {
        result = false;
      }
      
      //horizontal attack
      if (masterQueen.getRow() == slaveQueen.getRow()) {
        result = false;
      }
      
      //diagonal attack
      if(Math.abs(masterQueen.getValue()-slaveQueen.getValue()) == Math.abs(masterQueen.getRow()-slaveQueen.getRow())) {
        result = false;
      }
    }

    return result;
  }

  public AttackConstraint(Variable<Integer> a, Variable<Integer> b) {
    this.master = a;
    this.slave = b;
  }
}
