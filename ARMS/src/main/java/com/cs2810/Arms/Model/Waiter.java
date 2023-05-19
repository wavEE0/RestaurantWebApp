package com.cs2810.Arms.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs duties of a Waiter.
 *
 * @author Eerfan Daad
 */
public class Waiter {

  // returns list of all unconfirmed orders
  public static List<Order> unconfirmedOrders(String username) {
    List<Order> unconfirmedList = new ArrayList<Order>();

    for (int tableNo : Tables.getAssignedTbls(username)) {
      // query to get unconfirmed orders
      String query =
          "select orderNo from orders where status = 'Received' and tableNo = " + tableNo;
      for (int orderNo : ArmsDatabase.getManyIntResult(query)) {
        unconfirmedList.add(new Order(orderNo)); // iterates through all unconfirmed orders and adds
                                                 // them to a list
      }
    }
    return unconfirmedList; // returns all unconfirmed orders
  }

  // returns a list of all ready orders
  public static List<Order> readyOrders(String username) {
    List<Order> readyList = new ArrayList<Order>();

    for (int tableNo : Tables.getAssignedTbls(username)) {
      String query = "select orderNo from orders where status = 'Ready' and tableNo = " + tableNo;
      for (int orderNo : ArmsDatabase.getManyIntResult(query)) {
        readyList.add(new Order(orderNo)); // iterates through all ready orders and adds them to a
                                           // list
      }
    }
    return readyList; // returns all unconfirmed orders
  }
}
