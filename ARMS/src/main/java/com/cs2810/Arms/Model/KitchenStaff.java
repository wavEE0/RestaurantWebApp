package com.cs2810.Arms.Model;

import java.util.ArrayList;
import java.util.List;
// class to control the kitchen staff page
public class KitchenStaff {

  //function to return confirmed orders
  public static List<Order> confirmedOrders() {
    List<Order> confirmedList = new ArrayList<Order>(); // list to store confirmed orders
    String query = "select orderNo from orders where status = 'Confirmed'"; // query for db to get confirmed orders
    for (int orderNo : ArmsDatabase.getManyIntResult(query)) {
      confirmedList.add(new Order(orderNo)); // iterates through the db querys return and add them to the confirmedList
    }
    return confirmedList; // return list of confirmed orders
  }
}
