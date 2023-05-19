package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for KitchenStaff class.
 *
 * @author Eerfan Daad
 */
public class WaiterTests {
  private String tblName = "orders";
  private Item wings;
  private Item chips;
  private Item coke;
  private List<Order> orderList;

  @BeforeEach
  void createOrderList() {
    // Clearing table
    ArmsDatabase.dropTable(tblName);
    // Initialize items
    wings = new Item("Hot Wings", "Starter", "5 wings with chefs special white sauce",
        new BigDecimal("6.99"), 99, false, false, false, true);
    chips = new Item("Chips", "Starter", "Large crinkle-cut chips", new BigDecimal("2.99"), 199,
        false, false, false, false);
    coke = new Item("Coca-Cola", "Drink", "Can of Coke (500ml)", new BigDecimal("0.99"), 299, false,
        true, false, false);
    // Making 10 new orderss
    orderList = new ArrayList<Order>();
    for (int i = 0; i < 10; i++) {
      Tables.assignTbl(i + 1, "johnsmith");
      // Making basket of orders
      Basket basket = new Basket(i + 1);
      basket.addItem(wings, i + 1);
      basket.addItem(chips, i + 1);
      basket.addItem(coke, i + 1);
      orderList.add(new Order(basket));
    }
  }

  @Test
  void receivedOrders() {
    List<Order> receivedList = Waiter.unconfirmedOrders("johnsmith");

    assertEquals(receivedList.size(), orderList.size());
    for (int i = 0; i < receivedList.size(); i++) {
      assertEquals(receivedList.get(i).getOrderNo(), orderList.get(i).getOrderNo());
      assertEquals(receivedList.get(i).getStatus(), orderList.get(i).getStatus());
      assertEquals(receivedList.get(i).getTableNo(), orderList.get(i).getTableNo());
      assertEquals(receivedList.get(i).getTimeReceived(), orderList.get(i).getTimeReceived());
      assertEquals(receivedList.get(i).getTimeDelivered(), orderList.get(i).getTimeDelivered());
      assertEquals(receivedList.get(i).getBasket().getBasketNo(),
          orderList.get(i).getBasket().getBasketNo());
      // Checking quantity of each item
      for (Item item : receivedList.get(i).getBasket().getItemList()) {
        assertEquals(receivedList.get(i).getBasket().getQty(item), i + 1);
      }
    }
  }

  @Test
  void updatedReceivedOrders() {
    // Changing Status of some orders
    orderList.get(0).cancelOrder("Customer changed their mind");
    orderList.get(1).confirmOrder();
    orderList.get(2).confirmOrder();
    orderList.get(3).confirmOrder();
    orderList.get(4).confirmOrder();
    orderList.get(4).readyOrder();

    // Checking updated receivedOrders
    List<Order> receivedList = Waiter.unconfirmedOrders("johnsmith");
    assertEquals(receivedList.size(), orderList.size() - 5);
    for (int i = 0; i < receivedList.size(); i++) {
      assertEquals(receivedList.get(i).getOrderNo(), orderList.get(i + 5).getOrderNo());
      assertEquals(receivedList.get(i).getStatus(), orderList.get(i + 5).getStatus());
    }
  }

  @Test
  void readyOrders() {
    // Changing Status of all orders to Ready
    for (int i = 0; i < orderList.size(); i++) {
      orderList.get(i).confirmOrder();
      orderList.get(i).readyOrder();
    }

    List<Order> readyList = Waiter.readyOrders("johnsmith");

    assertEquals(readyList.size(), orderList.size());
    for (int i = 0; i < readyList.size(); i++) {
      assertEquals(readyList.get(i).getOrderNo(), orderList.get(i).getOrderNo());
      assertEquals(readyList.get(i).getStatus(), orderList.get(i).getStatus());
      assertEquals(readyList.get(i).getTableNo(), orderList.get(i).getTableNo());
      assertEquals(readyList.get(i).getTimeReceived(), orderList.get(i).getTimeReceived());
      assertEquals(readyList.get(i).getTimeDelivered(), orderList.get(i).getTimeDelivered());
      assertEquals(readyList.get(i).getBasket().getBasketNo(),
          orderList.get(i).getBasket().getBasketNo());
      // Checking quantity of each item
      for (Item item : readyList.get(i).getBasket().getItemList()) {
        assertEquals(readyList.get(i).getBasket().getQty(item), i + 1);
      }
    }
  }

  @Test
  void updatedReadyOrders() {
    readyOrders();
    // Changing Status of some orders
    for (int i = 0; i < 5; i++) {
      orderList.get(i).deliverOrder();
    }

    // Checking updated receivedOrders
    List<Order> receivedList = Waiter.readyOrders("johnsmith");
    assertEquals(receivedList.size(), orderList.size() - 5);
    for (int i = 0; i < receivedList.size(); i++) {
      assertEquals(receivedList.get(i).getOrderNo(), orderList.get(i + 5).getOrderNo());
      assertEquals(receivedList.get(i).getStatus(), orderList.get(i + 5).getStatus());
    }
  }
}
