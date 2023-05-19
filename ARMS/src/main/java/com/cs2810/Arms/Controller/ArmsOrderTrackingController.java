package com.cs2810.Arms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.cs2810.Arms.Model.ArmsDatabase;
import com.cs2810.Arms.Model.Order;

/**
 * Controller method for kitchen staff page.
 *
 * @author Eerfan Daad
 */
@Controller
public class ArmsOrderTrackingController {
  
  //function for when user visits the '/order-tracking' page
  @GetMapping("/order-tracking")
  public String showPage() {
    return "customer/order-tracking"; // return what html page should be showed to the user on at the '/order-tracking' url
  }
  
  //function for when html makes function call from the '/order-tracking' url
  @PostMapping("/order-tracking")
  public String updateReadyOrder(@ModelAttribute("orderNo") String orderNo, Model model) {
    if (orderNo != null) { // if there is an order number
      model.addAttribute("tracked", true); // sets attribute called tracked to value of true
      String query = "select status from orders where orderNo = '" + orderNo + "';"; // query to find order numbers
      if (ArmsDatabase.getStringResult(query) != "") { // if querying the database is empty 
        model.addAttribute("order", new Order(Integer.parseInt(orderNo))); // add attribute of a new order with the given order number
      } else {
        model.addAttribute("order", null); // if database query is empty add attribute of null 
      }
    } else {
      model.addAttribute("tracked", false); // if there is no order number add attribute called tracked set to false
    }
    return "customer/order-tracking";// return what html page should be showed to the user on at the '/order-tracking' url
  }
}
