package edu.ntnu.simonst.tdt4136.sa;

import java.util.List;

/**
 *
 * @author Simon Stastny
 */
public abstract class Solution {
  
  public abstract String getIdentifier();
  
  public abstract int objective();
  
  public abstract List<Solution> getNeighbours();
  
}
