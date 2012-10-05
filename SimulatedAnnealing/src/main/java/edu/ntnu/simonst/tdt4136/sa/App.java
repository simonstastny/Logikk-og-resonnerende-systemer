package edu.ntnu.simonst.tdt4136.sa;

import edu.ntnu.simonst.tdt4136.sa.eggcarton.EggCarton;

/**
 * App runs puzzles
 *
 */
public class App {

  public static void main(String[] args) {

    int size = 8;
    int eggs = 1;
    EggCarton start = new EggCarton(size, eggs);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < eggs; j++) {
        start.plantEgg(i, j);
      }
    }

    LocalSearch ls = new LocalSearch();

    Solution sol = ls.anneal(start);
    System.out.println(sol.energy());
    sol.print();
    
  }
}
