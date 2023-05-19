package com.cs2810.Arms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.cs2810.Arms.Model.Category;
import com.cs2810.Arms.Model.Item;
import com.cs2810.Arms.Model.SignIn;
import com.cs2810.Arms.Model.Tables;
import com.cs2810.Arms.Model.Menu;
import com.cs2810.Arms.Model.MenuFilter;
import com.cs2810.Arms.Model.Order;
import com.cs2810.Arms.Model.Waiter;

/**
 * Controller method for waiter-related api.
 *
 * @author Eerfan Daad
 */
@Controller
public class ArmsWaiterController {
  private String user;

  // function for when user visits the '/waiter' page
  @GetMapping("/waiter")
  public ModelAndView waiterPage(ModelMap modelMap, Model model) {
    // Checking if user is logged in and has privilieges
    if (user == null) {
      if (model.containsAttribute("user")) {
        user = (String) model.getAttribute("user");
      }
      if (!SignIn.getStatus(user).equals("online")) {
        System.err.println("Not logged in!");
        return new ModelAndView("redirect:/login");
      }
    }

    model.addAttribute("name", SignIn.getName(user)); // passing user's name as attribute
    model.addAttribute("readyOrders", Waiter.readyOrders(user)); // atribute for html to be able to
                                                                 // read
    // ready orders
    model.addAttribute("unconfirmedOrders", Waiter.unconfirmedOrders(user)); // atribute for html to
                                                                             // be
    // able to read unfonfirmed
    // orders
    return new ModelAndView("staff/waiter", modelMap);// return what html page should be showed to
                                                      // the user on at the '/waiter' url
  }

  @PostMapping("/waiter")
  public ModelAndView updateOrder(@RequestParam(value = "logout", required = false) boolean logout,
      @ModelAttribute("deliverOrder") String deliveredOrder,
      @ModelAttribute("confirmOrder") String confirmedOrder,
      @ModelAttribute("cancelOrder") String cancelledOrder, ModelMap modelMap, Model model) {

    if (logout) {
      SignIn.logout(user);
      user = null;
      return new ModelAndView("redirect:/login");
    }

    if (!deliveredOrder.equals("")) {
      new Order(Integer.parseInt(deliveredOrder)).deliverOrder();
    }
    if (!confirmedOrder.equals("")) {
      new Order(Integer.parseInt(confirmedOrder)).confirmOrder();
    }
    if (!cancelledOrder.equals("")) {
      new Order(Integer.parseInt(cancelledOrder)).cancelOrder("Customer changed mind");
    }
    model.addAttribute("name", SignIn.getName(user));
    model.addAttribute("readyOrders", Waiter.readyOrders(user)); // atribute for html to be able to
                                                                 // read
    // ready orders
    model.addAttribute("unconfirmedOrders", Waiter.unconfirmedOrders(user)); // atribute for html to
                                                                             // be
    // able to read unfonfirmed
    // orders
    return new ModelAndView("staff/waiter", modelMap);
  }

  @GetMapping("/waiter/edit-menu")
  public ModelAndView showEditMenu(ModelMap modelMap, Model model) {
    if (user == null) {
      if (model.containsAttribute("user")) {
        user = (String) model.getAttribute("user");
      }
      if (!SignIn.getStatus(user).equals("online")) {
        System.err.println("Not logged in!");
        return new ModelAndView("redirect:/login");
      }
    }
    MenuFilter.resetFilters();

    model.addAttribute("name", SignIn.getName(user));

    model.addAttribute("m_starters", Menu.getMenuCat(Category.Starter));
    model.addAttribute("m_mains", Menu.getMenuCat(Category.Main));
    model.addAttribute("m_deserts", Menu.getMenuCat(Category.Desert));
    model.addAttribute("m_drinks", Menu.getMenuCat(Category.Drink));

    model.addAttribute("nm_starters", Menu.getNonMenuCat(Category.Starter));
    model.addAttribute("nm_mains", Menu.getNonMenuCat(Category.Main));
    model.addAttribute("nm_deserts", Menu.getNonMenuCat(Category.Desert));
    model.addAttribute("nm_drinks", Menu.getNonMenuCat(Category.Drink));
    return new ModelAndView("staff/edit-menu", modelMap);
  }

  @PostMapping("/waiter/edit-menu")
  public ModelAndView changeMenu(@RequestParam(value = "logout", required = false) boolean logout,
      @ModelAttribute("removeItem") String removedItem, @ModelAttribute("addItem") String addedItem,
      ModelMap modelMap, Model model) {
    MenuFilter.resetFilters();

    if (logout) {
      SignIn.logout(user);
      user = null;
      return new ModelAndView("redirect:/login");
    }

    if (!removedItem.equals("")) {
      Menu.removeItem(new Item(removedItem));
    }
    if (!addedItem.equals("")) {
      Menu.addItem(new Item(addedItem));
    }

    model.addAttribute("name", SignIn.getName(user));

    model.addAttribute("m_starters", Menu.getMenuCat(Category.Starter));
    model.addAttribute("m_mains", Menu.getMenuCat(Category.Main));
    model.addAttribute("m_deserts", Menu.getMenuCat(Category.Desert));
    model.addAttribute("m_drinks", Menu.getMenuCat(Category.Drink));

    model.addAttribute("nm_starters", Menu.getNonMenuCat(Category.Starter));
    model.addAttribute("nm_mains", Menu.getNonMenuCat(Category.Main));
    model.addAttribute("nm_deserts", Menu.getNonMenuCat(Category.Desert));
    model.addAttribute("nm_drinks", Menu.getNonMenuCat(Category.Drink));
    return new ModelAndView("staff/edit-menu", modelMap);
  }

  @GetMapping("/waiter/tables")
  public ModelAndView tablePage(ModelMap modelMap, Model model) {
    // Checking if user is logged in and has privilieges
    if (user == null) {
      if (model.containsAttribute("user")) {
        user = (String) model.getAttribute("user");
      }
      if (!SignIn.getStatus(user).equals("online")) {
        System.err.println("Not logged in!");
        return new ModelAndView("redirect:/login");
      }
    }

    model.addAttribute("name", SignIn.getName(user));


    model.addAttribute("avl_tbls", Tables.getAllAvl());
    model.addAttribute("asg_tbls", Tables.getAssignedTbls(user));

    return new ModelAndView("staff/tables", modelMap);
  }

  @PostMapping("/waiter/tables")
  public ModelAndView changeTable(@RequestParam(value = "logout", required = false) boolean logout,
      @ModelAttribute("assign_tbl") String assignTbl, @ModelAttribute("clear_tbl") String clearTbl,
      ModelMap modelMap, Model model) {
    if (logout) {
      SignIn.logout(user);
      user = null;
      return new ModelAndView("redirect:/login");
    }

    if (!clearTbl.equals("")) {
      Tables.clearTbl(Integer.parseInt(clearTbl));
    }
    if (!assignTbl.equals("")) {
      Tables.assignTbl(Integer.parseInt(assignTbl), user);
    }

    model.addAttribute("name", SignIn.getName(user));

    model.addAttribute("avl_tbls", Tables.getAllAvl());
    model.addAttribute("asg_tbls", Tables.getAssignedTbls(user));

    return new ModelAndView("staff/tables", modelMap);

  }
}
