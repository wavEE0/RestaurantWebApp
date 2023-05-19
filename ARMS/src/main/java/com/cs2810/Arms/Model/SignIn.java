package com.cs2810.Arms.Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * class used to log users in and out
 * 
 * @author Eerfan Daad
 *
 */
public class SignIn {

  //function for creating users table
  public static void createUsers() {
    String tblName = "staff";
    String tblDesc = "staff_id serial unique not null, first_name varchar(50) not null, "
        + "last_name varchar(50) not null, username varchar(50) not null, "
        + "password varchar(150) NOT NULL, job varchar(20) not null, status varchar(10) not null, primary key(staff_id)"; // details of table to be constructed
    ArmsDatabase.createTable(tblName, tblDesc); //creates table
  }

  //function to attempt to log in a user
  public static boolean checkLogin(String username, String password) {
    String query = "select password from staff where username = '" + username + "'"; //query to find password for user
    if (ArmsDatabase.getStringResult(query).equals(getHashedPassword(password))) { //checks if given password and stored password are the same
      goOnline(username); //makes user appear online
      return true; 
    }
    return false;
  }

  //function to log user out
  public static void logout(String username) {
    if (getStatus(username).equals("online")) { //checks if user is online
      goOffline(username); // makes user appear offline
    }
  }

  //function to check what a users job is
  public static String checkJob(String username) {
    String query = "select job from staff where username = '" + username + "'";

    return ArmsDatabase.getStringResult(query); // returns what job they have from db
  }

  //function to hash a password to store it in the db
  public static String getHashedPassword(String password) {
    String hashedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      byte[] bytes = md.digest(); // splits password into bytes

      StringBuilder sb = new StringBuilder(); //string builder to reconstruct password from characters
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)); // hashes password
      }

      hashedPassword = sb.toString(); // turns hashed password into a string
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace(); // prints errors
    }
    return hashedPassword; //returns hashed password
  }

  //function to return users name
  public static String getName(String username) {
    String query = "select first_name from staff where username = '" + username + "'";
    return ArmsDatabase.getStringResult(query); //returns users name from db
  }

  //function to retrun users status
  public static String getStatus(String username) {
    String query = "select status from staff where username = '" + username + "'";
    return ArmsDatabase.getStringResult(query); // return the status of user from db
  }

  //function to make user go online
  private static void goOnline(String username) {
    String tblname = "staff";
    String changes = "status = 'online'";
    String condition = "username = '" + username + "'";
    ArmsDatabase.updateTable(tblname, changes, condition); //updates db to make user apear online
  }

  //function to make user go offline
  private static void goOffline(String username) {
    String tblname = "staff";
    String changes = "status = 'offline'";
    String condition = "username = '" + username + "'";
    ArmsDatabase.updateTable(tblname, changes, condition); //updates db to make user apear offline
  }

}
