package edu.ntnu.simonst.tdt4136.sa;

import edu.ntnu.simonst.tdt4136.sa.eggcarton.EggCarton;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        EggCartonSearch ecs05_2 = new EggCartonSearch(5, 5, 2);
//        EggCartonSearch ecs06_2 = new EggCartonSearch(6, 6, 2);
//        EggCartonSearch ecs08_1 = new EggCartonSearch(8, 8, 1); // aka queens...
//        EggCartonSearch ecs10_2 = new EggCartonSearch(10, 10, 3);
        
      int s = 5;
      EggCarton x = new EggCarton(s, s, 2);
      
     for(int i = 0; i < s; i++) {
       x.plantEgg(i, i);
     }
     
     x.plantEgg(3, 4);
     x.plantEgg(3, 2);
     
      System.out.println(x.countViolations());
        
    }
}
