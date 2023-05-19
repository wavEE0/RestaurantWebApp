package com.cs2810.Arms.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cs2810.Arms.Model.Basket;
import com.cs2810.Arms.Model.Order;

/**
 * Controller method for web app to route .
 *
 * @author Eerfan Daad
 */
@Controller
public class ArmsController {
  
  //function for when user visits the '/demo' page
  @GetMapping("/demo")
  public String showCreateForm(Model model) {
    List<Order> orderLog = new ArrayList<Order>();
    for (int i = 1; i <= 3; i++) { // iterates through adding menu items to a basket
      orderLog.add(new Order(new Basket(1)));
    }

    model.addAttribute("orderLog", orderLog); // attribute added so the html can make java function calls using the 'orderLog' name
    return "demo"; // return what html page should be showed to the user on at the '/demo' url
  }
  
  //function for when user visits the '/index' page
  @GetMapping({"/", "/index"})
  public String showHome() {
    return "customer/index"; // return what html page should be showed to the user on at the '/index' url
  }
  
  //function for when user visits the '/about-us' page
  @GetMapping("/about-us")
  public String showAboutUs() {
    return "customer/about-us"; // return what html page should be showed to the user on at the '/about-us' url
  }
  
  //function for when user visits the '/basket' page
  @GetMapping("/basket")
  public String showBasket() {
    return "customer/basket"; // return what html page should be showed to the user on at the '/basket' url
  }
  
}
