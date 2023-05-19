package com.cs2810.Arms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.cs2810.Arms.Model.KitchenStaff;
import com.cs2810.Arms.Model.SignIn;
import com.cs2810.Arms.Model.Order;

/**
 * Controller method for kitchen staff page.
 *
 * @author Eerfan Daad
 */
@Controller
public class ArmsKitchenController {
  private String user;

  // function for when user visits the '/kitchen-staff' page
  @GetMapping("/kitchen-staff")
  public ModelAndView showConfirmedOrders(ModelMap modelMap, Model model) {
    if (user == null) { // if there is no loged in user
      if (model.containsAttribute("user")) { // if there is already a user asosiated with the model
        user = (String) model.getAttribute("user"); //sets user attribute to model
      }
      if (!SignIn.getStatus(user).equals("online")) { // if user is NOT online
        System.err.println("Not logged in!");
        return new ModelAndView("redirect:/login"); //returns to login page
      }
    }

    model.addAttribute("name", SignIn.getName(user));
    model.addAttribute("confirmedOrders", KitchenStaff.confirmedOrders()); // adds the confirmed orders as an atribute so the html can read them 
    return new ModelAndView("staff/kitchen-staff", modelMap); // returns which html page to redirect user to 
  }

  // function for when html makes function call from the '/kitchen-staff' url
  @PostMapping("/kitchen-staff")
  public ModelAndView updateReadyOrder(@ModelAttribute("readyOrder") String orderNo,
      @RequestParam(value = "logout", required = false) boolean logout, ModelMap modelMap,
      Model model) {
    if (logout) { // if user is wanting to be logged out
      SignIn.logout(user); // sign out user
      user = null; // set user to null as there is no user signed in anymore
      return new ModelAndView("redirect:/login"); // returns to the login page so they can login 
    }

    if (!orderNo.equals("")) {
      new Order(Integer.parseInt(orderNo)).readyOrder();
    }

    model.addAttribute("name", SignIn.getName(user));
    model.addAttribute("confirmedOrders", KitchenStaff.confirmedOrders()); // adds the confirmed orders as an atribute so the html can read them 
    return new ModelAndView("staff/kitchen-staff", modelMap);  // returns which html page to redirect user to 
  } 
}
