package edu.ntnu.simonst.tdt4136.astar;

/**
 *
 * @author Simon Stastny
 */
public abstract class SearchNode implements Comparable{

  // delegate to BFS instance, which hold i.e. node-map and other stuff
  protected BestFirstSearch bfs;

  public void setBFs(BestFirstSearch bfs) {
    this.bfs = bfs;
  }
  
  // search state for this node
  protected SearchState state;

  // children of this node
  protected Fringe children;

  // possible parents of this node
  protected Fringe possibleParents;

  // current parent (best parent so far)
  protected SearchNode currentParent;

  // is open closed or open (i.e. visited)
  protected boolean open = true;
  
  public boolean isOpen() {
    return open;
  }
  
  public void close() {
    open = false;
  }

  /**
   * calculate cost estimate from this node to goal state
   * @param goalNode 
   */
  public abstract void calculateCosts(SearchNode goalNode);

  protected int costEstimate;
  protected int currentCost = 0;

  public int getCurrentCost() {
    return currentCost;
  }
  
  public int getCostEstimate() {
    return costEstimate;
  }

  public abstract boolean isSolution(SearchNode goal);

  public int getTotalCostEstimate() {
    return getCurrentCost() + getCostEstimate();
  }

  public SearchState getState() {
    return state;
  }

  public Fringe getChildren(SearchNode goal) {
    return children;
  }

  public SearchNode getCurrentParent() {
    return currentParent;
  }

  public Fringe getPossibleParents() {
    return possibleParents;
  }

  public void setCurrentParent(SearchNode currentParent) {
    this.currentParent = currentParent;
  }

  public void setState(SearchState state) {
    this.state = state;
  }

  /**
   * Method for printing out a reconstruction of the path from root to goal node.
   */
  public void reconstructPath() {
    System.out.println("current cost: " + this.getCurrentCost());
    System.out.println("cost estimate: " + this.getCostEstimate());
    System.out.println("state: " + this.getState().toString());
    System.out.println("----------------------------------------------");

    if (this.getCurrentParent() != null) {
      getCurrentParent().reconstructPath();
    }
  }
}
