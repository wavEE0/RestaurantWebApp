package com.cs2810.Arms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cs2810.Arms.Model.ArmsDatabase;
import com.cs2810.Arms.Model.Item;
import com.cs2810.Arms.Model.Menu;
import com.cs2810.Arms.Model.Order;
import com.cs2810.Arms.Model.SignIn;
import com.cs2810.Arms.Model.Tables;

/**
 * Main method for web application.
 *
 * @author Eerfan Daad
 */
@SpringBootApplication
public class ArmsApplication {
  // function for starting running of arms application
  public static void main(String[] args) {
    databasePopulator();
    SpringApplication.run(ArmsApplication.class, args);
  }

  // Database Populator
  private static void databasePopulator() {
    ArmsDatabase.connectToDatabase();
    ArmsDatabase.dropAllTables();
    itemsAddToDb();
    menuAddToDb();
    basketAddToDb();
    addStaffToDb();
    OrdersAddToDb();
    TablesAddToDb();
  }

  // External Methods
  // Add items in DB
  private static void itemsAddToDb() {
    String tblName = "items";
    String tblDesc = "name varchar(50) NOT NULL, " + "type varchar(25) NOT NULL, "
        + "description varchar(255) NOT NULL, " + "price dec(5,2) NOT NULL, " + "cal int NOT NULL, "
        + "nuts char(1) NOT NULL, " + "crst char(1) NOT NULL, " + "fish char(1) NOT NULL, "
        + "glut char(1) NOT NULL, " + "imageId serial UNIQUE NOT NULL, " + "primary key (name)";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("items",
        "'SPICY CHICKEN STRIPS','Starter','Spicy chicken strips served with a tangy dipping sauce','9.99','450','N','N','N','Y','1'");
    ArmsDatabase.insertIntoTable("items",
        "'CHEESY GARLIC TEAR and SHARE','Starter','Warm cheesy garlic bread for sharing','8.99','600','N','Y','N','Y','2'");
    ArmsDatabase.insertIntoTable("items",
        "'DORITOS CHEESY NACHOS','Starter','Crunchy Doritos topped with melted cheese','7.99','800','N','Y','N','Y','3'");
    ArmsDatabase.insertIntoTable("items",
        "'CHEESE and JALAPENO LOADED WEDGES','Starter','Crispy potato wedges loaded with melted cheese and jalapenos','8.99','650','N','N','N','Y','4'");
    ArmsDatabase.insertIntoTable("items",
        "'PINEAPPLE SALSA SEABASS','Starter','Grilled seabass with a sweet and tangy pineapple salsa','12.99','400','N','N','Y','N','5'");
    ArmsDatabase.insertIntoTable("items",
        "'CHICKEN and CHORIZO SKEWERS','Starter','Skewered chicken and chorizo with a zesty dipping sauce','10.99','550','N','N','N','Y','6'");
    ArmsDatabase.insertIntoTable("items",
        "'TOPPED SALAD','Starter','Fresh mixed greens topped with cherry tomatoes cucumber and red onion','6.99','200','Y','N','N','Y','7'");
    ArmsDatabase.insertIntoTable("items",
        "'LOADED NACHOS','Starter','Classic nachos loaded with ground beef cheese and sour cream','10.99','900','N','Y','N','Y','8'");
    ArmsDatabase.insertIntoTable("items",
        "'CAJUN DUSTED GREEN BEANS','Starter','Crispy green beans dusted with cajun seasoning','7.99','300','N','N','N','Y','9'");
    ArmsDatabase.insertIntoTable("items",
        "'CHICKEN WINGS','Starter','Classic buffalo wings with blue cheese dressing','9.99','600','N','N','N','Y','10'");
    ArmsDatabase.insertIntoTable("items",
        "'HOT HABANERO PRAWNS','Starter','Grilled prawns with a spicy habanero sauce','13.99','350','N','N','Y','N','11'");
    ArmsDatabase.insertIntoTable("items",
        "'GARLIC TORTILLA','Starter','Warm garlic tortilla served with a side of fresh salsa','5.99','250','N','N','N','Y','12'");
    ArmsDatabase.insertIntoTable("items",
        "'TACOS','Starter','Two soft tacos filled with your choice of beef or chicken','8.99','500','N','N','N','Y','13'");
    ArmsDatabase.insertIntoTable("items",
        "'CHIPS and DIPS','Starter','Freshly fried tortilla chips served with a trio of dips salsa guacamole and queso','6.99','400','N','Y','N','Y','14'");
    ArmsDatabase.insertIntoTable("items",
        "'QUESO FUNDIDO','Starter','Melted Mexican cheese served with warm tortillas for dipping','8.99','500','N','N','N','Y','15'");
    ArmsDatabase.insertIntoTable("items",
        "'SOUTHERN FRIED CHICKEN and BBQ PULLED PORK','Main','A combination of crispy fried chicken and tender BBQ pulled pork served with coleslaw and fries','15.99','1100','N','Y','N','Y','16'");
    ArmsDatabase.insertIntoTable("items",
        "'TREACLE PORK BELLY','Main','Slow-cooked pork belly glazed with a sweet treacle sauce served with roasted vegetables and mashed potatoes','18.99','850','N','N','N','Y','17'");
    ArmsDatabase.insertIntoTable("items",
        "'MEXICAN SIRLOIN','Main','Grilled sirloin steak with a spicy Mexican rub served with roasted vegetables and a side of rice and beans','22.99','800','N','N','N','Y','18'");
    ArmsDatabase.insertIntoTable("items",
        "'RIBS','Main','Slow-cooked BBQ ribs served with coleslaw and fries','19.99','1200','N','N','N','Y','19'");
    ArmsDatabase.insertIntoTable("items",
        "'STEAK and FRIES','Main','A juicy grilled steak served with a side of crispy fries','24.99','900','N','N','N','Y','20'");
    ArmsDatabase.insertIntoTable("items",
        "'SOUTHERN FRIED CHICKEN','Main','Crispy fried chicken served with coleslaw and fries','12.99','800','N','Y','N','Y','21'");
    ArmsDatabase.insertIntoTable("items",
        "'TEXAN STACK BURGER','Main','A double beef patty burger with cheddar cheese crispy bacon and onion rings served with a side of fries','16.99','1200','N','Y','N','Y','22'");
    ArmsDatabase.insertIntoTable("items",
        "'ENCHILADA','Main','Rolled tortilla stuffed with chicken beef or beans topped with enchilada sauce and melted cheese served with a side of rice and beans','14.99','650','N','N','N','Y','23'");
    ArmsDatabase.insertIntoTable("items",
        "'BURRITO','Main','A large flour tortilla filled with your choice of chicken beef or beans rice lettuce and cheese served with a side of salsa and sour cream','16.99','900','N','N','N','Y','24'");
    ArmsDatabase.insertIntoTable("items",
        "'CHIMICHANGA','Main','A deep-fried burrito filled with your choice of chicken beef or beans topped with salsa and melted cheese served with a side of rice and beans','17.99','1100','N','Y','N','Y','25'");
    ArmsDatabase.insertIntoTable("items",
        "'ULTIMATE FAJITA','Main','Sizzling strips of marinated steak or chicken breast with mixed peppers and onions served with soft tortillas guacamole sour cream and salsa','15.99','700','N','N','N','Y','26'");
    ArmsDatabase.insertIntoTable("items",
        "'PAELLA','Main','A classic Spanish dish made with saffron-infused rice chicken chorizo and seafood served with a side of bread and aioli','19.99','800','N','Y','Y','Y','27'");
    ArmsDatabase.insertIntoTable("items",
        "'BEN and JERRYS COOKIE DOUGH ICE CREAM','Desert','Vanilla ice cream with chunks of chocolate chip cookie dough','5.99','270','Y','N','N','Y','28'");
    ArmsDatabase.insertIntoTable("items",
        "'MAGNUM DOUBLE SALTED CARAMEL ICE CREAM','Desert','Salted caramel ice cream with a chocolatey coating and caramel sauce','4.99','250','Y','Y','N','Y','29'");
    ArmsDatabase.insertIntoTable("items",
        "'CHOCOLATE CHIP COOKIE DOUGH','Desert','Classic cookie dough with chocolate chips','3.99','120','Y','N','N','Y','30'");
    ArmsDatabase.insertIntoTable("items",
        "'TRIPLE CHOCOLATE BROWNIE','Desert','Fudgy chocolate brownie with chunks of dark and milk chocolate','2.99','360','Y','N','N','Y','31'");
    ArmsDatabase.insertIntoTable("items",
        "'WHITE CHOC CHIP CARAMEL COOKIE DOUGH','Desert','White chocolate chip cookie dough with caramel pieces','4.99','150','Y','N','N','Y','32'");
    ArmsDatabase.insertIntoTable("items",
        "'MOOPHORIA CHOCOLATE COOKIE DOUGH','Desert','Light ice cream with chocolate chips and cookie dough pieces','3.99','130','Y','N','N','Y','33'");
    ArmsDatabase.insertIntoTable("items",
        "'Pepsi Max','Drink','A sugar-free and low-calorie carbonated soft drink with a refreshing taste of cola','1.50','0','N','N','N','N','34'");
    ArmsDatabase.insertIntoTable("items",
        "'Robinsons Refresh Apple and Raspberry','Drink','A still fruit drink with a delicious blend of apple and raspberry flavors','2.00','50','N','N','N','N','35'");
    ArmsDatabase.insertIntoTable("items",
        "'7up Free','Drink','A clear lemon and lime-flavored carbonated soft drink without sugar','1.25','0','N','N','N','N','36'");
    ArmsDatabase.insertIntoTable("items",
        "'Pepsi Diet','Drink','A low-calorie version of Pepsi with the same great taste without sugar','1.50','0','N','N','N','N','37'");
    ArmsDatabase.insertIntoTable("items",
        "'Pepsi','Drink','A carbonated soft drink with a delicious refreshing cola taste','1.50','150','N','N','N','Y','38'");
    ArmsDatabase.insertIntoTable("items",
        "'Tango','Drink','A carbonated soft drink with a zesty and fruity orange flavor','1.50','160','N','N','N','Y','39'");
    ArmsDatabase.insertIntoTable("items",
        "'Bottled Water','Drink','A clear and refreshing still water perfect for quenching thirst','1.00','0','N','N','N','N','40'");
    ArmsDatabase.insertIntoTable("items",
        "'Pepsi Max Cherry','Drink','A sugar-free and low-calorie carbonated soft drink with a delicious cherry flavor','1.50','0','N','N','N','Y','41'");
    // ArmsDatabase.insertIntoTableFromFile("items",
    // "./PROJECT/ARMS/src/main/java/com/cs2810/Arms/Model/FoodItems.txt");
  }

