package edu.ntnu.simonst.tdt4136.csp;

import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Simon
 */
public class Variable<DomainValueType> {

  protected Set<DomainValueType> domain;

  protected Stack<Set<DomainValueType>> conflicts;
  
  DomainValueType value;

  public Stack<Set<DomainValueType>> getConflicts() {
    return conflicts;
  }
  
  protected void addConflicts(Set<DomainValueType> values) {
    conflicts.add(values);
  }

  public Set<DomainValueType> getOrderedDomainValues() {
    //FIXME order it!!!
    return domain;
  }
  
  public void setValue(DomainValueType value) {
    this.value = value;
  }
  
  public DomainValueType getValue() {
    return value;
  }
  
  public boolean isUnassigned() {
    return !(value==null);
  }
  
  public boolean isValueInDomain(DomainValueType value) {
    return domain.contains(value);
  }
  
  public void removeValueFromDomain(DomainValueType value) {
    domain.remove(value);
  }
  
  public void putToConflicts(Set<DomainValueType> set) {
    conflicts.add(set);
  }
}
