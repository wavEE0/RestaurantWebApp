package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for Basket class.
 *
 * @author Eerfan Daad
 */
public class BasketTests {
  private Basket basket;
  private Item wings;
  private Item chips;
  private Item coke;
  private int tableNo;

  @BeforeEach
  void setUp() {
    basket = new Basket(tableNo);
    wings = new Item("Hot Wings", "Starter", "5 wings with chefs special white sauce",
        new BigDecimal("6.99"), 99, false, false, false, true);
    chips = new Item("Chips", "Starter", "Large crinkle-cut chips", new BigDecimal("2.99"), 199,
        false, false, false, false);
    coke = new Item("Coca-Cola", "Starter", "Can of Coke (500ml)", new BigDecimal("0.99"), 299,
        false, true, false, false);
  }

  @Test
  void createBasket() {
    basket = new Basket(tableNo);
    basket = new Basket(basket.getBasketNo());
  }

  @Test
  void checkTableNo() {
    basket = new Basket(18);
    assertEquals(basket.getTableNo(), 18);
  }

  @Test
  void addNewItems() {
    basket.addItem(wings, 1);
    basket.addItem(chips, 2);
    basket.addItem(coke, 3);

    assertTrue(basket.contains(wings));
    assertEquals(basket.getQty(wings), 1);

    assertTrue(basket.contains(chips));
    assertEquals(basket.getQty(chips), 2);

    assertTrue(basket.contains(coke));
    assertEquals(basket.getQty(coke), 3);
  }

  @Test
  void addExistingItems() {
    addNewItems();

    assertTrue(basket.contains(wings));
    basket.addItem(wings, 2);
    assertEquals(basket.getQty(wings), 3);

    assertTrue(basket.contains(wings));
    basket.addItem(wings, 5);
    assertEquals(basket.getQty(wings), 8);
  }

  @Test
  void removeItems() {
    addNewItems();

    basket.removeItem(wings);
    assertFalse(basket.contains(wings));
    assertTrue(basket.contains(chips));
    assertTrue(basket.contains(coke));

    basket.removeItem(chips);
    assertFalse(basket.contains(chips));
    assertTrue(basket.contains(coke));

    basket.removeItem(coke);
    assertFalse(basket.contains(coke));
  }

  @Test
  void updateItemQty() {
    addNewItems();

    assertTrue(basket.contains(wings));
    basket.updateItemQty(wings, 5);
    assertEquals(basket.getQty(wings), 5);
  }

  @Test
  void clearBasket() {
    addNewItems();

    basket.clear();
    assertFalse(basket.contains(wings));
    assertFalse(basket.contains(chips));
    assertFalse(basket.contains(coke));
  }

  @Test
  void getItemPrice() {
    addNewItems();

    assertEquals(basket.getItemPrice(wings),
        wings.getPrice().multiply(new BigDecimal(basket.getQty(wings))));

    assertEquals(basket.getItemPrice(chips),
        chips.getPrice().multiply(new BigDecimal(basket.getQty(chips))));

    assertEquals(basket.getItemPrice(coke),
        coke.getPrice().multiply(new BigDecimal(basket.getQty(coke))));
  }

  @Test
  void getTotalPrice() {
    addNewItems();
    // Total price calculation
    BigDecimal totalPrice = BigDecimal.ZERO;
    totalPrice = totalPrice.add(wings.getPrice().multiply(new BigDecimal(basket.getQty(wings))));
    totalPrice = totalPrice.add(chips.getPrice().multiply(new BigDecimal(basket.getQty(chips))));
    totalPrice = totalPrice.add(coke.getPrice().multiply(new BigDecimal(basket.getQty(coke))));

    assertEquals(basket.getTotalPrice(), totalPrice);
  }

  @Test
  void getItemList() {
    basket.addItem(wings, 1);
    basket.addItem(chips, 2);
    basket.addItem(coke, 3);
    basket.removeItem(coke);
    List<Item> items = basket.getItemList();

    assertEquals(items.size(), 2);
    assertEquals(items.get(0).getName(), "Hot Wings");
    assertEquals(items.get(1).getName(), "Chips");
  }

  @Test
  void addBasketToDb() {
    createBasket();
    // Query to get orderNo of Order_1 from DB
    String query = "select basketNo from baskets where basketNo = '" + basket.getBasketNo()
        + "' AND tableNo = " + basket.getTableNo() + " ;";
    assertEquals(ArmsDatabase.getStringResult(query), basket.getBasketNo());
  }
}
