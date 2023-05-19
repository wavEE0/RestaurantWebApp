package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for Order class.
 *
 * @author Eerfan Daad
 */
public class OrderTests {
  private Order order_1;
  private Basket basket_1;
  private Order order_2;
  private Basket basket_2;
  private Item wings;
  private Item chips;
  private Item coke;
  private int tableNo_1;
  private int tableNo_2;

  @BeforeEach
  void setUpItems() {
    wings = new Item("Hot Wings", "Starter", "5 wings with chefs special white sauce",
        new BigDecimal("6.99"), 99, false, false, false, true);
    chips = new Item("Chips", "Starter", "Large crinkle-cut chips", new BigDecimal("2.99"), 199,
        false, false, false, false);
    coke = new Item("Coca-Cola", "Drink", "Can of Coke (500ml)", new BigDecimal("0.99"), 299, false,
        true, false, false);
  }

  @BeforeEach
  void setUpBaskets() {
    tableNo_1 = 1;
    basket_1 = new Basket(tableNo_1);
    basket_1.addItem(wings, 1);
    basket_1.addItem(coke, 1);

    tableNo_2 = 2;
    basket_2 = new Basket(tableNo_2);
    basket_1.addItem(wings, 3);
    basket_1.addItem(chips, 2);
  }

  @Test
  void createOrders() {
    order_1 = new Order(basket_1);
    assertEquals(order_1.getBasket().getBasketNo(), basket_1.getBasketNo());
    // Making order_2 using order_1's orderNo making order_2 same as order_1
    order_2 = new Order(order_1.getOrderNo());
    assertEquals(order_1.getBasket().getBasketNo(), basket_1.getBasketNo());

    order_2 = new Order(basket_2);
    assertEquals(order_2.getBasket().getBasketNo(), basket_2.getBasketNo());
  }

  @Test
  void checkOrderNoUniqueness() {
    createOrders();
    assertEquals(order_1.getOrderNo(), order_2.getOrderNo() - 1);
  }

  @Test
  void checkTimeReceived() throws InterruptedException {
    order_1 = new Order(basket_1);
    Thread.sleep(1000);
    order_2 = new Order(basket_2);
    // order_2 was made a second after order_1 so the time should be different
    assertNotEquals(order_1.getTimeReceived(), order_2.getTimeReceived());
  }

  @Test
  void checkReceivedStatus() {
    createOrders();
    assertEquals(order_1.getStatus(), Status.Received.toString());
  }

  @Test
  void checkConfirmedStatus() {
    checkReceivedStatus();
    order_1.confirmOrder();
    assertEquals(order_1.getStatus(), Status.Confirmed.toString());
  }

  @Test
  void checkReadyStatus() {
    checkReceivedStatus();
    // Order cannot be ready unless confirmed
    String statusBefore = order_1.getStatus();
    order_1.readyOrder();
    assertEquals(order_1.getStatus(), statusBefore);
    // Order can be ready if it was confirmed prior
    checkConfirmedStatus();
    order_1.readyOrder();
    assertEquals(order_1.getStatus(), Status.Ready.toString());
  }

  @Test
  void checkDeliveredStatus() {
    checkConfirmedStatus();
    // Order cannot be delivered unless ready
    String statusBefore = order_1.getStatus();
    order_1.deliverOrder();
    assertEquals(order_1.getStatus(), statusBefore);
    // Order can be delivered if it was ready prior
    order_1.readyOrder();
    order_1.deliverOrder();
    assertEquals(order_1.getStatus(), Status.Delivered.toString());
  }

  @Test
  void checkCancelledStatus() {
    // Order can be cancelled if only received and not yet confirmed
    checkReceivedStatus();
    order_1.cancelOrder("Customer changed their mind");
    assertEquals(order_1.getStatus(), Status.Cancelled.toString());
    assertEquals(order_1.getComments(), "Customer changed their mind");
    // Order cannot be cancelled if already confirmed
    order_2.confirmOrder();
    order_2.cancelOrder("Customer changed their mind");
    assertEquals(order_2.getStatus(), Status.Confirmed.toString());
    assertEquals(order_2.getComments(), null);
  }

  @Test
  void checkTimeDelivered() {
    createOrders();
    assertEquals(order_1.getTimeDelivered(), null);
    checkDeliveredStatus();
    // Checking against current time since they should be executed within a second
    assertEquals(order_1.getTimeDelivered(),
        (new Timestamp(System.currentTimeMillis()).toString()).substring(0, 19));
  }

  @Test
  void addOrderToDb() {
    createOrders();
    // Query to get orderNo of Order_1 from DB
    String query = "select orderNo from orders where orderNo = " + order_1.getOrderNo()
        + " AND basketNo = '" + order_1.getBasket().getBasketNo() + "' AND status = '"
        + order_1.getStatus() + "' AND timeReceived = '" + order_1.getTimeReceived()
        + "' AND timeDelivered is null AND comments is null;";
    assertEquals(ArmsDatabase.getIntResult(query), order_1.getOrderNo());
  }

  @Test
  void checkPaid() {
    createOrders();
    assertEquals(order_1.isPaid(), "Y");
    assertEquals(order_2.isPaid(), "Y");
  }
}
