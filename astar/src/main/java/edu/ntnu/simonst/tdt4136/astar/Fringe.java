package edu.ntnu.simonst.tdt4136.astar;

import java.util.ArrayList;

/**
 * Fringe is a sorted ArrayList of SearchNodes which automatically sorts them before removing/popping
 * @author Simon Stastny
 */
public class Fringe extends ArrayList<SearchNode> {

  /**
   * Returns search node from the fringe which is estimated to be closest to the goal.
   * @param goal search node which we want to get to
   * @return search node from the fringe which is estimated to be closest to the goal
   */
  public SearchNode getNearestNode(SearchNode goal) {
    // search-nodes are comparable
    java.util.Collections.sort(this);

    if (this.size() > 0) {
      return this.get(0);
    } else {
      return null;
    }
  }

  /**
   * Removes first node from the fringe.
   * @param goal search node which we want to get to (to know which is nearest)
   */
  public void removeNearest(SearchNode goal) {
    // search-nodes are comparable
    java.util.Collections.sort(this);
    if (this.size() > 0) {
      this.remove(0);
    }
  }
}
