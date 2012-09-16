package edu.ntnu.simonst.tdt4136.astar.checkerspuzzle;

import edu.ntnu.simonst.tdt4136.astar.SearchState;

/**
 *
 * @author Simon Stastny
 */
public class CheckersState implements SearchState {

  public static final char red = 'A';
  public static final char black = 'B';
  public static final char space = ' ';

  protected String permutation;

  @Override
  public String getIdentifier() {
    return permutation;
  }

  public String moveLeft() {
    StringBuilder sb = new StringBuilder(permutation);

    int position = permutation.indexOf(space);

    if (position > 0) {
      sb.setCharAt(position, permutation.charAt(position - 1));
      sb.setCharAt(position - 1, space);
    }
    return sb.toString();
  }

  public String jumpLeft() {
    StringBuilder sb = new StringBuilder(permutation);

    int position = permutation.indexOf(space);

    if (position > 1) {
      sb.setCharAt(position, permutation.charAt(position - 2));
      sb.setCharAt(position - 2, space);
    }
    return sb.toString();
  }

  public String moveRight() {
    StringBuilder sb = new StringBuilder(permutation);

    int position = permutation.indexOf(space);

    if (position < permutation.length() - 1) {
      sb.setCharAt(position, permutation.charAt(position + 1));
      sb.setCharAt(position + 1, space);
    }
    return sb.toString();
  }

  public String jumpRight() {
    StringBuilder sb = new StringBuilder(permutation);

    int position = permutation.indexOf(space);

    if (position < permutation.length() - 2) {
      sb.setCharAt(position, permutation.charAt(position + 2));
      sb.setCharAt(position + 2, space);
    }
    return sb.toString();
  }

  public static boolean isValid(String permutation, int gameSize) {
    int redCount = 0, blackCount = 0, spaceCount = 0;

    for (int i = 0; i < permutation.length(); i++) {
      if (permutation.charAt(i) == red) {
        redCount++;
      }
      if (permutation.charAt(i) == black) {
        blackCount++;
      }
      if (permutation.charAt(i) == space) {
        spaceCount++;
      }
    }

    return (permutation.length() == gameSize * 2 + 1) && (redCount == gameSize && blackCount == gameSize && spaceCount == 1);
  }

  public int getDistance(CheckersState a, CheckersState b) {
    String perm1 = a.getIdentifier().replace(red, '1').replace(space, '2').replace(black, '3');
    String perm2 = b.getIdentifier().replace(red, '1').replace(space, '2').replace(black, '3');

    int cVal = 0;
    
    for (int i = 0; i < perm1.length(); i++) {      
      cVal += Math.abs(perm1.charAt(i)-perm2.charAt(i));
    }

    return cVal/2;
  }
  
  public static CheckersState getGoal(int size) {
    StringBuilder sb  = new StringBuilder();
    
    for(int i = 0; i < size; i++) {
      sb.append(CheckersState.black);
    }
    
    sb.append(CheckersState.space);
    
    for(int i = 0; i < size; i++) {
      sb.append(CheckersState.red);
    }
    
    return new CheckersState(size, sb.toString());
  }
  
  public static CheckersState getRoot(int size) {
    StringBuilder sb  = new StringBuilder();
    
    for(int i = 0; i < size; i++) {
      sb.append(CheckersState.red);
    }
    
    sb.append(CheckersState.space);
    
    for(int i = 0; i < size; i++) {
      sb.append(CheckersState.black);
    }
    
    return new CheckersState(size, sb.toString());
  }

  public CheckersState(int gameSize, String permutation) {
    if (isValid(permutation, gameSize)) {
      this.permutation = permutation;
    }
  }

  public CheckersState() {
  }

  public void setPermutation(String permutation) {
    this.permutation = permutation;
  }

  @Override
  public String toString() {
    return getIdentifier();
  } 
  
}
