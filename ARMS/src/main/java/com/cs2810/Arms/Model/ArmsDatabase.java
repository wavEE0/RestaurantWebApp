package com.cs2810.Arms.Model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// class for connecting to the database 
public class ArmsDatabase {

  private static Connection connection = connectToDatabase();

  public static void main(String[] argv) throws SQLException {

    Connection connection = connectToDatabase();
    if (connection != null) {
      System.out.println("SUCCESS: You made it!" + "\n\t You can now take control of your database!\n");
    } else {
      System.out.println("ERROR: \tFailed to make connection!");
      System.exit(1);
    }

    // TODO add here methods in testing

  }

  /* Connects to database */
  public static Connection connectToDatabase() {
    final String user = "root"; // user name
    final String password = "12345"; // password
    try {
      String protocol = "jdbc:mysql://"; // url for database
      String database = "localhost:3306"; // port for database
      String dbName = "/ArmsDB"; // database name
      String fullURL = protocol + database + dbName; // full url with all elements combined
      connection = DriverManager.getConnection(fullURL, user, password); // using the driver manager with the url and
                                                                         // username and password to connect to the db
    } catch (SQLException e) {
      String errorMsg = e.getMessage(); // gets error message
      if (errorMsg.contains("authentication failed")) {
        System.out.println("ERROR: \tDatabase password is incorrect. Check user/password in MySQL"); // if password is
                                                                                                     // incorrect print
                                                                                                     // sayings its
                                                                                                     // incorrect
      } else {
        System.out.println("Connection failed! Check MySQL User Permisssions"); // if cant connect it will say it cant
                                                                                // connect
        e.printStackTrace(); // prints detailed error
      }
    }
    if (connection != null) {
      System.out.println("SUCCESS: Connection made to database!"); // if the connection is successfull print that it is
                                                                   // successfull
    } else {
      System.out.println("ERROR: \tFailed to make connection!"); // if cant connect print that it cant connect
      System.exit(1);
    }
    return connection; // return successful connection
  }

