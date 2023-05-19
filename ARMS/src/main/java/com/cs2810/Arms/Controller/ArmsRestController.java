package com.cs2810.Arms.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful controller method for web app.
 *
 * @author Eerfan Daad
 */
@RestController
@RequestMapping(path = "rest/demo")
public class ArmsRestController {
  //function for when user visits the base page
  @GetMapping("")
  public String getHello() {
    return "Hello World!";
  }

}
