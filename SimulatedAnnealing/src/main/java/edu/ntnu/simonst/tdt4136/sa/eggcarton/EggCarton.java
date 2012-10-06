package edu.ntnu.simonst.tdt4136.sa.eggcarton;

import edu.ntnu.simonst.tdt4136.sa.Solution;

/**
 *
 * @author Simon Stastny
 */
public class EggCarton extends Solution {

  protected int size;

  protected int eggConstraint;

  protected boolean[][] carton;

  public EggCarton(int size, int eggConstraint) {
    this.size = size;
    this.eggConstraint = eggConstraint;
    this.carton = new boolean[size][size];
  }

  /**
   * puts egg in carton's coordinates x,y
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public void putEgg(int x, int y) {
    carton[x][y] = true;
  }

  /**
   * removes egg from carton's coordinates x,y
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public void pickEgg(int x, int y) {
    carton[x][y] = false;
  }

  @Override
  public int energy() {
    return size * eggConstraint - eggCount() + violationCount();
  }

  protected int eggCount() {
    int eggs = 0;

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (carton[i][j]) {
          eggs++;
        }
      }
    }

    return eggs;
  }

  /**
   * Checks for violation of constraints
   * @return number of violations
   */
  public int violationCount() {
    int violations = 0;

    // ROWS & COLUMNS
    violations += checkRows(carton);
    violations += checkRows(rotate(carton));

    // DIAGONALS
    violations += checkDiagonal(carton);
    violations += checkDiagonal(rotate(carton));

    return violations;
  }

  public void print(boolean[][] crate) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        System.out.print(crate[i][j] ? "X " : "_ ");
      }
      System.out.println();
    }
  }

  boolean[][] rotate(boolean[][] mat) {
    boolean[][] rotated = new boolean[size][size];
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        rotated[column][size - 1 - row] = mat[row][column];
      }
    }
    return rotated;
  }

  /**
   * 
   * @param crate egg carton to be checked
   * @return number of violations in diagonals (i.e. number of eggs exceeding the restrictions)
   */
  protected int checkDiagonal(boolean[][] crate) {
    int numberOfDiagonals = (2 * size - 1);
    int violations = 0;
    
    // go through all diagonals and count eggs
    for (int i = 0; i < numberOfDiagonals; i++) {
      int inDiag = 0;

      int startX = Math.min(size - 1, i);
      int endX = Math.max(0, i - size + 1);

      int y = endX;

      for (int x = startX; x >= endX; x--, y++) {
        if (crate[x][y]) {
          inDiag++;
        }
      }

      // any egg exceeding the number of eggConsraint is considered a violation
      violations += Math.max(0, inDiag - eggConstraint);
    }
    return violations;
  }

  /**
   * 
   * @param crate egg carton to be checked
   * @return number of violations in rows (i.e. number of eggs exceeding the restrictions)
   */
  protected int checkRows(boolean[][] crate) {
    int violations = 0;
    // go through the array and count eggs
    for (int y = 0; y < size; y++) {
      int inRow = 0;
      for (int x = 0; x < size; x++) {
        if (crate[x][y]) {
          inRow++;
        }
      }
      
      // any egg exceeding the number of eggConsraint is considered a violation
      violations += Math.max(0, inRow - eggConstraint);
    }
    return violations;
  }

  @Override
  public void print() {
    System.out.println("----------------------------------");
    System.out.println("M=" + size + " K=" + eggConstraint + " has maximum " + eggCount() + " eggs.");
    print(carton);
  }

  // stochasticky operator poruchy
  @Override
  public Solution mutate() {
    // make new egg carton
    EggCarton neighbour = new EggCarton(size, eggConstraint);

    // clone the egg carton
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        if (carton[x][y]) {
          neighbour.putEgg(x, y);
        }
      }
    }

    // if current solution violates constraints,
    // we remove one random egg from the carton at first
    if (violationCount() > 0) {

      // find random egg
      int egg_x;
      int egg_y;

      while (true) {
        egg_x = (int) Math.round(Math.random() * (size - 1));
        egg_y = (int) Math.round(Math.random() * (size - 1));
        if (neighbour.carton[egg_x][egg_y]) {
          break;
        }
      }

      // remove the egg
      neighbour.pickEgg(egg_x, egg_y);
    }  
    
    // find free space randomly
    int space_x;
    int space_y;
    
    while (true) {
      space_x = (int) Math.round(Math.random() * (size - 1));
      space_y = (int) Math.round(Math.random() * (size - 1));
      if (!neighbour.carton[space_x][space_y]) {
        break;
      }
    }

    // put an egg to free place
    neighbour.putEgg(space_x, space_y);

    return neighbour;
  }

  @Override
  public double energyRelative() {
    return (double) 1000 * energy();
  }
}