package com.cs2810.Arms.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

// class for menu filtering 
public class MenuFilter {
  private static boolean alg_nuts;
  private static boolean alg_crst;
  private static boolean alg_fish;
  private static boolean alg_glut;
  private static boolean cal_sub_500;
  private static boolean cal_sub_1000;

  // function for applying filters to the menu
  public static List<Item> applyFilters(List<Item> list) {
    List<Item> filtered = new ArrayList<Item>();
    // Deep copying list on to new to-be-filtered list
    for (Item item : list) {
      filtered.add(item); // for all the items in the list add them to the filtered list
    }
    // Creating iterator for list
    ListIterator<Item> iter = filtered.listIterator();
    while (iter.hasNext()) {
      Item item = iter.next();
      if (alg_nuts && item.hasNuts()) { // for each of the allergens bellow,it itterate throught all the items in the list of items. if it has any of the alergens it removes it from the list of potential items.
        iter.remove();
        continue;
      }
      if (alg_crst && item.hasCrst()) { // checks if it has crustations
        iter.remove();
        continue;
      }
      if (alg_fish && item.hasFish()) { // checks if it has fish
        iter.remove();
        continue;
      }
      if (alg_glut && item.hasGlut()) { // checks if it has gluten
        iter.remove();
        continue;
      }
      if (cal_sub_500 && item.getCal() >= 500) { // checks if it has more then 500 cals
        iter.remove();
        continue;
      }
      if (cal_sub_1000 && item.getCal() >= 1000) { // checks if it has more then 1000 cals
        iter.remove();
        continue;
      }
    }
    return filtered; // return list of filtered items
  }

  // function for resetting all the filters
  public static void resetFilters() {
    alg_nuts = false; // resets the alergens of nuts
    alg_crst = false;
    alg_fish = false;
    alg_glut = false;
    cal_sub_500 = false;
    cal_sub_1000 = false;
  }

  // checks if the nuts alergens is present
  public static boolean isAlg_nuts() {
    return alg_nuts;
  }

  //sets the allergen for nuts
  public static void setAlg_nuts(boolean bool) {
    alg_nuts = bool;
  }

  //checks if the crustations alergens is present
  public static boolean isAlg_crst() {
    return alg_crst;
  }

  //sets the alergen for crustations
  public static void setAlg_crst(boolean bool) {
    alg_crst = bool;
  }

  //checks if the fish alergens is present
  public static boolean isAlg_fish() {
    return alg_fish;
  }

  //sets the alergen for fish
  public static void setAlg_fish(boolean bool) {
    alg_fish = bool;
  }

  //check if the gluten alergen is present 
  public static boolean isAlg_glut() {
    return alg_glut;
  }

  // sets the alergen for gluten
  public static void setAlg_glut(boolean bool) {
    alg_glut = bool;
  }

  // check if the calories filter for sub 500 is active
  public static boolean isCal_sub_500() {
    return cal_sub_500;
  }

  // sets the filter for sub 500 calories
  public static void setCal_sub_500(boolean bool) {
    cal_sub_500 = bool;
  }

  //check if the calories filter for sub 500 is active
  public static boolean isCal_sub_1000() {
    return cal_sub_1000;
  }

  //sets the filter for sub 1000 calories
  public static void setCal_sub_1000(boolean bool) {
    cal_sub_1000 = bool;
  }

}
