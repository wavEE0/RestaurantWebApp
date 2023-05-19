package com.cs2810.Arms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cs2810.Arms.Model.Category;
import com.cs2810.Arms.Model.Menu;
import com.cs2810.Arms.Model.MenuFilter;


/**
 * Controller method for Menu page.
 *
 * @author Eerfan Daad & Devlin Doyle
 */
@Controller
public class ArmsMenuController {
  
  //function for when user visits the '/menu' page
  @GetMapping("/menu")
  public String showMenu(Model model) {
    
    // attributes added so the html can make java function calls using the relevant names 
    // bellow, first one 'starters' ,second 'mains' ... etc. all of these attributes are 
    // for filtering the menu
    // these attributes are for menu category's
    model.addAttribute("starters", Menu.getMenuCat(Category.Starter));
    model.addAttribute("mains", Menu.getMenuCat(Category.Main));
    model.addAttribute("deserts", Menu.getMenuCat(Category.Desert));
    model.addAttribute("drinks", Menu.getMenuCat(Category.Drink));
    // these attributes are for alergens
    model.addAttribute("check_alg_nuts", MenuFilter.isAlg_nuts());
    model.addAttribute("check_alg_crst", MenuFilter.isAlg_crst());
    model.addAttribute("check_alg_fish", MenuFilter.isAlg_fish());
    model.addAttribute("check_alg_glut", MenuFilter.isAlg_glut());
    // these atributes are for alergens
    model.addAttribute("check_cal_sub_500", MenuFilter.isCal_sub_500());
    model.addAttribute("check_cal_sub_1000", MenuFilter.isCal_sub_1000());

    return "customer/menu"; // return what html page should be showed to the user on at the '/menu' url
  }
  
  //function for when html makes function call from the '/menu' url
  @PostMapping("/menu")
  public String applyFilters(
     // paramaters for filtering the menu taken from the filter menu section on the '/menu' page
      @RequestParam(value = "alg_nuts", required = false) boolean alg_nuts,
      @RequestParam(value = "alg_crst", required = false) boolean alg_crst,
      @RequestParam(value = "alg_fish", required = false) boolean alg_fish,
      @RequestParam(value = "alg_glut", required = false) boolean alg_glut,
      @RequestParam(value = "cal_sub_500", required = false) boolean cal_sub_500,
      @RequestParam(value = "cal_sub_1000", required = false) boolean cal_sub_1000, Model model) {
    
    // setting menu filters that have been inputed by the user 
    MenuFilter.setAlg_nuts(alg_nuts);
    MenuFilter.setAlg_crst(alg_crst);
    MenuFilter.setAlg_fish(alg_fish);
    MenuFilter.setAlg_glut(alg_glut);
    MenuFilter.setCal_sub_500(cal_sub_500);
    MenuFilter.setCal_sub_1000(cal_sub_1000);
    
    // attributes added so the html can make java function calls using the relevant names 
    // bellow, first one 'starters' ,second 'mains' ... etc. all of these attributes are 
    // for filtering the menu
    // these attributes are for menu category's
    model.addAttribute("starters", Menu.getMenuCat(Category.Starter));
    model.addAttribute("mains", Menu.getMenuCat(Category.Main));
    model.addAttribute("deserts", Menu.getMenuCat(Category.Desert));
    model.addAttribute("drinks", Menu.getMenuCat(Category.Drink));
    // these attributes are for alergens
    model.addAttribute("check_alg_nuts", MenuFilter.isAlg_nuts());
    model.addAttribute("check_alg_crst", MenuFilter.isAlg_crst());
    model.addAttribute("check_alg_fish", MenuFilter.isAlg_fish());
    model.addAttribute("check_alg_glut", MenuFilter.isAlg_glut());
    // these atributes are for alergens
    model.addAttribute("check_cal_sub_500", MenuFilter.isCal_sub_500());
    model.addAttribute("check_cal_sub_1000", MenuFilter.isCal_sub_1000());

    return "customer/menu"; // return what html page should be showed to the user on at the '/menu' url
  }

}
