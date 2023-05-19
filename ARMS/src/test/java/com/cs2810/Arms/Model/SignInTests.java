package com.cs2810.Arms.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignInTests {

  @BeforeEach
  void setUp() {
    ArmsDatabase.dropTable("staff");
    SignIn.createUsers();
  }

  @AfterAll
  static void cleanUp() {
    ArmsDatabase.dropTable("staff");
    SignIn.createUsers();
    addUsers();
  }

  @Test
  static void addUsers() {
    ArmsDatabase.insertIntoTable("staff", "default, 'John', 'Smith', 'johnsmith', '"
        + SignIn.getHashedPassword("aaa") + "', 'Waiter', 'offline'");
    ArmsDatabase.insertIntoTable("staff", "default, 'Sarah', 'Cameron', 'sarahcameron' , '"
        + SignIn.getHashedPassword("aaa") + "', 'Waiter', 'offline'");
    ArmsDatabase.insertIntoTable("staff", "default, 'Spongebob', 'Squarepants', 'spongebob' , '"
        + SignIn.getHashedPassword("bbb") + "', 'Kitchen', 'offline'");
    ArmsDatabase.insertIntoTable("staff", "default, 'Mike', 'Hawkhard', 'mikehawkhard' , '"
        + SignIn.getHashedPassword("bbb") + "', 'Kitchen', 'offline'");
    ArmsDatabase.insertIntoTable("staff", "default, 'Bob', 'Dabillder', 'bobdabillder' , '"
        + SignIn.getHashedPassword("aaa") + "', 'Waiter', 'offline'");
  }

  @Test
  void checkLogin() {
    addUsers();
    assertTrue(SignIn.checkLogin("johnsmith", "aaa"));
    assertTrue(SignIn.checkLogin("bobdabillder", "aaa"));
    assertTrue(SignIn.checkLogin("mikehawkhard", "bbb"));

    assertFalse(SignIn.checkLogin("Imposter", "aaa"));
    assertFalse(SignIn.checkLogin("johnsmith", "xxx"));
    assertFalse(SignIn.checkLogin("sarahcameron", "xxx"));
  }

  @Test
  void checkLogout() {
    checkLogin();
    assertEquals(SignIn.getStatus("johnsmith"), "online");
    assertEquals(SignIn.getStatus("bobdabillder"), "online");

    SignIn.logout("johnsmith");
    SignIn.logout("bobdabillder");
    assertEquals(SignIn.getStatus("johnsmith"), "offline");
    assertEquals(SignIn.getStatus("bobdabillder"), "offline");
  }

  @Test
  void checkStatus() {
    checkLogin();

    assertEquals(SignIn.getStatus("sarahcameron"), "offline");
    assertEquals(SignIn.getStatus("spongebob"), "offline");

    assertEquals(SignIn.getStatus("johnsmith"), "online");
    assertEquals(SignIn.getStatus("mikehawkhard"), "online");
    assertEquals(SignIn.getStatus("bobdabillder"), "online");
  }

}
