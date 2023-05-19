package com.cs2810.Arms.Model;

import java.sql.Timestamp;

// class for storing items in an order
public class Order {
  private final int orderNo;
  private final Basket basket;
  private String status;
  private final String timeReceived;
  private String timeDelivered;

  // constructor
  public Order(Basket basket) {
    this.basket = basket; // holds basket
    // Current time noted as time of receeival
    this.timeReceived = (new Timestamp(System.currentTimeMillis()).toString()).substring(0, 19);
    this.status = Status.Received.toString(); // status of order
    addToDb(); // adds to db
    this.orderNo = getOrderNo(); // order number
  }

  // constructor
  public Order(int orderNo) {
    this.orderNo = orderNo; // order number
    this.basket = getBasket(); // holds basket
    this.timeReceived = getTimeReceived(); // time received
  }

  // returns order number
  public int getOrderNo() {
    if (this.orderNo > 0) {
      return orderNo;
    }
    // query to get orderNo
    String query =
        "select orderNo from orders where basketNo = '" + this.basket.getBasketNo() + "';";
    return ArmsDatabase.getIntResult(query); // returns result of executing the above query
  }

  // returns basket
  public Basket getBasket() {
    String query = "select basketNo from orders where orderNo = " + this.orderNo; // query for
                                                                                  // retreiving name
                                                                                  // of db
    return new Basket(ArmsDatabase.getStringResult(query)); // returns name of basket retreived from
                                                            // db
  }

  // return table number
  public int getTableNo() {
    return basket.getTableNo(); // return table name
  }

  // returns time the order was received
  public String getTimeReceived() {
    String query = "select timeReceived from orders where orderNo = " + this.orderNo;
    return ArmsDatabase.getStringResult(query); // returns time the order was recived as it was
                                                // stored in the db
  }

  // returns the time an order was delivered
  public String getTimeDelivered() {
    String query = "select timeDelivered from orders where orderNo = " + this.orderNo;
    return ArmsDatabase.getStringResult(query); // returns the time an order was delivered
  }

  // sets the time an oder was delivered to the current time
  private void setTimeDelivered() {
    this.timeDelivered = (new Timestamp(System.currentTimeMillis()).toString()).substring(0, 19); // gets
                                                                                                  // current
                                                                                                  // time

    String tblName = "orders";
    String changes = "timeDelivered = '" + this.timeDelivered + "'";
    String condition = "orderNo = " + this.orderNo;
    ArmsDatabase.updateTable(tblName, changes, condition); // stores time delivered in the db
  }

  // returns status of order
  public String getStatus() {
    String query = "select status from orders where orderNo = " + this.orderNo;
    return ArmsDatabase.getStringResult(query); // return status of order from db
  }

  // sets orders status to confirmed
  public void confirmOrder() {
    if (getStatus().equals(Status.Received.toString())) {
      String tblname = "orders";
      String changes = "status = '" + Status.Confirmed.toString() + "'";
      String condition = "orderNo = " + this.orderNo;
      ArmsDatabase.updateTable(tblname, changes, condition); // updates status of order in db to
                                                             // confirmed
      // Tables.makeUA(getTableNo());
    }
  }

  // sets status of order to ready
  public void readyOrder() {
    if (getStatus().equals(Status.Confirmed.toString())) {
      String tblname = "orders";
      String changes = "status = '" + Status.Ready.toString() + "'";
      String condition = "orderNo = " + this.orderNo;
      ArmsDatabase.updateTable(tblname, changes, condition); // updates status of order in db to
                                                             // ready
    }
  }

  // sets status of order to delivered
  public void deliverOrder() {
    if (getStatus().equals(Status.Ready.toString())) {
      String tblname = "orders";
      String changes = "status = '" + Status.Delivered.toString() + "'";
      String condition = "orderNo = " + this.orderNo;
      ArmsDatabase.updateTable(tblname, changes, condition); // updates status of order in db to
                                                             // delivered
      setTimeDelivered(); // sets delivery time
    }
  }

  // cancels the order
  public void cancelOrder(String reason) {
    if (getStatus().equals(Status.Received.toString())) {
      String tblname = "orders";
      String changes =
          "status = '" + Status.Cancelled.toString() + "', comments = '" + reason + "'";
      String condition = "orderNo = " + this.orderNo;
      ArmsDatabase.updateTable(tblname, changes, condition); // updates the db to cancel the order
                                                             // with the given reason
    }
  }

  // returns comments on an order
  public String getComments() {
    String query = "select comments from orders where orderNo = " + this.orderNo;
    return ArmsDatabase.getStringResult(query); // returns order comments from the db
  }

  // returns weather the order has been payed for
  public String isPaid() {
    String query = "select paid from orders where orderNo = " + this.orderNo;
    return ArmsDatabase.getStringResult(query); // returns weather the order has been payed for
  }

  // add order to db
  private void addToDb() {
    // Create orders table if not exists
    String tblName = "orders"; // table name
    String tblDesc = "orderNo serial unique not null, "
        + "basketNo varchar(50) NOT NULL references baskets, status varchar(10), "
        + "tableNo int NOT NULL, timeReceived datetime not null, timeDelivered datetime, "
        + "comments varchar(255), paid char(1) not null"; // table information
    ArmsDatabase.createTable(tblName, tblDesc); // add table to db

    // Insert new basket into basksts
    String values = "default, '" + this.basket.getBasketNo() + "', '" + this.status + "', "
        + getTableNo() + ", '" + this.timeReceived + "', null, null, 'Y'"; // order information for
                                                                           // adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
  }
}
