package edu.ntnu.simonst.tdt4136.astar.checkerspuzzle;

import edu.ntnu.simonst.tdt4136.astar.Fringe;
import edu.ntnu.simonst.tdt4136.astar.SearchNode;

/**
 *
 * @author Simon Stastny
 */
public class CheckersNode extends SearchNode {

  @Override
  public void calculateCosts(SearchNode goalNode) {
    CheckersState goalState = (CheckersState) goalNode.getState();
    CheckersState currentState = (CheckersState) this.getState();

    costEstimate = goalState.getDistance(goalState, currentState);

    // child is in distance 1 from parent
    if (getCurrentParent() != null) {
      currentCost = 1 + getCurrentParent().getCurrentCost();
    }

  }

  @Override
  public boolean isSolution(SearchNode goal) {
    if (goal instanceof CheckersNode && goal.getState() instanceof CheckersState) {
      CheckersState other = (CheckersState) goal.getState();
      CheckersState thisOne = (CheckersState) this.getState();

      if (other.getIdentifier().equals(thisOne.getIdentifier())) {
        return true;
      }

    }
    return false;
  }

  @Override
  public Fringe getChildren(SearchNode goal) {
      children = new Fringe();

    // generate all children of current node
    // make all possible one-swap permutations
    CheckersState currentState = (CheckersState) state;

    // aply operators to generate children
    addNode(currentState.moveLeft(), goal);
    addNode(currentState.moveRight(), goal);
    addNode(currentState.jumpLeft(), goal);
    addNode(currentState.jumpRight(), goal);

    return super.getChildren(goal);
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

  //FIXME
  protected void addNode(String newperm, SearchNode goal) {
    SearchNode genNeighbour = bfs.getNodeTable().get(newperm);
    int gameSize = (newperm.length()-1)/2;

    if (genNeighbour == null) {
      // create a new node
      genNeighbour = new CheckersNode();

      genNeighbour.setState(new CheckersState(gameSize, newperm));

      genNeighbour.setBFs(bfs);
      //this is a new node, we should record which parent was the best for him
      genNeighbour.setCurrentParent(this);

      //calculate his costs
      genNeighbour.calculateCosts(goal);

      // save the new node to the node table
      bfs.getNodeTable().put(newperm, genNeighbour);
    }

    // add the child to the fringe only if it was not closed before (if it is new)
    if (genNeighbour.isOpen()) {
      children.add(genNeighbour);
    }
  }
}
