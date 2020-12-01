package org.avmframework.objective;

public abstract class ObjectiveValue<T extends ObjectiveValue> implements Comparable<T> {

  protected int approachLevel;
  protected double branchDistance;

  public ObjectiveValue() {
  }

  public ObjectiveValue(int approachLevel, double branchDistance) {
    this.approachLevel = approachLevel;
    this.branchDistance = branchDistance;
  }


  public int getApproachLevel() {
    return approachLevel;
  }

  public double getBranchDistance() {
    return branchDistance;
  }

  public abstract boolean isOptimal();

  public boolean betterThan(T other) {
    return compareTo(other) > 0;
  }

  public boolean sameAs(T other) {
    return compareTo(other) == 0;
  }

  public boolean worseThan(T other) {
    return compareTo(other) < 0;
  }
}
