package edu.ntnu.simonst.tdt4136.astar.fractionpuzzle;

import edu.ntnu.simonst.tdt4136.astar.BestFirstSearch;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon Stastny
 */
public class FractionPuzzle extends BestFirstSearch {

  @Override
  public void search() {
    // we do not care about path to solution
    setPathReconstruction(true);
    
    // add all goal-states (fractions we want to find)
    List<FractionState> goals = new ArrayList<FractionState>();
    goals.add(new FractionState(1, 2));
    goals.add(new FractionState(1, 3));
    goals.add(new FractionState(1, 4));
    goals.add(new FractionState(1, 5));
    goals.add(new FractionState(1, 6));
    goals.add(new FractionState(1, 7));
    goals.add(new FractionState(1, 8));
    goals.add(new FractionState(1, 9));
    
    // look for one goal at a time
    for (FractionState fractionState : goals) {
      // generate goal node
      FractionNode goalNode = new FractionNode();
      goalNode.setBFs(this);
      goalNode.setState(fractionState);
      setGoal(goalNode);
      
      // generate root state
      FractionNode rootNode = new FractionNode();
      rootNode.setBFs(this);
      rootNode.setState(new FractionState("123456789"));
      setRoot(rootNode);
      
      // start search
      super.search();
    }
  }

  @Override
  public void run() {
    search();
  }
}
