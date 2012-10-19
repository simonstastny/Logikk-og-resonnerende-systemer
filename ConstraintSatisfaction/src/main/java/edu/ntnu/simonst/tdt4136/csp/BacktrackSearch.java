package edu.ntnu.simonst.tdt4136.csp;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
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
  public Assignment<DomainValueType> backtrack(Assignment<DomainValueType> assignment, boolean smallestDomainMode) {
    //DBG assignment.printState();
    //DBG assignment.print();

    // return the solution if it was found
    // paklize bylo reseni nalezeno, vrat jej
    if (assignment.isComplete()) {
      return assignment;
    }

    // select unassigned variable
    // vyber z oboru hodnot
    Variable<DomainValueType> var = assignment.selectUnassignedVariable(smallestDomainMode);

    Set<DomainValueType> set = new HashSet<DomainValueType>();
    set.addAll(var.getOrderedDomainValues());

    for (DomainValueType value : set) {
      // save this value to the assignment
      // uloz hodnotu do ulohy
      var.setValue(value);

      // infer conflicts
      // usud na vyplyvajici neshody
      if (ac3(assignment)) {

        // find solution recursively
        // najdi vnorene reseni
        Assignment<DomainValueType> solution = backtrack(assignment, smallestDomainMode);

        // return the solution if it is successful
        // pokud je reseni uspesne, vrat ho
        if (solution != null) {
          return solution;
        }
      }

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
   *
   * @param assignment state to check
   * @return true iff solutions exists
   */
  protected boolean ac3(Assignment<DomainValueType> assignment) {
    Queue<Constraint<DomainValueType>> worklist;

    // unary constraint - once we select a value, other values must be in conflicts
    // monadicke omezeni - pokud byla hodnota promenne vybrana, ostatni hodnoty z domeny musi byt v zasobniku neshod
    for (Variable<DomainValueType> var : assignment.getVariables()) {
      if (!var.isUnassigned() && !var.domain.isEmpty()) {
        // put all (except for chosen value) values from domain to conflicts
        // krome vybrane hodnoty presun ostatni z oboru hodnot do zasobniku neshod
        var.domain.remove(var.getValue());
        var.addConflicts(var.domain);
        // put the chosen value back to the domain (the only value now)
        // navrat zpet vybranou hodnotu do oboru hodnot
        var.domain = new HashSet<DomainValueType>();
        var.domain.add(var.getValue());
      }
    }

    // binary constraints
    // dvojclenna omezeni

    // fill the worklist queue with generated arcs
    // napln frontu orientovanymi hranami mezi promennymi
    worklist = new LinkedList<Constraint<DomainValueType>>(assignment.setupConstraints());

    while (!worklist.isEmpty()) {
      Constraint<DomainValueType> constraint = worklist.poll();

      // use revise (arc-reduce) algoritm to reduce the number of values in domain
      // pouzij postup arc-reduce pro nalezeni nedosazitelnych prvku z oboru hodnot
      Set<DomainValueType> removals = revise(constraint);

      // if found, remove reducable values from domain to conflict stack
      // pokud byly nejake hodnoty nalazeny, premisti je z domeny do zasobiku neshod
      if (!removals.isEmpty()) {
        constraint.getMaster().addConflicts(removals);
        constraint.getMaster().removeFromDomain(removals);
      }

      // if reducing resulted in the domain being empty,
      // this branch does not have a solution (and we will back track)
      // pokud obor hodnot vysel prazdny, tato vetev nema reseni
      if (constraint.getMaster().getOrderedDomainValues().isEmpty()) {
        return false;
      }
    }

    // ac-3 algoritm succeeded
    // postup skoncil uspesne
    return true;
  }

  /**
   * Returns removable domain values found to be inconsistent within constraint
   * 
   * @param constraint constraint to check and reduce
   * @return list of removable domain values from master variable
   */
  @SuppressWarnings("unchecked")
  public Set<DomainValueType> revise(Constraint<DomainValueType> constraint) {
    Variable<DomainValueType> masterOrig = constraint.getMaster();
    Variable<DomainValueType> slaveOrig = constraint.getSlave();

    Set<DomainValueType> removals = new HashSet<DomainValueType>();

    Set<DomainValueType> domainValues = new HashSet<DomainValueType>();
    domainValues.addAll(masterOrig.getOrderedDomainValues());

    // for each value of master variable
    // try to find a value of slave variable so the constraint is satisfied
    // pro kazdou hodnotu z oboru nadrizene promenne
    //najdi hodnotu z oboru podrizene promenne tak aby omezeni bylo splneno
    for (DomainValueType masterValue : domainValues) {
      boolean found = false;

      // go through slave variable's values
      // prochazej hodnoty podrizene promenne
      for (DomainValueType slaveValue : slaveOrig.getOrderedDomainValues()) {
        Constraint<DomainValueType> constraintLocal = (Constraint<DomainValueType>) constraint.clone();

        Variable<DomainValueType> master = (Variable<DomainValueType>) constraintLocal.getMaster().clone();
        if (masterOrig.getValue() == null) {
          master.setValue(masterValue);
        }

        Variable<DomainValueType> slave = (Variable<DomainValueType>) constraintLocal.getSlave().clone();
        if (slaveOrig.getValue() == null) {
          slave.setValue(slaveValue);
        }

        constraintLocal.setMaster(master);
        constraintLocal.setSlave(slave);

        // if value of slave was found,
        //break loop and continue with next value of master's variable
        // pokud jsme nasli takovou hodnotu podrizene promenne,
        // pokracuj s hledanim dalsi hodnoty nadrizene promenne
        if (constraintLocal.isSatisfied()) {
          found = true;
          break;
        }
      }

      // add to removals list every value of master variable
      // for which does not exist a value of slave variable so that they satisfy the constraint
      // takovou hodnotu nadrizene promenne, pro kterou neexistuje hodnota
      // podrizene tak aby splnovaly omezeni, pridej do seznamu k odebrani
      if (!found) {
        removals.add(masterValue);
      }
    }

    return removals;
  }
}
