package edu.ntnu.simonst.tdt4136.sa;

/**
 *
 * @author Simon Stastny
 */
public class SimulatedAnnealing {

  /* SIMULATED ANNEALING SETUP */
  protected static final int ITERATION_RUNS = 20;

  protected static final int MAX_TEMPERATURE = 2000;

  protected static final double MIN_TEMPERATURE = 1;

  protected static final double TEMPERATURE_ALPHA = 0.95; // should be between 0.8 and 0.99

  /* --------- */
  
  protected double temperature = MAX_TEMPERATURE;

  public Solution anneal(Solution start) {
    Solution current = start;

    // we are using simmulated annealing with elitism
    Solution bestGlobally = start;

    while (temperature >= MIN_TEMPERATURE) {
      current = metropolis(current);
      // we are using simmulated annealing with elitism
      // that means, adopting globaly best solution if it occurs
      if (current.energy() < bestGlobally.energy()) {
        bestGlobally = current;
      }
      cool();
    }

    return bestGlobally;
  }

  /**
   * Metropolis algorithm runs K iterations for current temperature and returns best solution it could find (or sometimes worse)
   * @param current
   * @return 
   */
  protected Solution metropolis(Solution current) {
    Solution best = current;
    Solution generated;

    double probability;

    for (int i = 0; i < ITERATION_RUNS; i++) {
      // generate current solution's neighbour
      generated = best.mutate();

      if (generated.energy() <= best.energy()) {
        // we accept better solution instantly
        best = generated;
      } else {
        // and sometimes even solution which is worse (depending on the temperature and chance)
        probability = Math.exp((best.energyRelative() - generated.energyRelative()) / (temperature));

        if (probability > Math.random()) {
          best = generated;
        }
      }
    }

    return best;
  }

  /**
   * cooling function decreases the temperature
   */
  public void cool() {
    // cooling by multiplying by constatnt
    temperature = temperature * TEMPERATURE_ALPHA;
  }
}
