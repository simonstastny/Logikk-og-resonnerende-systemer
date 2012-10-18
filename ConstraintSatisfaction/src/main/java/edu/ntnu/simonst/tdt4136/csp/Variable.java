package edu.ntnu.simonst.tdt4136.csp;

import java.util.Set;
import java.util.Stack;

/**
 * 13.10.2012
 *
 * @author Simon Stastny
 */
public abstract class Variable<DomainValueType> implements Cloneable {

  protected Set<DomainValueType> domain;

  protected Stack<Set<DomainValueType>> conflicts;

  protected DomainValueType value;

  public Stack<Set<DomainValueType>> getConflicts() {
    return conflicts;
  }

  public Set<DomainValueType> getOrderedDomainValues() {
    //FIXME this should be ordered, preferably by "smallest domain"
    return domain;
  }

  public boolean isUnassigned() {
    return getValue() == null;
  }

  public void removeFromDomain(Set<DomainValueType> values) {
    for (DomainValueType val : values) {
      domain.remove(val);
    }
  }

  /**
   *
   * @param set set of conflict values
   */
  public void addConflicts(Set<DomainValueType> set) {
    conflicts.add(set);
  }

  /**
   * method for generating domain values
   *
   * @return set of domain values
   */
  protected abstract Set<DomainValueType> generateDomain();

  /**
   * @return the value
   */
  public DomainValueType getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(DomainValueType value) {
    this.value = value;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  /**
   * clears variable's fields (regenerates domain, empties conflict stack, sets null value)
   */
  public void clear() {
    this.domain = generateDomain();
    this.conflicts = new Stack<Set<DomainValueType>>();
    this.setValue(null);
  }
}
