package edu.ntnu.simonst.tdt4136.astar;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Simon Stastny
 */
public abstract class BestFirstSearch implements Runnable{
  
  // SETUP
  
  protected boolean pathReconstruction = true;

  public boolean isPathReconstruction() {
    return pathReconstruction;
  }

  public void setPathReconstruction(boolean pathReconstruction) {
    this.pathReconstruction = pathReconstruction;
  }
  
  
  // A*
  
  //FIXME encapsulate
  protected Map<String, SearchNode> nodeTable = new HashMap<String, SearchNode>();

  protected Fringe agenda = new Fringe();

  protected SearchNode root;

  protected SearchNode goal;

  protected Fringe closed = new Fringe();

  protected int fringeGrowthLimit = Integer.MAX_VALUE;

  public Fringe getAgenda() {
    return agenda;
  }

  public void setAgenda(Fringe agenda) {
    this.agenda = agenda;
  }

  public Fringe getClosed() {
    return closed;
  }

  public void setClosed(Fringe closed) {
    this.closed = closed;
  }

  public Map<String, SearchNode> getNodeTable() {
    return nodeTable;
  }

  public void setNodeTable(Map<String, SearchNode> nodeTable) {
    this.nodeTable = nodeTable;
  }

  public SearchNode getRoot() {
    return root;
  }

  public void setRoot(SearchNode root) {
    this.root = root;
  }

  public int getFringeGrowthLimit() {
    return fringeGrowthLimit;
  }

  public void setFringeGrowthLimit(int fringeGrowthLimit) {
    this.fringeGrowthLimit = fringeGrowthLimit;
  }

  public SearchNode getGoal() {  
    return goal;
  }

  public void setGoal(SearchNode goal) {
    this.goal = goal;
  }

  // MAIN APPLICATION LOGIC FIXME
  
  public void search() {

    SearchNode current = root;

    int runCounter = 0;

    while (true) {
      current.close();
      closed.add(current);

      agenda.addAll(current.getChildren(goal));

      current = agenda.getNearestNode(goal);

      runCounter++;

      agenda.removeNearest(goal);

      //System.out.println(" ---- " + current.getState() + " closed: " + closed.size());
      
      if (current.isSolution(goal)) {
        System.out.print("run: " + runCounter);
        System.out.println(" ----- WINNER: " + current.getState() + " is equal " + goal.getState());
        break;
      }
    }

    if (pathReconstruction) {
      System.out.println("PATH RECONSTRUCTION: ");
      current.reconstructPath();
    }

  }
}
