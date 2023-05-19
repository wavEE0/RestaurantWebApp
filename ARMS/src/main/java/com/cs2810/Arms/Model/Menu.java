package com.cs2810.Arms.Model;

import java.util.ArrayList;
import java.util.List;
// class to store items for the menu
public class Menu {

  //function to create the menu
  private static void createMenu() {
    // Create menu table if not exists
    String tblName = "menu"; // table name for db
    String tblDesc = "item varchar(50) not null references items(name), "
        + "category varchar(25) references items(type), primary key(item)"; // table details
    ArmsDatabase.createTable(tblName, tblDesc); // creates table in db
  }

  //function for checking if an item is in the table
  public static boolean hasItem(Item item) {
    createMenu(); // creates table on db 
    String query = "select item from menu where item = '" + item.getName() + "'"; // query got checking if an item is in the db
    return ArmsDatabase.getStringResult(query).equals(item.getName()); // returns the result of querying the db
  }
  
  
  //function for adding an item to the db
  public static void addItem(Item item) {
    createMenu(); // creates table in db
    if (!hasItem(item)) { // if the item isnt already in the list 
      String tblName = "menu";
      String value = "'" + item.getName() + "', '" + item.getType() + "'"; // query to add items to the db
      ArmsDatabase.insertIntoTable(tblName, value); // inserts item into table 
    }
  }

  //function to remove item from the db
  public static void removeItem(Item item) {
    if (hasItem(item)) { // if the item is in the db 
      String tblName = "menu";
      String condition = "item = '" + item.getName() + "'"; // query of what to remove from the db
      ArmsDatabase.deleteFromTable(tblName, condition); // removes the entry from the db 
    }
  }

  //function to get menu items from the catagory
  public static List<Item> getMenuCat(Category category) {
    createMenu(); // creates table in db
    String query = "select item from menu where category = '" + category.toString() + "'"; // query for getting menu items of a catagory
    List<String> rs = ArmsDatabase.getManyStringResult(query); // list of items in the menu
    List<Item> items = new ArrayList<Item>();
    for (String name : rs) {
      items.add(new Item(name)); // add items to the list of items in that catagory
    }
    return MenuFilter.applyFilters(items); // returns the list of items with filters applied
  }

  //function to get items not on the menu
  public static List<Item> getNonMenuCat(Category category) {
    createMenu();
    String query = "select name from items left join menu on items.name = menu.item "
        + "where menu.item is null and items.type = '" + category + "'"; // query for getting items not on the menu
    List<String> rs = ArmsDatabase.getManyStringResult(query); // query to the db
    List<Item> items = new ArrayList<Item>();
    for (String name : rs) {
      items.add(new Item(name)); // add items to the list of items to return 
    }
    return MenuFilter.applyFilters(items); // returns the list of items not on the menu
  }

}