  // Adding menu to database
  private static void menuAddToDb() {
    // Create menu table if not exists
    String tblName = "menu"; // table name for db
    String tblDesc = "item varchar(50) not null references items(name), "
        + "category varchar(25) references items(type), primary key(item)"; // table details
    ArmsDatabase.createTable(tblName, tblDesc); // creates table in db

    // Populate menu
    String query = "select name from items";
    for (String item : ArmsDatabase.getManyStringResult(query)) {
      Menu.addItem(new Item(item));
    }
  }

  // Adding staff info and login to database
  private static void addStaffToDb() {
    String tblName = "staff";
    String tblDesc =
        "staff_id serial unique not null, first_name varchar(30), last_name varchar(30), "
            + "username varchar(60), password varchar(255) NOT NULL, job varchar(20) NOT NULL, status varchar(20) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);

    ArmsDatabase.insertIntoTable("staff", "default,'John','Smith','johnsmith', '"
        + SignIn.getHashedPassword("aaa") + "','Waiter','online'");
    ArmsDatabase.insertIntoTable("staff", "default,'Sarah','Cameron','sarahcameron','"
        + SignIn.getHashedPassword("aaa") + "','Waiter','offline'");
    ArmsDatabase.insertIntoTable("staff", "default,'Michael','Hicks','michaelhicks','"
        + SignIn.getHashedPassword("aaa") + "','Kitchen','online'");
    ArmsDatabase.insertIntoTable("staff", "default,'Xavier','Ortiz','xavierortiz','"
        + SignIn.getHashedPassword("aaa") + "','Kitchen','offline'");
    ArmsDatabase.insertIntoTable("staff", "default,'Ethan','Cooper','ethancooper','"
        + SignIn.getHashedPassword("aaa") + "','Waiter','offline'");
  }

