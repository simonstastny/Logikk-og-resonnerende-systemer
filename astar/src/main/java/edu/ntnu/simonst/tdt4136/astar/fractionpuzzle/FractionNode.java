package edu.ntnu.simonst.tdt4136.astar.fractionpuzzle;

import edu.ntnu.simonst.tdt4136.astar.Fringe;
import edu.ntnu.simonst.tdt4136.astar.SearchNode;


/**
 *
 * @author Simon Stastny
 */
public class FractionNode extends SearchNode {

  @Override
  public void calculateCosts(SearchNode goalNode) {
    if (goalNode.getState() instanceof FractionState) {
      FractionState goalFraction = (FractionState) goalNode.getState();
      FractionState thisFraction = (FractionState) this.getState();

      costEstimate = goalFraction.getDistance(thisFraction, goalFraction);
    } else {
      costEstimate = Integer.MAX_VALUE;
    }

    currentCost = 0;
  }

  @Override
  public boolean isSolution(SearchNode goal) {
    return getCostEstimate() == 0;
  }

  @Override
  public Fringe getChildren(SearchNode goal) {
    if (children == null) {
      children = new Fringe();

      // generate all children of current node
      // make all possible one-swap permutations
      for (int i = 0; i < 9; i++) {
        for (int j = i + 1; j < 9; j++) {

          String newperm = ((FractionState) this.getState()).swap(state.getIdentifier(), i, j);

          SearchNode genNeighbour = bfs.getNodeTable().get(newperm);

          if (genNeighbour == null) {
            // create a new node
            genNeighbour = new FractionNode();
            genNeighbour.setState(new FractionState(newperm));
            genNeighbour.setBFs(bfs);
            //calculate his costs
            genNeighbour.calculateCosts(goal);

            //this is a new node, we should record which parent was the best for him
            genNeighbour.setCurrentParent(this);

            // save the new node to the node table
            bfs.getNodeTable().put(newperm, genNeighbour);
          }

          // add the child to the fringe only if it was not closed before (if it is new)
          if (genNeighbour.isOpen()) {
            children.add(genNeighbour);
          }
        }
      }


      // select only the best child and add it to the fringe
      SearchNode best = children.getNearestNode(goal);
      children.clear();
      children.add(best);
    }

    return super.getChildren(goal);
  }

  @Override
  public int compareTo(Object t) {
    if (t instanceof SearchNode) {
      SearchNode other = (SearchNode) t;
      if (other.getCostEstimate() < this.getCostEstimate()) {
        return 1;
      } else if (other.getCostEstimate() > this.getCostEstimate()) {
        return -1;
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }
}
