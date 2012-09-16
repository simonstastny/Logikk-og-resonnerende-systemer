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
    // first node to be expanded is root
    SearchNode current = root;

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
    System.out.print("run: " + runCounter);
    System.out.println(" ----- WINNER: " + current.getState() + " is equal " + goal.getState());

    // if path reconstruction is enables, print the path
    if (pathReconstruction) {
      System.out.println("PATH RECONSTRUCTION: ");
      current.reconstructPath();
    }
  }
}
