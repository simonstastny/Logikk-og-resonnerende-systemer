package edu.ntnu.simonst.tdt4136.csp;

/**
 *
 * @author Simon Stastny
 */
public abstract class BacktrackSearch<DomainValueType> {

  public Assignment backtrack(Assignment assignment) {
    // return the solution if it was found
    if (assignment.isComplete()) {
      return assignment;
    }

    // select unassigned variable
    Variable<DomainValueType> var = assignment.selectUnassignedVariable();

    for (DomainValueType value : var.getOrderedDomainValues()) {
      // bude to s touhle hodnotou ok? ++++
      // uloz hodnotu do assignmentu
      var.setValue(value);

      // spocitej vyplyvajici inference
      //FIXME
      assignment.recomputeInferences();

      // pokud nevednou k selhani
      //FIXME

      // uloz inference
      //FIXME

      // najdi rekurzivne reseni
      Assignment solution = backtrack(assignment);
      // pokud je reseni uspesne, vrat ho
      if (solution != null) {
        return solution;
      }

      //----
      // odstran posledni hodnotu a inference z assinmentu
      var.setValue(null);
      assignment.recomputeInferences();

    }

    return null;
  }
}
