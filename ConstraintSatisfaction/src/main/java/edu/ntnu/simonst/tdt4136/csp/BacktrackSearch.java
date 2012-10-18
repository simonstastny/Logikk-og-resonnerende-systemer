package edu.ntnu.simonst.tdt4136.csp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 13.10.2012
 *
 * @author Simon Stastny
 */
public class BacktrackSearch<DomainValueType> {

  /**
   * search using backtrack
   *
   * @param assignment assignment state to solve
   * @return solved assignment
   */
  public Assignment<DomainValueType> backtrack(Assignment<DomainValueType> assignment) {
    System.out.println("CLEAN");
    System.out.println(assignment.printState());

    // return the solution if it was found
    // paklize bylo reseni nalezeno, vrat jej
    if (assignment.isComplete()) {
      return assignment;
    }

    // select unassigned variable
    // vyber z oboru hodnot
    Variable<DomainValueType> var = assignment.selectUnassignedVariable();
    
    Set<DomainValueType> set = new HashSet<DomainValueType>();
    set.addAll(var.getOrderedDomainValues());

    for (DomainValueType value : set) {
      // save this value to the assignment
      // uloz hodnotu do ulohy
      var.setValue(value);
      
      assignment.printCurrent();

      // infer conflicts
      // usud na vyplyvajici neshody
      if (ac3(assignment)) { //FIXME

        // find solution recursively
        // najdi vnorene reseni
        Assignment<DomainValueType> solution = backtrack(assignment);

        // return the solution if it is successful
        // pokud je reseni uspesne, vrat ho
        if (solution != null) {
          return solution;
        }
      }


      System.out.println(assignment.printState()); //FIXME
      System.out.println("NIKAM TO NEVEDE"); //FIXME

      // this path is not successful -> back track
      // remove last step and inferences
      // v teto vetvi nebylo resnei uspesne -> backtracking
      // odstran posledni hodnotu a usudky z ulohy
      var.setValue(null);
      assignment.popConflicts();
    }

    // it the flow hit this point, the solution was not found
    // pokud program dospel az sem, reseni nebylo nalezeno
    return null;
  }

  protected boolean ac3(Assignment assignment) {

    // unary constraint - once we select a value, other values must be in conflicts

    for (Variable var : assignment.variableArray) {
      if (!var.isUnassigned() && !var.domain.isEmpty()) {
        var.domain.remove(var.getValue());
        var.putToConflicts(var.domain);
        var.domain = new HashSet();
        var.domain.add(var.getValue());
      }
    }

    // binary constraints

    List<Constraint> worklist = assignment.setupConstraints();

    while (!worklist.isEmpty()) {
      Constraint constraint = worklist.get(0);
      worklist.remove(0);

      Set<DomainValueType> removals = revise(constraint);

      if (!removals.isEmpty() && constraint.getA().isUnassigned()) {
        constraint.getA().putToConflicts(removals);
        for (DomainValueType val : removals) {
          constraint.getA().removeValueFromDomain(val);
        }
      }


      if (removals.isEmpty()) {
        if (constraint.getA().isUnassigned() && constraint.getA().getOrderedDomainValues().isEmpty()) {
          return false;
        } else {
          //FIXME
        }
      }

    }

    return true;
  }

  public Set<DomainValueType> revise(Constraint constraint) {

    Variable a = constraint.getA();
    Variable b = constraint.getB();

    Set<DomainValueType> removals = new HashSet<DomainValueType>();

    Set<DomainValueType> domainValues = new HashSet();
    domainValues.addAll(a.getOrderedDomainValues());

    for (DomainValueType valueA : domainValues) {
      boolean found = false;

      for (Object valueB : b.getOrderedDomainValues()) {

        Constraint c = (Constraint) constraint.clone();

        Variable aa = (Variable) c.getA().clone();
        if (a.getValue() == null) {
          aa.setValue(valueA);
        } else {
          aa.setValue(a.getValue());
        }

        Variable bb = (Variable) c.getB().clone();
        if (b.getValue() == null) {
          bb.setValue(valueB);
        } else {
          bb.setValue(b.getValue());
        }

        c.setA(aa);
        c.setB(bb);

        if (c.isSatisfied()) {
          found = true;
          break;
        }
      }


      // B domain is empty - it is assigned FIXME
      if (b.getValue() != null) {
        Constraint c = (Constraint) constraint.clone();

        Variable aa = (Variable) c.getA().clone();
        if (a.getValue() == null) {
          aa.setValue(valueA);
        } else {
          aa.setValue(a.getValue());
        }

        Variable bb = (Variable) c.getB().clone();
        bb.setValue(b.getValue());

        c.setA(aa);
        c.setB(bb);

        if (c.isSatisfied()) {
          found = true;
        }
      }

      if (!found) {
        removals.add(valueA);
      }
    }

    return removals;
  }
}
