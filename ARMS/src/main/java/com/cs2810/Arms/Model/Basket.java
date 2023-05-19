package com.cs2810.Arms.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Basket of Items selected for order.
 *
 * @author Eerfan Daad
 */
public class Basket {
  private HashMap<Item, Integer> basket; // hash map to store items
  private final String basketNo; // string to store basket number
  private final int tableNo; // string to store table number

  // constructor 
  public Basket(int tableNo) {
    basket = new HashMap<Item, Integer>();
    this.basketNo = UUID.randomUUID().toString().replace("-", ""); // gives random basket id
    this.tableNo = tableNo;
    addToDb();
  }

  // constructor
  public Basket(String basketNo) {
    this.basketNo = basketNo;
    this.tableNo = getTableNo(); // calls function to set table number
  }

  // function to add items to the basket
  public void addItem(Item item, int qty) {
    if (this.contains(item)) {
      qty += this.getQty(item);
      this.updateItemQty(item, qty); // updates item quantity 
    } else {
      String tblName = "basket" + this.basketNo; // table name 
      String values =
          "'" + item.getName() + "', " + qty + ", " + item.getPrice().multiply(new BigDecimal(qty)); //query to insert into db
      ArmsDatabase.insertIntoTable(tblName, values); // inserts values into database 
    }
  }

  //function to remove items from db
  public void removeItem(Item item) {
    String tblName = "basket" + this.basketNo; // table name
    String condition = "item = '" + item.getName() + "';"; // query of what to delete
    ArmsDatabase.deleteFromTable(tblName, condition); // runs query to delete item from db
  }

  // function to update item quantity 
  public void updateItemQty(Item item, int qty) {
    if (qty == 0) {
      basket.remove(item); // removes item from basket
    } else {
      String tblName = "basket" + this.basketNo; // table name 
      String changes =
          "qty = " + qty + ", price = " + item.getPrice().multiply(new BigDecimal(qty)); //query to change items in db
      String condition = "item = '" + item.getName() + "';"; //query to insert into db
      ArmsDatabase.updateTable(tblName, changes, condition); // runs update table query
    }
  }

  // function to delete table from db
  public void clear() {
    String tblName = "basket" + this.basketNo; // table to clear
    ArmsDatabase.deleteFromTable(tblName, "TRUE"); // runs table clear query
  }

  //function to return basket number
  public String getBasketNo() {
    return basketNo; //returns basket number
  }

  //fcuntion to get table number
  public int getTableNo() {
    String query = "select tableNo from baskets where basketNo = '" + this.getBasketNo() + "' ;"; // query for db to get table number
    return ArmsDatabase.getIntResult(query); // returns table number retreved from db
  }

  // function to see if a basket contains any items
  public boolean contains(Item item) {
    String tblName = "basket" + this.basketNo; // table name 
    String condition = "item = '" + item.getName() + "';"; // conditions for query
    String query = "select item from " + tblName + " where " + condition; // query to run on db
    return ArmsDatabase.getStringResult(query) != ""; // if items dont equal nothing return true 
  }

  //function to get quantity of item
  public int getQty(Item item) {
    String tblName = "basket" + this.basketNo; // table name 
    String condition = "item = '" + item.getName() + "';"; // conditions for query
    String query = "select qty from " + tblName + " where " + condition; //query to run on db
    return ArmsDatabase.getIntResult(query); // returns item quantity converted to int from db
  }

  //function to get item price
  public BigDecimal getItemPrice(Item item) {
    String tblName = "basket" + this.basketNo; // table name 
    String condition = "item = '" + item.getName() + "';"; // conditions for query
    String query = "select price from " + tblName + " where " + condition; //query to run on db
    return ArmsDatabase.getDecResult(query); // returns price
  }

  //function to get total price of basket
  public BigDecimal getTotalPrice() {
    String tblName = "basket" + this.basketNo; // table name 
    String query = "select sum(price) from " + tblName; //query to run on db
    BigDecimal total = ArmsDatabase.getDecResult(query); // gets price price from db
    if (total == null) {
      return BigDecimal.ZERO; // if theres no price return 0
    }
    return total; // return total price
  }

  //function to return a list of items
  public List<Item> getItemList() {
    List<Item> items = new ArrayList<Item>();
    String tblName = "basket" + this.basketNo; // table name 
    String query = "select item from " + tblName; //query to run on db
    for (String name : ArmsDatabase.getManyStringResult(query)) {
      items.add(new Item(name)); // iterates through items and adds them to a list
    }
    return items; // returns list of items
  }

  //function to add to database
  private void addToDb() {
    // Create baskets table if not exists
    String tblName = "baskets"; // table name 
    String tblDesc = "basketNo varchar(255) NOT NULL, tableNo int NOT NULL, primary key (basketNo)"; // what elements for the database to add
    ArmsDatabase.createTable(tblName, tblDesc); // creates table

    // Insert new basket into basksts
    String values = "'" + this.getBasketNo() + "', " + this.tableNo;
    ArmsDatabase.insertIntoTable("baskets", values); // inserts new basket into baskets

    // Create table for basket
    tblName = "basket" + this.basketNo;
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc); // creates table for basket
  }
}
