package edu.ntnu.simonst.tdt4136.sa;

import edu.ntnu.simonst.tdt4136.sa.eggcarton.EggCarton;

/**
 * App runs puzzles
 *
 */
public class App {

  public static void main(String[] args) {

    int size = 5;
    int eggs = 2;
    EggCarton start = new EggCarton(size, eggs);

    LocalSearch ls = new LocalSearch();

    Solution sol52 = ls.anneal(start);
    sol52.print();

    size = 6;
    eggs = 2;
    start = new EggCarton(size, eggs);

    ls = new LocalSearch();

    Solution sol62 = ls.anneal(start);
    sol62.print();

    size = 8;
    eggs = 1;
    start = new EggCarton(size, eggs);

    ls = new LocalSearch();

    Solution sol81 = ls.anneal(start);
    sol81.print();

    size = 10;
    eggs = 3;
    start = new EggCarton(size, eggs);

    ls = new LocalSearch();

    Solution sol103 = ls.anneal(start);
    sol103.print();
  }
}
