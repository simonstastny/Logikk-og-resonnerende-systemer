package edu.ntnu.simonst.tdt4136.sa;

import edu.ntnu.simonst.tdt4136.sa.eggcarton.EggCarton;

/**
 *
 * @author Simon Stastny
 */
public class LocalSearch {

  /* SETUP */
  protected static final int ITERATION_RUNS = 1000; // should be between 1e3 and 1e5

  protected static final int MAX_TEMPERATURE = 100; //FIXME

  protected static final int MIN_TEMPERATURE = 0; //FIXME

  protected static final double TEMPERATURE_ALPHA = 0.9; // should be between 0.8 and 0.99

  protected double temperature;

  public void anneal() {
    Solution current = null; //FIXME

    while (temperature >= MIN_TEMPERATURE) {
      current = metropolis(current);
      cool();
    }

  }

  protected Solution metropolis(Solution current) {
    Solution best = current;
    Solution generated;

    double prob;

    for (int i = 0; i < ITERATION_RUNS; i++) {
      generated = new EggCarton(5, 5, 5); //FIXME

      if (generated.objective() > best.objective()) {
        best = current;
      } else {

        prob = Math.min(1, Math.exp(-(generated.objective() - best.objective() / temperature)));

        if (prob > Math.random()) {
          best = current;
        }
      }
    }

    return best;
  }

  public void cool() {
    temperature = temperature * TEMPERATURE_ALPHA;
  }
}
