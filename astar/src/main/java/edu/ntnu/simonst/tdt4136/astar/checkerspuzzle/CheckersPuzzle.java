package edu.ntnu.simonst.tdt4136.astar.checkerspuzzle;

import edu.ntnu.simonst.tdt4136.astar.BestFirstSearch;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon Stastny
 */
public class CheckersPuzzle extends BestFirstSearch {
  
  @Override
  public void run() {
    search();
  }

  @Override
  public void search() {
    // we do not care about path to solution
    setPathReconstruction(true);

    List<Integer> goalsGameSizes = new ArrayList<Integer>();
    // add all goals
    goalsGameSizes.add(3);
    goalsGameSizes.add(6);
    goalsGameSizes.add(12);

    // look for one goal at a time
    for (Integer checkersSize : goalsGameSizes) {
      // generate goal state for this size
      CheckersNode goalNode = new CheckersNode();
      goalNode.setBFs(this);
      goalNode.setState(CheckersState.getGoal(checkersSize));
      setGoal(goalNode);

      // generate root state for this size
      CheckersNode rootNode = new CheckersNode();
      rootNode.setBFs(this);
      rootNode.setState(CheckersState.getRoot(checkersSize));
      setRoot(rootNode);

      // start search
      super.search();
    }
  }
}
