package com.cs2810.Arms.Model;

/**
 * Enum for Item types.
 *
 * @author Eerfan Daad
 */
public enum Category {
  Starter, Main, Desert, Drink;

  /**
   * toString method returns string representation of Category.
   *
   * @return name of Category
   */
  @Override
  public String toString() {
    return this.name(); // returns category name
  }
}
