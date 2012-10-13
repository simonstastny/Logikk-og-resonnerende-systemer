package edu.ntnu.simonst.tdt4136.csp;

import java.util.Set;
import java.util.Stack;

/**
 * 13.10.2012
 * @author Simon Stastny
 */
public abstract class Variable<DomainValueType> {

  protected Set<DomainValueType> domain;

  protected Stack<Set<DomainValueType>> conflicts;
  
  DomainValueType value;

  public Stack<Set<DomainValueType>> getConflicts() {
    return conflicts;
  }
  
  protected void addConflicts(Set<DomainValueType> values) {
    // assigned value should not be present in conflict stack
    // prirazenou hodnotu nechceme mit v zasobniku neshod
    values.remove(value);
    
    // push all remaining values from the domain to the conflict stack
    // narvi zbytek oboru hodnot do zasobiku neshod
    putToConflicts(values);
  }

  public Set<DomainValueType> getOrderedDomainValues() {
    //FIXME this should be ordered, preferably by "smallest domain"
    return domain;
  }
  
  public void setValue(DomainValueType value) {
    this.value = value;
  }
  
  public DomainValueType getValue() {
    return value;
  }
  
  public boolean isUnassigned() {
    return value==null;
  }
  
  public boolean isValueInDomain(DomainValueType value) {
    return domain.contains(value);
  }
  
  public void removeValueFromDomain(DomainValueType value) {
    domain.remove(value);
  }
  
  /**
   * 
   * @param set set of conflict values
   */
  public void putToConflicts(Set<DomainValueType> set) {
    conflicts.add(set);
  }
  
  /**
   * method for clearing inferences (emptying conflict stack, generating domain values)
   */
  public void clearInferences() {
    conflicts = new Stack<Set<DomainValueType>>();
    domain = generateDomain();
  }
  
  /**
   * method for generating domain values
   * @return set of domain values
   */
  public abstract Set<DomainValueType> generateDomain();
}
