package com.cs2810.Arms.Model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

/**
 * Tests for Table class.
 *
 * @author Eerfan Daad
 */
public class TablesTests {
  @BeforeEach
  void setUpTablesDb() {
    ArmsDatabase.dropTable("tables");
    // Create tables table if not exists
    String tblName = "tables";
    String tblDesc = "tableNo serial unique NOT NULL, avl varchar(1) NOT NULL, "
        + "waiter varchar (50) references staff.username, " + "primary key (tableNo)";
    ArmsDatabase.createTable(tblName, tblDesc);

    // Insert new empty tables
    for (int i = 1; i <= 10; i++) {
      String values = "Default , 'Y', 'None'";
      ArmsDatabase.insertIntoTable(tblName, values);
    }
  }

  @AfterAll
  static void cleanUp() {
    for (int i = 1; i <= 10; i++) {
      String values = "Default , 'Y', 'None'";
      ArmsDatabase.insertIntoTable("tables", values);
    }
  }

  @Test
  void getAvailability() {
    for (int i = 1; i <= 10; i++) {
      assertTrue(Tables.checkAvl(i));
    }
  }

  @Test
  void MakeUA() {
    for (int i = 6; i <= 10; i++) {
      Tables.assignTbl(i, "johnsmith");
      assertFalse(Tables.checkAvl(i));
    }
  }

  @Test
  void MakeAvl() {
    MakeUA();
    for (int i = 6; i <= 10; i++) {
      Tables.clearTbl(i);
      assertTrue(Tables.checkAvl(i));
    }
  }

  @Test
  void getAllAvl() {
    MakeUA();
    List<Integer> avlTables = Tables.getAllAvl();

    assertEquals(avlTables.size(), 5);
    for (int i = 1; i <= 5; i++) {
      assertEquals(avlTables.get(i - 1), i);
    }
  }

}
