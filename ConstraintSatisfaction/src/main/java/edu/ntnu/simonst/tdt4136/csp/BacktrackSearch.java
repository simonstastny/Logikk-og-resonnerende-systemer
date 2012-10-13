package edu.ntnu.simonst.tdt4136.csp;

/**
 * 13.10.2012
 * @author Simon Stastny
 */
public class BacktrackSearch<DomainValueType> {

  public Assignment backtrack(Assignment assignment) {
    
    //DBG
    //assignment.printCurrent();
    
    // return the solution if it was found
    // paklize bylo reseni nalezeno, vrat jej
    if (assignment.isComplete()) {
      return assignment;
    }

    // select unassigned variable
    // vyber z oboru hodnot
    Variable<DomainValueType> var = assignment.selectUnassignedVariable();

    for (DomainValueType value : var.getOrderedDomainValues()) {
      // save this value to the assignment
      // uloz hodnotu do ulohy
      var.setValue(value);

      // infer conflicts
      // usud na vyplyvajici neshody
      assignment.recomputeInferences();

      // find solution recursively
      // najdi vnorene reseni
      Assignment solution = backtrack(assignment);
      
      // return the solution if it is successful
      // pokud je reseni uspesne, vrat ho
      if (solution != null) {
        return solution;
      }

      // this path is not successful -> back track
      // remove last step and inferences
      // v teto vetvi nebylo resnei uspesne -> backtracking
      // odstran posledni hodnotu a usudky z ulohy
      var.setValue(null);
      if(!var.conflicts.empty())var.conflicts.pop();
      assignment.recomputeInferences(); //FIXME
    }

    // it the flow hit this point, the solution was not found
    // pokud program dospel az sem, reseni nebylo nalezeno
    return null;
  }
}
