package edu.ntnu.simonst.tdt4136.astar;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Simon Stastny
 */
public abstract class BestFirstSearch implements Runnable {

  // SETUP
  // path reconstruction is implicitly ON
  protected boolean pathReconstruction = true;

  public void setPathReconstruction(boolean pathReconstruction) {
    this.pathReconstruction = pathReconstruction;
  }
  
  // A*
  protected Map<String, SearchNode> nodeTable = new HashMap<String, SearchNode>();

  protected Fringe agenda = new Fringe();

  protected Fringe closed = new Fringe(); //FIXME do I even use this?
  
  protected SearchNode root;

  protected SearchNode goal;

  public Fringe getAgenda() {
    return agenda;
  }

  public Fringe getClosed() {
    return closed;
  }

  public Map<String, SearchNode> getNodeTable() {
    return nodeTable;
  }

  protected void setRoot(SearchNode root) {
    this.root = root;
  }

  protected void setGoal(SearchNode goal) {
    this.goal = goal;
  }

  public SearchNode getGoal() {
    return goal;
  }
  
  // MAIN APPLICATION LOGIC
  public void search() {
    // each search deserves own node table
    this.nodeTable = new HashMap<String, SearchNode>();
    this.agenda = new Fringe();
    this.closed = new Fringe();
    
    // first node to be expanded is root
    SearchNode current = root;
    current.calculateCosts();

    int runCounter = 0; //DBG

    // go through until 
    while (!current.isSolution()) {
      runCounter++; //DBG

      // close current node (mark as visited), remove it from the fringe and add it to the closed-fringe
      current.close();
      closed.add(current);
      agenda.removeNearest(getGoal());

      // add children to agenda/fringe
      agenda.addAll(current.getChildren());

      // pick nearest node from agenda/fringe and remove it from there
      current = agenda.getNearestNode(goal);
    }

    // the solution was found, print information about solution state
    System.out.println("Solution " + current.getState() + " equal to goal " + goal.getState() + " found in run " + runCounter + ". Cost of solution is " + current.getFinalCost() + ".");

    // if path reconstruction is enables, print the path
    if (pathReconstruction) {
      System.out.println("\n\nPath reconstruction fra " + root.getState().getIdentifier() + " til " + goal.getState().getIdentifier() + " (in reverse): \n");
      current.reconstructPath();
    }
  }
}
