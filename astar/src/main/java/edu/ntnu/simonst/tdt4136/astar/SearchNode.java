package edu.ntnu.simonst.tdt4136.astar;

/**
 *
 * @author Simon Stastny
 */
public abstract class SearchNode implements Comparable {

  // delegate to BFS instance, which hold i.e. node-map, goal-node and other stuff
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

  /**
   * Method for resolving whether this node is a solution.
   * @return true if node is solution
   */
  public abstract boolean isSolution();

  /**
   * Returns whether this node is open or closed.
   * @return true if node is open
   */
  public boolean isOpen() {
    return open;
  }

  /**
   * closes node (after expansion)
   */
  public void close() {
    open = false;
  }

  /**
   * calculate cost estimate from this node to goal state
   */
  public abstract void calculateCosts();
  protected int costEstimate;

  protected int currentCost = 0;

  public int getCurrentCost() {
    return currentCost;
  }

  public int getCostEstimate() {
    return costEstimate;
  }

  public int getPrintCostTotal() {
    return getTotalCostEstimate();
  }

  public int getPrintCostEstimate() {
    return getCostEstimate();
  }

  public int getPrintCurrentCost() {
    return getCurrentCost();
  }

  public int getTotalCostEstimate() {
    return getCurrentCost() + getCostEstimate();
  }

  public SearchState getState() {
    return state;
  }

  public Fringe getChildren() {
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
    System.out.println("current cost: " + this.getPrintCurrentCost());
    System.out.println("cost estimate: " + this.getCostEstimate());
    System.out.println("state: " + this.getState().toString());
    System.out.println("----------------------------------------------");

    if (this.getCurrentParent() != null) {
      getCurrentParent().reconstructPath();
    }
  }

  @Override
  public int compareTo(Object t) {
    if (t instanceof SearchNode) {
      SearchNode other = (SearchNode) t;

      if (other.getTotalCostEstimate() > this.getTotalCostEstimate()) {
        return -1;
      } else if (other.getTotalCostEstimate() < this.getTotalCostEstimate()) {
        return 1;
      } else {
        return 0;
      }

    } else {
      return 0;
    }
  }
}
