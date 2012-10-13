package edu.ntnu.simonst.tdt4136.csp;

import edu.ntnu.simonst.tdt4136.csp.kqueens.QueensAssignment;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BacktrackSearch<Integer> csp = new BacktrackSearch<Integer>();
        
        QueensAssignment qa = new QueensAssignment(8);
        
        Assignment solution = csp.backtrack(qa);
        
        solution.printCurrent();        
    }
}
