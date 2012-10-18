package edu.ntnu.simonst.tdt4136.csp;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
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
    //DBG System.out.println(assignment.printState());
    //DBG assignment.printCurrent();

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

      //DBG assignment.printCurrent();

      // infer conflicts
      // usud na vyplyvajici neshody
      if (ac3(assignment)) {

        // find solution recursively
        // najdi vnorene reseni
        Assignment<DomainValueType> solution = backtrack(assignment);

        // return the solution if it is successful
        // pokud je reseni uspesne, vrat ho
        if (solution != null) {
          return solution;
        }
      }

      //DBG System.out.println(assignment.printState());

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

  /**
   * Checks arc consistencies 
   * @param assignment
   * @return true if solutions exists
   */
  protected boolean ac3(Assignment assignment) {

    // unary constraint - once we select a value, other values must be in conflicts
    for (Variable var : assignment.variables) {
      if (!var.isUnassigned() && !var.domain.isEmpty()) {
        // put all (except for chosen value) values from domain to conflicts
        var.domain.remove(var.getValue());
        var.addConflicts(var.domain);
        // put the chosen value back to the domain (the only value now)
        var.domain = new HashSet();
        var.domain.add(var.getValue());
      }
    }

    // binary constraints
    Queue<Constraint> worklist = new LinkedList<Constraint>(assignment.setupConstraints());

    Constraint constraint = null;

    while (!worklist.isEmpty()) {
      constraint = worklist.poll();

      Set<DomainValueType> removals = revise(constraint);

      if (!removals.isEmpty() && constraint.getMaster().isUnassigned()) {
        constraint.getMaster().addConflicts(removals);
        constraint.getMaster().removeFromDomain(removals);
      }


      if (removals.isEmpty()) {
        if (constraint.getMaster().isUnassigned() && constraint.getMaster().getOrderedDomainValues().isEmpty()) {
          return false;
        } 
      }
    }

    return true;
  }

  public Set<DomainValueType> revise(Constraint constraint) {
    Variable masterOrig = constraint.getMaster();
    Variable slaveOrig = constraint.getSlave();

    Set<DomainValueType> removals = new HashSet<DomainValueType>();

    Set<DomainValueType> domainValues = new HashSet();
    domainValues.addAll(masterOrig.getOrderedDomainValues());

    for (DomainValueType masterValue : domainValues) {
      boolean found = false;

      for (Object slaveValue : slaveOrig.getOrderedDomainValues()) {
        constraint =  (Constraint) constraint.clone();

        Variable master = (Variable) constraint.getMaster().clone();
        if (masterOrig.getValue() == null) {
          master.setValue(masterValue);
        }

        Variable slave = (Variable) constraint.getSlave().clone();
        if (slaveOrig.getValue() == null) {
          slave.setValue(slaveValue);
        }

        constraint.setMaster(master);
        constraint.setSlave(slave);

        if (constraint.isSatisfied()) {
          found = true;
          break;
        }
      }

      if (!found) {
        removals.add(masterValue);
      }
    }

    return removals;
  }
}
