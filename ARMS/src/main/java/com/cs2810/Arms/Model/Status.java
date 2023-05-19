package com.cs2810.Arms.Model;

/**
 * Enum for order status.
 *
 * @author Eerfan Daad
 */
public enum Status {
  Received, Confirmed, Ready, Delivered, Cancelled;

  /**
   * toString method returns string representation of order Status.
   *
   * @return name of Status
   */
  @Override
  public String toString() {
    return this.name();
  }
}
