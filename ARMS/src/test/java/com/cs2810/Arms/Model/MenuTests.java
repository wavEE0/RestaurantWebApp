package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuTests {
  private List<Item> starters;
  private List<Item> mains;
  private List<Item> drinks;
  private Item wings;
  private Item chips;
  private Item coke;
  private Item pasta;
  private Item pizza;
  private Item taco;

  @BeforeEach
  void setUp() {
    ArmsDatabase.dropTable("items");
    ArmsDatabase.dropTable("menu");


    wings = new Item("Hot Wings", "Starter", "5 wings with chefs special white sauce",
        new BigDecimal("6.99"), 99, false, false, false, true);
    chips = new Item("Chips", "Starter", "Large crinkle-cut chips", new BigDecimal("2.99"), 199,
        false, false, false, false);
    coke = new Item("Coca-Cola", "Drink", "Can of Coke (500ml)", new BigDecimal("0.99"), 299, false,
        true, false, false);
    pasta = new Item("Pasta", "Main", "bolognese", new BigDecimal(13.99), 200, true, true, false,
        false);
    pizza = new Item("Pizza", "Main", "pizza with cheese and tomato", new BigDecimal(14.99), 100,
        false, false, true, true);
    taco = new Item("Taco", "Main", "cheese and beef taco", new BigDecimal(7.99), 600, false, true,
        true, false);

    starters = new ArrayList<Item>();
    mains = new ArrayList<Item>();
    drinks = new ArrayList<Item>();
    mains.add(pasta);
    mains.add(pizza);
    mains.add(taco);
    starters.add(chips);
    starters.add(wings);
    drinks.add(coke);
  }

  @Test
  void addItem() {
    for (Item item : starters) {
      Menu.addItem(item);
      assertTrue(Menu.hasItem(item));
    }

    for (Item item : mains) {
      Menu.addItem(item);
      assertTrue(Menu.hasItem(item));
    }

    for (Item item : drinks) {
      Menu.addItem(item);
      assertTrue(Menu.hasItem(item));
    }
  }

  @Test
  void removeItem() {
    addItem();

    Menu.removeItem(wings);
    assertFalse(Menu.hasItem(wings));

    Menu.removeItem(pasta);
    assertFalse(Menu.hasItem(pasta));

    Menu.removeItem(taco);
    assertFalse(Menu.hasItem(taco));
  }

  @Test
  void getMenuCat() {
    addItem();

    List<Item> results = Menu.getMenuCat(Category.Starter);
    assertEquals(starters.size(), results.size());
    for (int i = 0; i < 2; i++) {
      assertEquals(starters.get(i).getName(), results.get(i).getName());
    }

    results = Menu.getMenuCat(Category.Main);
    assertEquals(mains.size(), results.size());
    for (int i = 0; i < 3; i++) {
      assertEquals(mains.get(i).getName(), results.get(i).getName());
    }

    results = Menu.getMenuCat(Category.Drink);
    assertEquals(drinks.size(), results.size());
     for (int i = 0; i < drinks.size(); i++) {
     assertEquals(drinks.get(i).getName(), results.get(i).getName());
     }
  }

  @Test
  void getNonMenuCat() {
    removeItem();

    List<Item> results = Menu.getNonMenuCat(Category.Starter);
    assertEquals(1, results.size());
    assertEquals(results.get(0).getName(), wings.getName());

    results = Menu.getNonMenuCat(Category.Main);
    assertEquals(results.size(), 2);
    assertEquals(results.get(0).getName(), pasta.getName());
    assertEquals(results.get(1).getName(), taco.getName());
  }

}