  // Make Basket and Table
  private static void basketAddToDb() {
    // Create baskets table if not exists
    String tblName = "baskets";
    String tblDesc = "basketNo varchar(255) NOT NULL, tableNo int NOT NULL, primary key (basketNo)";
    ArmsDatabase.createTable(tblName, tblDesc);

    // Insert new basket into basksts
    ArmsDatabase.insertIntoTable("baskets", "'78a655a1d4c041769e4a76b942b262c9','1'");
    ArmsDatabase.insertIntoTable("baskets", "'5ed9eb77d77347c386c7c8ce6a159686','2'");
    ArmsDatabase.insertIntoTable("baskets", "'a640840459184413814e0d7acf588437','3'");
    ArmsDatabase.insertIntoTable("baskets", "'1bfe069899144288aadcab374d522bbe','4'");
    ArmsDatabase.insertIntoTable("baskets", "'760e30f8adcb4e0787e1140cf8ba50ea','5'");
    ArmsDatabase.insertIntoTable("baskets", "'0099ee7ddc0346eaa35e42272f343e4a','6'");
    ArmsDatabase.insertIntoTable("baskets", "'505a4c6fe44049cfbb8243d04b9aecc6','7'");

    // Create table for basket
    tblName = "basket78a655a1d4c041769e4a76b942b262c9";
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("basket78a655a1d4c041769e4a76b942b262c9",
        "'SPICY CHICKEN STRIPS','1','9.99'");
    ArmsDatabase.insertIntoTable("basket78a655a1d4c041769e4a76b942b262c9",
        "'CHEESE and JALAPENO LOADED WEDGES', '1', '8.99'");
    ArmsDatabase.insertIntoTable("basket78a655a1d4c041769e4a76b942b262c9",
        "'TOPPED SALAD', '1', '6.99'");

    tblName = "basket5ed9eb77d77347c386c7c8ce6a159686";
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("basket5ed9eb77d77347c386c7c8ce6a159686",
        "'PINEAPPLE SALSA SEABASS', '1', '12.99'");
    ArmsDatabase.insertIntoTable("basket5ed9eb77d77347c386c7c8ce6a159686",
        "'TOPPED SALAD', '1', '6.99'");

    tblName = "basketa640840459184413814e0d7acf588437";
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("basketa640840459184413814e0d7acf588437",
        "'TREACLE PORK BELLY', '1', '18.99'");

    tblName = "basket1bfe069899144288aadcab374d522bbe";
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("basket1bfe069899144288aadcab374d522bbe",
        "'LOADED NACHOS', '1', '10.99'");
    ArmsDatabase.insertIntoTable("basket1bfe069899144288aadcab374d522bbe",
        "'HOT HABANERO PRAWNS', '1', '13.99'");
    ArmsDatabase.insertIntoTable("basket1bfe069899144288aadcab374d522bbe",
        "'GARLIC TORTILLA', '1', '5.99'");

    tblName = "basket760e30f8adcb4e0787e1140cf8ba50ea";
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("basket760e30f8adcb4e0787e1140cf8ba50ea",
        "'HOT HABANERO PRAWNS', '1', '13.99'");

    tblName = "basket0099ee7ddc0346eaa35e42272f343e4a";
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("basket0099ee7ddc0346eaa35e42272f343e4a",
        "'ENCHILADA', '1', '14.99'");

    tblName = "basket505a4c6fe44049cfbb8243d04b9aecc6";
    tblDesc = "item varchar(50) NOT NULL, qty int NOT NULL, price dec(5, 2) NOT NULL";
    ArmsDatabase.createTable(tblName, tblDesc);
    ArmsDatabase.insertIntoTable("basket505a4c6fe44049cfbb8243d04b9aecc6",
        "'TEXAN STACK BURGER', '1', '16.99'");
    ArmsDatabase.insertIntoTable("basket505a4c6fe44049cfbb8243d04b9aecc6",
        "'BURRITO', '1', '16.99'");

  }

