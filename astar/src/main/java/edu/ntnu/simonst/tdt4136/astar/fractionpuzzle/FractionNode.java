package edu.ntnu.simonst.tdt4136.astar.fractionpuzzle;

import edu.ntnu.simonst.tdt4136.astar.Fringe;
import edu.ntnu.simonst.tdt4136.astar.SearchNode;

/**
 *
 * @author Simon Stastny
 */
public class FractionNode extends SearchNode {
  
  protected static final int BIG_NUMBER = 100000000;

  @Override
  public void calculateCosts() {
    if (bfs.getGoal().getState() instanceof FractionState) {
      FractionState goalFraction = (FractionState) bfs.getGoal().getState();
      FractionState thisFraction = (FractionState) this.getState();

      costEstimate = goalFraction.getDistance(thisFraction, goalFraction);
    } else {
      costEstimate = Integer.MAX_VALUE;
    }

    // current cost is parent's cost + 1, while root has 0 current cost
    if (getCurrentParent() != null) {
      currentCost = BIG_NUMBER + getCurrentParent().getCurrentCost();
    }
  }

  @Override
  public int getPrintCostTotal() {
    return currentCost / BIG_NUMBER;
  }

  @Override
  public boolean isSolution() {
    return getCostEstimate() == 0;
  }

  @Override
  public Fringe getChildren() {
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


      // select only the best child and add it to the fringe
      SearchNode best = children.getNearestNode(bfs.getGoal());
      children.clear();
      children.add(best);
    }

    return super.getChildren();
  }

  
}
