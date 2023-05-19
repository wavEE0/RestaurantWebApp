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
public class KitchenStaffTests {
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
    // Making new orders
    orderList = new ArrayList<Order>();
    for (int i = 0; i < 10; i++) {
      // Making basket of orders
      Basket basket = new Basket(i);
      basket.addItem(wings, i + 1);
      basket.addItem(chips, i + 1);
      basket.addItem(coke, i + 1);
      orderList.add(new Order(basket));
    }
  }

  @Test
  void confirmedOrders() {
    for (Order order : orderList) {
      order.confirmOrder();
    }
    List<Order> confirmedList = KitchenStaff.confirmedOrders();

    assertEquals(confirmedList.size(), orderList.size());
    for (int i = 0; i < 10; i++) {
      assertEquals(confirmedList.get(i).getOrderNo(), orderList.get(i).getOrderNo());
      assertEquals(confirmedList.get(i).getStatus(), orderList.get(i).getStatus());
      assertEquals(confirmedList.get(i).getTableNo(), orderList.get(i).getTableNo());
      assertEquals(confirmedList.get(i).getTimeReceived(), orderList.get(i).getTimeReceived());
      assertEquals(confirmedList.get(i).getTimeDelivered(), orderList.get(i).getTimeDelivered());
      assertEquals(confirmedList.get(i).getBasket().getBasketNo(),
          orderList.get(i).getBasket().getBasketNo());
      // Checking quantity of each item
      for (Item item : confirmedList.get(i).getBasket().getItemList()) {
        assertEquals(confirmedList.get(i).getBasket().getQty(item), i + 1);
      }
    }
  }
}