  // Create orders table and basket details
  private static void OrdersAddToDb() {
    // Create orders table if not exists
    String tblName = "orders";
    String tblDesc = "orderNo serial unique not null, "
        + "basketNo varchar(50) NOT NULL references baskets, status varchar(10), "
        + "tableNo int NOT NULL, timeReceived datetime not null, timeDelivered datetime, "
        + "comments varchar(255), paid char(1) not null";
    ArmsDatabase.createTable(tblName, tblDesc);

    // Insert new basket into basksts
    // Table 1
    String values =
        "default,'78a655a1d4c041769e4a76b942b262c9','Received','1','2023-03-23 17:20:31',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db

    // Table 2
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Delivered','2','2023-03-23 10:55:11','2023-03-23 17:50:30',NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Ready','2','2023-03-23 10:40:31',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    // Insert new basket into basksts
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Confirmed','2','2023-03-23 10:20:25',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Received','2','2023-03-23 10:15:31',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db

    // Table 2 -order 2
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Delivered','2','2023-03-23 15:55:11','2023-03-23 17:50:30',NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Ready','2','2023-03-23 15:40:31',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    // Insert new basket into basksts
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Confirmed','2','2023-03-23 15:20:25',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    values =
        "default,'5ed9eb77d77347c386c7c8ce6a159686','Received','2','2023-03-23 15:15:31',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db

    // Table 3
    // Insert new basket into basksts
    values =
        "default,'a640840459184413814e0d7acf588437','Ready','3','2023-03-23 09:35:00',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    // Insert new basket into basksts
    values =
        "default,'a640840459184413814e0d7acf588437','Confirmed','3','2023-03-23 09:32:10',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    // Insert new basket into basksts
    values =
        "default,'a640840459184413814e0d7acf588437','Received','3','2023-03-23 09:30:00',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db

    // Table 4
    // Insert new basket into basksts
    values =
        "default,'1bfe069899144288aadcab374d522bbe','Delivered','4','2023-03-23 15:45:20','2023-03-23 15:50:30',NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    values =
        "default,'1bfe069899144288aadcab374d522bbe','Ready','4','2023-03-23 15:20:29',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    values =
        "default,'1bfe069899144288aadcab374d522bbe','Confirmed','4','2023-03-23 14:50:45',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    values =
        "default,'1bfe069899144288aadcab374d522bbe','Received','4','2023-03-23 14:45:20',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db

    // Table 5
    // Insert new basket into basksts
    values =
        "default,'760e30f8adcb4e0787e1140cf8ba50ea','Cancelled','5','2023-03-23 10:16:00',NULL,NULL,'N'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    // Insert new basket into basksts
    values =
        "default,'760e30f8adcb4e0787e1140cf8ba50ea','Received','5','2023-03-23 10:15:05',NULL,NULL,'N'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db

    // Table 6
    // Insert new basket into basksts
    values =
        "default,'0099ee7ddc0346eaa35e42272f343e4a','Cancelled','6','2023-03-23 11:00:30',NULL,NULL,'N'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db

    // Table 7
    // Insert new basket into basksts
    values =
        "default,'505a4c6fe44049cfbb8243d04b9aecc6','Ready','7','2023-03-23 16:40:17',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    // Insert new basket into basksts
    values =
        "default,'505a4c6fe44049cfbb8243d04b9aecc6','Confirmed','7','2023-03-23 16:23:15',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
    // Insert new basket into basksts
    values =
        "default,'505a4c6fe44049cfbb8243d04b9aecc6','Received','7','2023-03-23 16:20:15',NULL,NULL,'Y'";
    // order information for adding to db
    ArmsDatabase.insertIntoTable(tblName, values); // insert order to db
  }

  // Create orders table and basket details
  private static void TablesAddToDb() {
    // Create tables table if not exists
    String tblName = "tables";
    String tblDesc = "tableNo serial unique NOT NULL, avl varchar(1) NOT NULL, "
        + "waiter varchar (50) references staff.username, " + "primary key (tableNo)";
    ArmsDatabase.createTable(tblName, tblDesc);

    // Populate Tables

    // Insert new empty tables
    for (int i = 1; i <= 10; i++) {
      String values = "Default , 'Y', 'None'";
      ArmsDatabase.insertIntoTable(tblName, values);
    }

    // Assign tables
    String query = "select orderNo from orders where status = 'Received' or  status = 'Confirmed' "
        + "or status = 'Delivered'";
    for (int orderNo : ArmsDatabase.getManyIntResult(query)) {
      int tableNo = new Order(orderNo).getTableNo();
      if (tableNo <= 3) {
        Tables.assignTbl(tableNo, "johnsmith");
      }
      if (tableNo > 3) {
        Tables.assignTbl(tableNo, "sarahcameron");
      }
    }

  }

}