  public static void insertIntoTableFromFile(String table, String file) {
    System.out.println("Inserting values into " + table);
    int numRowsFinal = 0;
    try (Scanner scanner = new Scanner(new File(file))) {
      Statement st = connection.createStatement();
      while (scanner.hasNextLine()) {
        String sCurrentLine = scanner.nextLine();
        // Insert each line to the DB
        String[] brokenLine = sCurrentLine.split(",");
        StringBuilder composedLine = new StringBuilder("INSERT INTO " + table + " VALUES (");
        int i;
        for (i = 0; i < brokenLine.length - 1; i++) {
          composedLine.append("'").append(brokenLine[i]).append("',");
        }
        composedLine.append("'").append(brokenLine[i]).append("')");
        st.executeUpdate(composedLine.toString());
        numRowsFinal += 1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    rowsInsertedCount(numRowsFinal, table);
  }

  // Print rows inserted.
  public static void rowsInsertedCount(int numRowsFinal, String table) {
    System.out.println(numRowsFinal + " rows inserted into " + table);
  }

  /* DELETION METHODS */

  /* Drops tables if already exists */
  public static void dropTable(String tblName) {
    System.out.println("DB: Dropping '" + tblName + "' table..."); // prints what table is being dropped
    try {
      Statement st = connection.createStatement(); // creates connection
      st.execute("DROP TABLE IF EXISTS " + tblName); // statement to be executed on the db
      st.close();
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
  }

  /* Delete data from tables */
  public static void deleteFromTable(String tblName, String condition) {
    System.out.println("DB: Deleteing from '" + tblName + "' table...");
    try {
      Statement st = connection.createStatement();
      st.executeUpdate("DELETE FROM " + tblName + " WHERE " + condition);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void dropAllTables() {
    String query = "SELECT table_name " + "FROM information_schema.tables " + "WHERE table_schema = 'armsdb';";
    List<String> tblList = ArmsDatabase.getManyStringResult(query);
    for (String tblName : tblList) {
      query = "Drop table if exists " + tblName + ";";
      System.out.println("DB: Executing query...");
      try {
        Statement st = connection.createStatement();
        st.execute(query);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // note: I might have spent useless time to make a method that already exists
  /* Drop Every Table */
  public static void dropEveryTable() {
    System.out.println("DB: Erasing every existent table in database...");
    try {
      Statement st = connection.createStatement();
      ResultSet rs = st.executeQuery("SHOW TABLES");
      while (rs.next()) {
        String loop = rs.getString(1);
        st.executeUpdate("DROP TABLE " + loop);
      }
      st.close();
      System.out.println("DB: All tables have been deleted successfully.");
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
  }
  /* CREATION METHODS */

  /* Creates tables */
  public static void createTable(String tblName, String tblDesc) {
    System.out.println("DB: Creating '" + tblName + "' table..."); // prints what table is being created
    try {
      Statement st = connection.createStatement(); // creates connection
      st.execute("CREATE TABLE IF NOT EXISTS " + tblName + "(" + tblDesc + ")"); // statement to be executed on the db
      st.close();
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
  }

  /* INSERTION METHODS */

  /* Inserts data into tables */
  public static void insertIntoTable(String tblName, String values) {
    System.out.println("DB: Inserting into '" + tblName + "' table..."); // prints what table the data is being inserted
                                                                         // into
    try {
      Statement st = connection.createStatement(); // creates connection
      st.executeUpdate("INSERT INTO " + tblName + " VALUES (" + values + ")"); // statement to be executed on the db
    } catch (Exception e) {
      e.printStackTrace(); // print errors
    }
  }

  /* Updates data into tables */
  public static void updateTable(String tblName, String changes, String condition) {
    System.out.println("DB: Updating record in '" + tblName + "' table..."); // prints what table data is being updated
                                                                             // in
    try {
      Statement st = connection.createStatement(); // creates connection
      st.executeUpdate("UPDATE " + tblName + " SET " + changes + " WHERE " + condition); // statement to be executed on
                                                                                         // the db
    } catch (Exception e) {
      e.printStackTrace(); // print errors
    }
  }

  /* Executes queries and returns results */
  public static ResultSet executeQuery(String query) {
    System.out.println("DB: Executing query...");
    try {
      Statement st = connection.createStatement(); // creates connection
      ResultSet rs = st.executeQuery(query); // statement to be executed on the db
      return rs;
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
      return null;
    }
  }

  /* Prints data from ResultSets (for testing) */
  public static void printResuts(ResultSet rs) {
    System.out.println("DB: Printing query results...");
    try {
      if (rs != null) {
        ResultSetMetaData rsmd = rs.getMetaData(); // gets data from results set
        // Printing column headers
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          System.out.print(rsmd.getColumnName(i) + "  "); // prints information from one colomn of result set
        }
        System.out.print("\n"); // prints new line

        // Printing info columns
        while (rs.next()) {
          for (int j = 1; j <= rsmd.getColumnCount(); j++) {
            System.out.print(rs.getString(j) + "  "); // prints column of information
          }
          System.out.print("\n"); // prints new line
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
  }

  /* returns an int from database */
  public static int getIntResult(String query) {
    int result = -1; // var to store result
    ResultSet rs = executeQuery(query); // statement to be executed on the db
    try {
      if (rs.next()) {
        result = rs.getInt(1); // gets int from result
      } else {
        System.err.println("No such element found."); // if no element is found this message is printed
      }
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
    return result; // returns result
  }

  /* returns an string from database */
  public static String getStringResult(String query) {
    String result = ""; // var to store result
    ResultSet rs = executeQuery(query); // statement to be executed on the db
    try {
      if (rs != null && rs.next()) {
        result = rs.getString(1); // if query isnt empty gets string from result
      } else {
        System.err.println("No such element found."); // if no element is found this message is printed
      }
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
    return result; // returns result
  }

  /* returns an BigDecimal from database */
  public static BigDecimal getDecResult(String query) {
    BigDecimal result = BigDecimal.ZERO; // car to store results
    ResultSet rs = executeQuery(query); // statement to be executed on the db
    try {
      if (rs.next()) {
        result = rs.getBigDecimal(1); // gets big decimal from query
      } else {
        System.err.println("No such element found."); // if no element is found this message is printed
      }
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
    return result; // returns result
  }

  /* returns a list of strings from database */
  public static List<String> getManyStringResult(String query) {
    List<String> result = new ArrayList<String>(); // list to store results
    ResultSet rs = executeQuery(query); // statement to be executed on the db
    try {
      if (rs != null) {
        while (rs.next()) {
          result.add(rs.getString(1)); // adds all string results to result list
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
    return result; // returns result list
  }

  /* returns a whole colomns of information in a list */
  public static List<String> getColomnStringResult(String query, int colomn) {
    List<String> result = new ArrayList<String>(); // list to store results
    ResultSet rs = executeQuery(query); // statement to be executed on the db
    try {
      if (rs != null) {
        while (rs.next()) {
          result.add(rs.getString(colomn)); // adds all string results to result list
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
    return result; // returns result list
  }

  /* returns a list of ints from database */
  public static List<Integer> getManyIntResult(String query) {
    List<Integer> result = new ArrayList<Integer>(); // list to store results
    ResultSet rs = executeQuery(query); // statement to be executed on the db
    try {
      if (rs != null) {
        while (rs.next()) {
          result.add(rs.getInt(1)); // adds all string results to result list
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(); // print errors
    }
    return result; // returns result list
  }

}
