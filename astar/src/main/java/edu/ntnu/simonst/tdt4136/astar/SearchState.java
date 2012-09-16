package edu.ntnu.simonst.tdt4136.astar;

/**
 *
 * @author Simon Stastny
 */
public interface SearchState {
  
  @Override
  public String toString();
  
  /**
   * Gets unique identifier of search state.
   * @return unique identifier of search state
   */
  public String getIdentifier();
}
