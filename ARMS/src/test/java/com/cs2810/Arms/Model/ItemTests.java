package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for Item class.
 *
 * @author Eerfan Daad
 */
public class ItemTests {
  private Item wings;

  @BeforeEach
  void setUp() {
    ArmsDatabase.dropTable("items");
    wings = new Item("Hot Wings", "Starter", "5 wings with chefs special white sauce",
        new BigDecimal("6.99"), 99, false, false, false, true);
  }

  @Test
  void blankItem() {
    wings = new Item("Hot Dogs");

    assertEquals(wings.getName(), "Hot Dogs");
    assertEquals(wings.getType(), "");
    assertEquals(wings.getDescription(), "");
    assertEquals(wings.getPrice(), BigDecimal.ZERO);
    assertEquals(wings.getCal(), -1);
    assertFalse(wings.hasNuts());
    assertFalse(wings.hasCrst());
    assertFalse(wings.hasFish());
    assertFalse(wings.hasGlut());
    assertEquals(wings.getImgId(), -1);
  }

  @Test
  void filledItem() {
    assertEquals(wings.getName(), "Hot Wings");
    assertEquals(wings.getType(), "Starter");
    assertEquals(wings.getDescription(), "5 wings with chefs special white sauce");
    assertEquals(wings.getPrice(), new BigDecimal("6.99"));
    assertFalse(wings.hasNuts());
    assertFalse(wings.hasCrst());
    assertFalse(wings.hasFish());
    assertTrue(wings.hasGlut());
    assertEquals(wings.getImgId(), 1);
  }

}
