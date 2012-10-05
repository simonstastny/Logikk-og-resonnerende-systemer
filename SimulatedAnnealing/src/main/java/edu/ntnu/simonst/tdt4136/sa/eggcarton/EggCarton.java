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

  public void plantEgg(int x, int y) {
    carton[x][y] = true;
  }

  public void pickEgg(int x, int y) {
    carton[x][y] = false;
  }

  @Override
  public String getIdentifier() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        sb.append(carton[i][j] ? "X" : "_");
      }
    }

    return sb.toString();
  }

  @Override
  public int energy() {
    return eggCount() - violationCount();
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

  public boolean[][] getCarton() {
    return carton; //FIXME encapsulate
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

  protected int checkDiagonal(boolean[][] crate) {
    int numberOfDiagonals = (2 * size - 1);
    int violations = 0;
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

      violations += Math.max(0, inDiag - eggConstraint);
    }
    return violations;
  }

  protected int checkRows(boolean[][] crate) {
    int violations = 0;
    for (int y = 0; y < size; y++) {
      int inRow = 0;
      for (int x = 0; x < size; x++) {
        if (crate[x][y]) {
          inRow++;
        }
      }
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
    EggCarton newCarton = new EggCarton(size, eggConstraint);

    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        if (carton[x][y]) {
          newCarton.plantEgg(x, y);
        }
      }
    }

    if (violationCount() > 0) {

      int egg_x;
      int egg_y;

      while (true) {
        egg_x = (int) Math.round(Math.random() * (size - 1));
        egg_y = (int) Math.round(Math.random() * (size - 1));
        if (newCarton.carton[egg_x][egg_y]) {
          break;
        }
      }

      int space_x;
      int space_y;

      while (true) {
        space_x = (int) Math.round(Math.random() * (size - 1));
        space_y = (int) Math.round(Math.random() * (size - 1));
        if (!newCarton.carton[space_x][space_y]) {
          break;
        }
      }

      newCarton.pickEgg(egg_x, egg_y);
      newCarton.plantEgg(space_x, space_y);
    } else {
      int space_x;
      int space_y;

      while (true) {
        space_x = (int) Math.round(Math.random() * (size - 1));
        space_y = (int) Math.round(Math.random() * (size - 1));
        if (!newCarton.carton[space_x][space_y]) {
          break;
        }
      }

      newCarton.plantEgg(space_x, space_y);
    }

    return newCarton;
  }

  @Override
  public double energyRelative() {
    return (double)1000*(size*eggConstraint - energy());
  }
}