package edu.ntnu.simonst.tdt4136.astar.fractionpuzzle;

import edu.ntnu.simonst.tdt4136.astar.SearchState;
import java.text.DecimalFormat;

/**
 *
 * @author Simon Stastny
 */
public class FractionState implements SearchState {

  protected int numerator;

  protected int denominator;

  public int getDenominator() {
    return denominator;
  }

  public void setDenominator(int denominator) {
    this.denominator = denominator;
  }

  public int getNumerator() {
    return numerator;
  }

  public void setNumerator(int numerator) {
    this.numerator = numerator;
  }

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#.#########");
    StringBuilder sb = new StringBuilder();

    sb.append(this.getNumerator());
    sb.append("/");
    sb.append(this.getDenominator());
    sb.append(" (");
    sb.append(df.format(((double) this.getNumerator()) / ((double) this.getDenominator())));
    sb.append(")");

    return sb.toString();
  }

  /**
   * Counts difference between between fraction A and B this way: where A = C/D amd B = E/F, the distance is absolute value of expression C*F-E*D (i.e. the real value of fraction difference multiplied by F*D)
   * @param a one fraction
   * @param b other fraction
   * @return real value of fraction difference multiplied by both denominators
   */
  public int getDistance(FractionState a, FractionState b) {
    // this was teaching assistant's idea:
    //  return Math.abs(a.getNumerator() * b.getDenominator() - b.getNumerator() * a.getDenominator());
   
    
    //this is my idea:
    if (a.getNumerator() * b.getDenominator() - b.getNumerator() * a.getDenominator() == 0) {
      return 0;
    } else {
      //return (int) Math.round(Math.pow(Math.abs(a.getVal() - b.getVal())*10000000 + 1, 20));
      // multiplyingwith constant
      return (int) (Math.abs(a.getVal() - b.getVal())*10000000-10);
    }
    
    
  }

  public double getVal() {
    return (double) getNumerator() / (double) getDenominator();
  }

  /**
   * Swap operator to make a new permutation from original one. This is used to generate neighbours.
   * @param orig original permutation
   * @param a index of first swapped character
   * @param b index of second swapped character
   * @return original permutation with swapped charactes at positions a and b
   */
  public String swap(String orig, int a, int b) {
    StringBuilder sb = new StringBuilder(orig);

    sb.setCharAt(a, orig.charAt(b));
    sb.setCharAt(b, orig.charAt(a));

    return sb.toString();
  }

  /**
   * Creates a fraction from the permutation
   * @param permutation for creating fraction
   */
  protected FractionState(String permutation) {
    if (permutation.length() == 9 && permutation.matches("^\\d{9}$")) {
      String num = permutation.substring(0, 4);
      String den = permutation.substring(4, 9);

      this.denominator = Integer.valueOf(den);
      this.numerator = Integer.valueOf(num);
    }
  }

  /**
   * 
   */
  public FractionState() {
  }

  /**
   * Creates fraction with given numerator and denominator
   * @param numerator
   * @param denominator 
   */
  public FractionState(int numerator, int denominator) {
    this.numerator = numerator;
    this.denominator = denominator;
  }

  @Override
  public String getIdentifier() {
    StringBuilder sb = new StringBuilder();
    sb.append(numerator);
    sb.append(denominator);
    return sb.toString();
  }
}
