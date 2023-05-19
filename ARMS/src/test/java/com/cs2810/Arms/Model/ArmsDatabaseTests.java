package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArmsDatabaseTests {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void connectingToDatabase() {
    ArmsDatabase.connectToDatabase();

    assertEquals("SUCCESS: Connection made to database!\r\n", outContent.toString());
    assertEquals(errContent.toString(), "");
  }

  @Test
  public void dropTable() {
    String tblName = "example";
    ArmsDatabase.dropTable(tblName);

    assertTrue(outContent.toString().contains("DB: Dropping 'example' table..."));
    assertEquals(errContent.toString(), "");
  }

  @Test
  public void createTable() {
    dropTable();
    String tblName = "example";
    String tblDesc = "attr1 int, attr2 varchar(10)";
    ArmsDatabase.createTable(tblName, tblDesc);

    assertTrue(outContent.toString().contains("DB: Creating 'example' table..."));
    assertEquals(errContent.toString(), "");
  }

  @Test
  public void insertIntoTable() {
    createTable();
    String tblName = "example";
    String value = "9, 'text'";
    ArmsDatabase.insertIntoTable(tblName, value);

    assertTrue(outContent.toString().contains("DB: Inserting into 'example' table..."));
    assertEquals(errContent.toString(), "");
    String query = "SELECT attr1 FROM " + tblName + " WHERE attr1 = 9 AND attr2 = 'text'";
    assertEquals(ArmsDatabase.getIntResult(query), 9);
  }

  @Test
  public void updateTable() {
    insertIntoTable();
    String tblName = "example";
    String changes = "attr1 = 7";
    String condition = "attr2 = 'text'";
    ArmsDatabase.updateTable(tblName, changes, condition);

    assertTrue(outContent.toString().contains("DB: Updating record in 'example' table..."));
    assertEquals(errContent.toString(), "");
    String query = "SELECT attr1 FROM " + tblName + " WHERE attr1 = 7 AND attr2 = 'text'";
    assertEquals(ArmsDatabase.getIntResult(query), 7);
  }

  @Test
  public void deleteFromTable() {
    updateTable();
    String tblName = "example";
    String condition = "attr2 = 'text'";
    ArmsDatabase.deleteFromTable(tblName, condition);

    assertTrue(outContent.toString().contains("DB: Deleteing from 'example' table..."));
    assertEquals(errContent.toString(), "");
    String query = "SELECT attr1 FROM " + tblName + " WHERE " + condition;
    assertEquals(ArmsDatabase.getStringResult(query), "");
  }

  @Test
  public void executeQuery() {
    updateTable();
    String query = "SELECT * FROM example";
    ArmsDatabase.executeQuery(query);
    
    assertTrue(outContent.toString().contains("DB: Executing query..."));
    assertEquals(errContent.toString(), "");
  }

  @Test
  public void printResuts() {
    updateTable();
    String query = "SELECT * FROM example";
    ResultSet rs = ArmsDatabase.executeQuery(query);
    ArmsDatabase.printResuts(rs);
    
    assertTrue(outContent.toString().contains("DB: Printing query results..."));
    assertTrue(outContent.toString().contains("attr1  attr2  \n" + "7  text"));
    assertEquals(errContent.toString(), "");
  }

  @Test
  public void getIntResult() {
    updateTable();
    
    String query = "SELECT attr1 FROM example where attr1 =  7;";
    assertEquals(ArmsDatabase.getIntResult(query), 7);
  }

  @Test
  public void getStringResult() {
    updateTable();
    
    String query = "SELECT attr2 FROM example where attr2 = 'text';";
    assertEquals(ArmsDatabase.getStringResult(query), "text");
  }

  @Test
  public void getManyStringResult() {
    createTable();
    String tblName = "example";
    String value = "100, 'text0'";
    ArmsDatabase.insertIntoTable(tblName, value);
    value = "100, 'text1'";
    ArmsDatabase.insertIntoTable(tblName, value);
    value = "100, 'text2'";
    ArmsDatabase.insertIntoTable(tblName, value);
    
    String query = "SELECT attr2 FROM " + tblName + " WHERE attr1 = 100";
    List<String> list = ArmsDatabase.getManyStringResult(query);
    for (int i = 0; i < 3; i++) {
      assertEquals(list.get(i), "text" + i);
    }
  }

  @Test
  public void dropAllTables() {
    ArmsDatabase.dropAllTables();
    String query =
        "SELECT table_name " + "FROM information_schema.tables " + "WHERE table_schema = 'armsdb';";
    assertTrue(ArmsDatabase.getManyStringResult(query).isEmpty());
  }
}
