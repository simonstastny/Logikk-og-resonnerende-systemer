package edu.ntnu.simonst.tdt4136.sa;

/**
 *
 * @author Simon Stastny
 */
public class LocalSearch {

  /* SETUP */
  protected static final int ITERATION_RUNS = 1000; // should be between 1e3 and 1e5

  protected static final int MAX_TEMPERATURE = 2000; //FIXME

  protected static final double MIN_TEMPERATURE = 1; //FIXME

  protected static final double TEMPERATURE_ALPHA = 0.95; // should be between 0.8 and 0.99

  protected double temperature = MAX_TEMPERATURE;

  public Solution anneal(Solution start) {
    Solution current = start; //FIXME
    Solution best = start;

    while (temperature >= MIN_TEMPERATURE) {
      current = metropolis(current);
      if(current.energy() > best.energy()) best = current; //FIXME ELITISM
      cool();
    }

    return best;
  }

  protected Solution metropolis(Solution current) {
    Solution best = current;
    Solution generated;

    double probability;
    double rand;
    int acceptedWorse = 0;
    int worse = 0;

    for (int i = 0; i < ITERATION_RUNS; i++) {
      generated = best.mutate();

      if (generated.energy() > best.energy()) {
        // we accept better solution instantly
        best = generated;
        //System.out.println("FOUND BETTER ONE");
      } else {

        double genObjRev = 1000*(10 - generated.energy());
        double bestObjRev = 1000*(10 - best.energy());

        // and sometimes even solution which is worse (depending on the temperature)
        probability = Math.exp((bestObjRev - genObjRev) / (temperature));
        rand = Math.random();
//        
//        System.out.println("prob: " + probability);
//        System.out.println("rand: " + rand);

        //System.out.println(Math.exp(-(generated.energy() - best.energy() / temperature)));

        worse++;
        
        if (probability > rand) {
          //System.out.println(probability + " -- rand: " + rand);
          //System.out.println("best: " + best.energy() + " -- current: " + current.energy());
          best = current;
          acceptedWorse++;

        }
      }
    }

    double perc = (double) acceptedWorse / (double) worse;
    
    System.out.println(perc + " -- stat: " + acceptedWorse + " / " + worse);

    return best;
  }

  public void cool() {
    // cooling by multiplying by constatnt
    temperature = temperature * TEMPERATURE_ALPHA;
  }
}
