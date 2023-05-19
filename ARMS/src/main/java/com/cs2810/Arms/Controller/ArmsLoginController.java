package com.cs2810.Arms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cs2810.Arms.Model.SignIn;

/**
 * Controller method for Login page.
 *
 * @author Eerfan Daad
 */
@Controller
public class ArmsLoginController {

  // function for when user visits the '/login' page
  @GetMapping("/login")
  public String showLogin() {
    return "customer/login"; // returns which html page to redirect user to 
  }

  //function for when html makes function call from the '/login' url
  @PostMapping("/login")
  public ModelAndView Login(RedirectAttributes redirectAttrs, ModelMap model, String username,
      String password) {
    if (SignIn.checkLogin(username, password)) { // check if login details are correct
      String job = SignIn.checkJob(username); //checks active job

      redirectAttrs.addFlashAttribute("user", username); // adds user name as attribute 

      if (job.equals("Waiter")) {
        return new ModelAndView("redirect:/waiter", model); // if there job is as a waiter it sends them to the waiter page
      }

      if (job.equals("Kitchen")) {
        return new ModelAndView("redirect:/kitchen-staff", model); // if there job is kitchen staff it sends them to the kitchen-staff page
      }
    }
    model.addAttribute("fail", true);
    return new ModelAndView("customer/login", model); // if there job is customer it sends them to the customer page
  }

}
