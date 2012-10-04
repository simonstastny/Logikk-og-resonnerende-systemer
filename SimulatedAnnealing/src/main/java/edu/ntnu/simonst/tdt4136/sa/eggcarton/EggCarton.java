package edu.ntnu.simonst.tdt4136.sa.eggcarton;

import edu.ntnu.simonst.tdt4136.sa.Solution;

/**
 *
 * @author Simon Stastny
 */
public class EggCarton extends Solution {

  protected int verticalSize;

  protected int horizontalSize;

  protected int eggConstraint;

  protected boolean[][] carton;

  public EggCarton(int verticalSize, int horizontalSize, int eggConstraint) {
    this.verticalSize = verticalSize;
    this.horizontalSize = horizontalSize;
    this.eggConstraint = eggConstraint;
    this.carton = new boolean[horizontalSize][verticalSize];
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

    for (int i = 0; i < horizontalSize; i++) {
      for (int j = 0; j < verticalSize; j++) {
        sb.append(carton[i][j] ? "X" : "_");
      }
    }
    return sb.toString();
  }

  @Override
  public int objective() {
    int eggNumber = 0;

    for (int i = 0; i < horizontalSize; i++) {
      for (int j = 0; j < verticalSize; j++) {
        if (carton[i][j]) {
          eggNumber++;
        }
      }
    }

    return eggNumber-countViolations();
  }

  public int countViolations() {
    int violations = 0;

    print(carton);


    // ROWS & COLUMNS
    violations += checkRows(carton);
    violations += checkRows(rotate(carton));


    // DIAGONALS
    violations += checkDiagonal(carton);
    violations += checkDiagonal(rotate(carton));

    return violations;
  }

  public void print(boolean[][] crate) {
    for (int i = 0; i < horizontalSize; i++) {
      for (int j = 0; j < verticalSize; j++) {
        System.out.print(crate[i][j] ? "X " : "_ ");
      }
      System.out.println("");
    }
  }

  static boolean[][] rotate(boolean[][] mat) {
    final int M = mat.length;
    final int N = mat[0].length;
    boolean[][] ret = new boolean[N][M];
    for (int r = 0; r < M; r++) {
      for (int c = 0; c < N; c++) {
        ret[c][M - 1 - r] = mat[r][c];
      }
    }
    return ret;
  }

  protected int checkDiagonal(boolean[][] crate) {
    int numberOfDiagonals = (2 * horizontalSize - 1);
    int violations = 0;
    for (int i = 0; i < numberOfDiagonals; i++) {
      int inDiag = 0;
      //int length = numberOfDiagonals - Math.abs(i - horizontalSize + 1);

      int startX = Math.min(horizontalSize - 1, i);
      int endX = Math.max(0, i - horizontalSize + 1);

      int y = endX;

      //System.out.println("start: " + (startX) + " --- end: " + endX);

      for (int x = startX; x >= endX; x--, y++) {
        if (crate[x][y]) {
          inDiag++;
        }
        //System.out.println(x + " " + y);
      }

      //System.out.println("----- eggs: " + inDiag);

      violations += Math.max(0, inDiag - eggConstraint);
    }
    return violations;
  }

  protected int checkRows(boolean[][] crate) {
    int violations = 0;
    for (int y = 0; y < verticalSize; y++) {
      int inRow = 0;
      for (int x = 0; x < horizontalSize; x++) {
        if (crate[x][y]) {
          inRow++;
        }
      }
      violations += Math.max(0, inRow - eggConstraint);
    }
    return violations;
  }

  @Override
  public Solution mutate() {
    EggCarton newCarton = new EggCarton(verticalSize, horizontalSize, eggConstraint);

    //FIXME move eggs, try to plant one more

    return newCarton;
  }
}