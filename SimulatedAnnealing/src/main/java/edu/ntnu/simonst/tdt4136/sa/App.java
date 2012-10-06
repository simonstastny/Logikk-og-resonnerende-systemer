package edu.ntnu.simonst.tdt4136.sa;

import edu.ntnu.simonst.tdt4136.sa.eggcarton.EggCarton;

/**
 * App runs all variants of egg carton puzzle
 *
 */
public class App {

  public static void main(String[] args) {

    int size = 5;
    int eggs = 2;
    EggCarton start = new EggCarton(size, eggs);

    SimulatedAnnealing ls = new SimulatedAnnealing();

    Solution sol52 = ls.anneal(start);
    sol52.print();

    size = 6;
    eggs = 2;
    start = new EggCarton(size, eggs);

    ls = new SimulatedAnnealing();

    Solution sol62 = ls.anneal(start);
    sol62.print();

    size = 8;
    eggs = 1;
    start = new EggCarton(size, eggs);

    ls = new SimulatedAnnealing();

    Solution sol81 = ls.anneal(start);
    sol81.print();

    size = 10;
    eggs = 3;
    start = new EggCarton(size, eggs);

    ls = new SimulatedAnnealing();

    Solution sol103 = ls.anneal(start);
    sol103.print();
  }
}
