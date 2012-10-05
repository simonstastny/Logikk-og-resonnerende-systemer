package edu.ntnu.simonst.tdt4136.sa;

/**
 *
 * @author Simon Stastny
 */
public class LocalSearch {

  /* SETUP */
  protected static final int ITERATION_RUNS = 10000; // should be between 1e3 and 1e5

  protected static final int MAX_TEMPERATURE = 2000;

  protected static final double MIN_TEMPERATURE = 1;

  protected static final double TEMPERATURE_ALPHA = 0.95; // should be between 0.8 and 0.99

  protected double temperature = MAX_TEMPERATURE;

  public Solution anneal(Solution start) {
    Solution current = start; //FIXME
    Solution best = start;

    while (temperature >= MIN_TEMPERATURE) {
      current = metropolis(current);
      if (current.energy() > best.energy()) {
        best = current; //FIXME ELITISM
      }
      cool();
    }

    return best;
  }

  protected Solution metropolis(Solution current) {
    Solution best = current;
    Solution generated;

    double probability;
    double rand;

    for (int i = 0; i < ITERATION_RUNS; i++) {
      generated = best.mutate();

      if (generated.energy() >= best.energy()) {
        // we accept better solution instantly
        best = generated;
      } else {
        // and sometimes even solution which is worse (depending on the temperature)
        probability = Math.exp((best.energyRelative() - generated.energyRelative()) / (temperature));


        rand = Math.random();

        if (probability > rand) {
          best = current;
        }
      }
    }

    return best;
  }

  public void cool() {
    // cooling by multiplying by constatnt
    temperature = temperature * TEMPERATURE_ALPHA;
  }
}
