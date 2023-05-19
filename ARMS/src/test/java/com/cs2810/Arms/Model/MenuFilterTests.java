package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for MenuFilter class.
 *
 * @author Eerfan Daad
 */
public class MenuFilterTests {
  private List<Item> starters;
  private List<Item> mains;
  private Item wings;
  private Item chips;
  private Item coke;
  private Item pasta;
  private Item pizza;
  private Item taco;

  @BeforeEach
  void setUp() {
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
    starters.add(chips);
    starters.add(coke);
    starters.add(wings);
    mains = new ArrayList<Item>();
    mains.add(pasta);
    mains.add(pizza);
    mains.add(taco);
  }

  @Test
  void applyFilters() {
    // Single filter
    MenuFilter.setAlg_nuts(true);
    List<Item> results = MenuFilter.applyFilters(mains);

    assertEquals(results.size(), 2);
    assertEquals(results.get(0).getName(), pizza.getName());
    assertEquals(results.get(1).getName(), taco.getName());

    // Another filter
    MenuFilter.setAlg_glut(true);
    results = MenuFilter.applyFilters(mains);

    assertEquals(results.size(), 1);
    assertEquals(results.get(0).getName(), taco.getName());

    // Another filter
    MenuFilter.setAlg_fish(true);
    results = MenuFilter.applyFilters(mains);

    assertEquals(results.size(), 0);

    // Removing some filters
    MenuFilter.setAlg_nuts(false);
    MenuFilter.setAlg_fish(false);
    results = MenuFilter.applyFilters(mains);

    assertEquals(results.size(), 2);
    assertEquals(results.get(0).getName(), pasta.getName());
    assertEquals(results.get(1).getName(), taco.getName());
  }

  @Test
  void resetFilters() {
    MenuFilter.setAlg_nuts(true);
    MenuFilter.setAlg_fish(true);
    MenuFilter.setAlg_glut(true);
    MenuFilter.setCal_sub_1000(true);

    MenuFilter.resetFilters();

    assertFalse(MenuFilter.isAlg_nuts());
    assertFalse(MenuFilter.isAlg_fish());
    assertFalse(MenuFilter.isAlg_glut());
    assertFalse(MenuFilter.isCal_sub_1000());
  }
}
