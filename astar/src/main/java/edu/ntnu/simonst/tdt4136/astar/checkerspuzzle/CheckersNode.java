package edu.ntnu.simonst.tdt4136.astar.checkerspuzzle;

import edu.ntnu.simonst.tdt4136.astar.Fringe;
import edu.ntnu.simonst.tdt4136.astar.SearchNode;

/**
 *
 * @author Simon Stastny
 */
public class CheckersNode extends SearchNode {

  @Override
  public void calculateCosts() {
    // cost estimate is distance of two states (see @s CheckersState.getDistance(a,b)
    CheckersState goalState = (CheckersState) bfs.getGoal().getState();
    CheckersState currentState = (CheckersState) this.getState();

    costEstimate = goalState.getDistance(goalState, currentState);

    // current cost is parent's cost + 1, while root has 0 current cost
    if (getCurrentParent() != null) {
      currentCost = 1 + getCurrentParent().getCurrentCost();
    }

  }

  @Override
  public boolean isSolution() {
    if (bfs.getGoal() instanceof CheckersNode && bfs.getGoal().getState() instanceof CheckersState) {
      CheckersState other = (CheckersState) bfs.getGoal().getState();
      CheckersState thisOne = (CheckersState) this.getState();

      // two states are equal when their identifiers are equal
      if (other.getIdentifier().equals(thisOne.getIdentifier())) {
        return true;
      }

    }
    return false;
  }

  @Override
  public Fringe getChildren() {
    children = new Fringe();

    // generate all children of current node
    // make all possible one-swap permutations
    CheckersState currentState = (CheckersState) state;

    // apply operators to generate children
    addNode(currentState.moveLeft());
    addNode(currentState.moveRight());
    addNode(currentState.jumpLeft());
    addNode(currentState.jumpRight());

    return super.getChildren();
  }

  @Override
  public int compareTo(Object t) {
    if (t instanceof CheckersNode) {
      CheckersNode other = (CheckersNode) t;

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

  /**
   * Creates a new state, new node, links them and add is to the Fringe.
   * @param newperm permutation for a new state and node to be created and added to the fringe
   */
  protected void addNode(String newperm) {
    // sometimes the move/jump operation could not be performed and the permutations stays the same
    // we do not want those states to be added to the fringe again
    if (((CheckersState) getState()).getIdentifier().equals(newperm)) {
      return;
    }
    
    // get the node from the node-table
    SearchNode genNeighbour = bfs.getNodeTable().get(newperm);

    // node was not found
    if (genNeighbour == null) {
      // create a new node and set the state to it
      genNeighbour = new CheckersNode();
      genNeighbour.setState(new CheckersState(newperm));
      genNeighbour.setBFs(bfs);
      
      //this is a new node, we should record which parent was the best for him
      genNeighbour.setCurrentParent(this);

      //calculate his costs
      genNeighbour.calculateCosts();

      // save the new node to the node table
      bfs.getNodeTable().put(newperm, genNeighbour);
    }

    // add the child to the fringe only if it was not closed before (if it is new)
    if (genNeighbour.isOpen()) {
      children.add(genNeighbour);
    }
  }
}
