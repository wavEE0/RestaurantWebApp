package com.cs2810.Arms.Model;

import java.math.BigDecimal;

/**
 * Items/dishes that the restaurant may offer.
 *
 * @author Eerfan Daad
 */
public class Item {
  private final String name;
  private String type;
  private String description;
  private BigDecimal price;
  private int cal;
  private boolean nuts;
  private boolean crst;
  private boolean fish;
  private boolean glut;

  /* Constructors */
  public Item(String name) {
    this.name = name;
  }

  // constructor
  public Item(String name, String type, String description, BigDecimal price, int cal, boolean nuts,
      boolean crst, boolean fish, boolean glut) {
    this.name = name;
    this.type = type;
    this.description = description;
    this.price = price;
    this.nuts = nuts;
    this.crst = crst;
    this.fish = fish;
    this.glut = glut;
    this.cal = cal;
    addToDb(); // adds item to db
  }

  /* Getters and setters for item atrributes */
  public String getName() {
    return name;
  }

  //get image id
  public int getImgId() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select imageId from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getIntResult(query); //returns image id got from db
  }

  //get type
  public String getType() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select type from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getStringResult(query); //returns item type got from db
  }

  //get description
  public String getDescription() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select description from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getStringResult(query); //returns item description got from db
  }

  //get price
  public BigDecimal getPrice() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select price from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getDecResult(query); //returns item price got from db
  }

  //get cal
  public int getCal() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select cal from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getIntResult(query); //returns item calories got from db
  }

  //get weather item has nuts
  public boolean hasNuts() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select nuts from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getStringResult(query).equals("Y"); //returns weather item has nuts 
  }

  //get weather item has crustations
  public boolean hasCrst() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select crst from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getStringResult(query).equals("Y"); //returns weather item has crustations 
  }

  //get weather item has fish
  public boolean hasFish() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select fish from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getStringResult(query).equals("Y"); //returns weather item has fish
  }

  //get weather item has glutens
  public boolean hasGlut() {
    String tblName = "items";
    String condition = "name = '" + this.getName() + "';"; //condition for query
    String query = "select glut from " + tblName + " where " + condition; //query for db
    return ArmsDatabase.getStringResult(query).equals("Y"); //returns weather item has gluten
  }

  //gets alergens
  public String getAllergens() {
    String alrg = ""; // base string for alergens
    if (this.hasNuts()) {
      alrg += "| Nuts |"; // if item contains nuts add to string 
    }
    if (this.hasCrst()) {
      alrg += "| Crustaceans |"; // if item contains crustations add to string 
    }
    if (this.hasFish()) {
      alrg += "| Fish |"; // if item contains fish add to string 
    }
    if (this.hasGlut()) {
      alrg += "| Gluten |"; // if item contains gluten add to string 
    }
    return alrg; // retrurns a string containing all alergens that apply to that item
  }

  //function to translate alergens from bool to y or n
  private String translateAlrg(boolean alrg) {
    if (alrg) {
      return "Y"; //return Y
    }
    return "N"; //return N
  }

  //function to add items to db
  private void addToDb() {
    // Create items table if not exists
    String tblName = "items"; // table name
    String tblDesc = "name varchar(50) NOT NULL, " + "type varchar(25) NOT NULL, "
        + "description varchar(255) NOT NULL, " + "price dec(5,2) NOT NULL, " + "cal int NOT NULL, "
        + "nuts char(1) NOT NULL, " + "crst char(1) NOT NULL, " + "fish char(1) NOT NULL, "
        + "glut char(1) NOT NULL, " + "imageId serial UNIQUE NOT NULL, " + "primary key (name)"; // table description
    ArmsDatabase.createTable(tblName, tblDesc); // creates table

    // Checking if exists in table
    String query = "select name from items where name = '" + this.name + "';"; //query for db
    if (!ArmsDatabase.getStringResult(query).equals(this.name)) { // checks if table exists
      // Insert new item into items
      String values = "'" + this.name + "', '" + this.type + "', '" + this.description + "', "
          + this.price + ", '" + this.cal + "', '" + translateAlrg(this.nuts) + "', '"
          + translateAlrg(this.crst) + "', '" + translateAlrg(this.fish) + "', '"
          + translateAlrg(this.glut) + "', DEFAULT"; // values to add to db
      ArmsDatabase.insertIntoTable("items", values); // inserts items into db
    }
  }
}
