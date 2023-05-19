package com.cs2810.Arms.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of tables.
 *
 * @author Eerfan Daad
 */
public class Tables {
  // function to check if the given table number is
  public static boolean checkAvl(int tableNo) {
    String query = "select avl from tables where tableNo = " + tableNo;
    return ArmsDatabase.getStringResult(query).equals("Y"); // if there is a table with the given
                                                            // number already, if not return true
  }

  // function to make a table that is already asinged as used, to avalable
  public static void clearTbl(int tableNo) {
    String tblName = "tables";
    String changes = "avl = 'Y', waiter = 'None'";
    String condition = "tableNo = " + tableNo;
    ArmsDatabase.updateTable(tblName, changes, condition); // updates table to be avalable
  }

  // function to update a table to be taken
  public static void assignTbl(int tableNo, String waiter) {
    String tblName = "tables";
    String changes = "avl = 'N', waiter = '" + waiter + "'";
    String condition = "tableNo = " + tableNo;
    ArmsDatabase.updateTable(tblName, changes, condition); // updates table to be unavalable
  }

  // function that returns all avalable table
  public static List<Integer> getAllAvl() {
    String query = "select tableNo from tables where avl = 'Y'";
    return ArmsDatabase.getManyIntResult(query); // returns all avalable tables
  }

  public static List<Integer> getAllUA() {
    String query = "select tableNo from tables where avl = 'N'";
    return ArmsDatabase.getManyIntResult(query); // returns all avalable tables
  }

  public static List<Integer> getAssignedTbls(String username) {
    List<Integer> tblList = new ArrayList<Integer>();
    String query = "select tableNo from tables where avl = 'N' and waiter = '" + username + "'";
    for (int tableNo : ArmsDatabase.getManyIntResult(query)) {
      tblList.add(tableNo);
    }
    return tblList;
  }
}
