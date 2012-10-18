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
    
    if (getA() instanceof Queen && getB() instanceof Queen) {
      result = true;
      Queen aq = (Queen) getA();
      Queen bq = (Queen) getB();

      // vertical attack
      if (aq.getValue().equals(bq.getValue())) {
        result = false;
      }
      
      //horizontal attack
      if (aq.getRow() == bq.getRow()) {
        result = false;
      }
      
      //diagonal attack
      if(Math.abs(aq.getValue()-bq.getValue()) == Math.abs(aq.getRow()-bq.getRow())) {
        result = false;
      }

    }

    return result;
  }

  public AttackConstraint(Variable a, Variable b) {
    this.a = a;
    this.b = b;
  }
  
  
}
