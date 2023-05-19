package com.cs2810.Arms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cs2810.Arms.Model.Basket;
import com.cs2810.Arms.Model.Category;
import com.cs2810.Arms.Model.Item;
import com.cs2810.Arms.Model.Menu;
import com.cs2810.Arms.Model.MenuFilter;
import com.cs2810.Arms.Model.Order;
import com.cs2810.Arms.Model.Tables;


/**
 * Controller method for Order page.
 *
 * @author Devlin Doyle & Eerfan Daad
 */
@Controller
public class ArmsOrderController {
  private Basket basket; //basket item to keep track of items in a users basket
  private Order order; // order to keep track of items in a finalised order
  private boolean hideTblSelector; // variable to decide weather to show table selector
  
  //function for when user visits the '/order' page
  @GetMapping("/order")
  public String showOrder(Model model) {
    model.addAttribute("tables", Tables.getAllUA());
    
    MenuFilter.resetFilters(); // resets the menu filters
    
    hideTblSelector = false;  // set the table selector to not be hidden
    model.addAttribute("hideTblSelector", hideTblSelector);
    
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

    return "customer/order";  // return what html page should be showed to the user on at the '/order' url
  }
  
  //function for when html makes function call from the '/order' url
  @PostMapping("/order")
  public String applyFilters(
   // paramaters for filtering the menu taken from the filter menu section on the '/order' page
      @RequestParam(value = "backFromCheckout", required = false) boolean backFromCheckout,
      @RequestParam(value = "alg_nuts", required = false) boolean alg_nuts,
      @RequestParam(value = "alg_crst", required = false) boolean alg_crst,
      @RequestParam(value = "alg_fish", required = false) boolean alg_fish,
      @RequestParam(value = "alg_glut", required = false) boolean alg_glut,
      @RequestParam(value = "cal_sub_500", required = false) boolean cal_sub_500,
      @RequestParam(value = "cal_sub_1000", required = false) boolean cal_sub_1000,
      @ModelAttribute("tblNo") String tblNo, @ModelAttribute("item") String item,
      @ModelAttribute("qty") String qty, Model model) {
    
    model.addAttribute("tables", Tables.getAllUA());

    if (backFromCheckout) {
      hideTblSelector = true; // if you are retruning from checkout dont show table selector
    }

    if (!hideTblSelector) { // if table selector is ment to be shown do the following
      if (!tblNo.equals("")) { // if there is no table number set
        basket = new Basket(Integer.parseInt(tblNo)); // new basket with the inputed table number
        hideTblSelector = true; // hide table selector
      } else {
        // attribute for hiding table selector
        model.addAttribute("hideTblSelector", hideTblSelector);
        // attributes for menu filtering
        model.addAttribute("starters", Menu.getMenuCat(Category.Starter));
        model.addAttribute("mains", Menu.getMenuCat(Category.Main));
        model.addAttribute("deserts", Menu.getMenuCat(Category.Desert));
        model.addAttribute("drinks", Menu.getMenuCat(Category.Drink));
        return "customer/order"; // return what html page should be showed to the user on at the '/order' url
      }
    }

    
    if (!item.equals("")) {
      basket.addItem(new Item(item), Integer.parseInt(qty)); // if the item isnt null add it to the basket
    }
    
    // attributes for the html page to be able to interact with the basket object
    model.addAttribute("basket", basket); // for general basket interactions
    model.addAttribute("tblNo", basket.getTableNo()); // for getting table number for the basket
    model.addAttribute("basketItems", basket.getItemList()); // for getting amount of items in basket
    model.addAttribute("basketTotal", basket.getTotalPrice()); // for getting total price of items in basket
    model.addAttribute("basketEmpty", basket.getItemList().isEmpty()); // for checking if a basket if empty
    model.addAttribute("hideTblSelector", hideTblSelector); // for checking if the table selector should be shown
    
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

    return "customer/order"; // return what html page should be showed to the user on at the '/order' url
  }

  //function for when html makes function call from the '/order/checkout' url
  @PostMapping("order/checkout")
  public String showBasketCheckout(@ModelAttribute("updateItem") String updatedItem,
      @ModelAttribute("qty") String qty, @ModelAttribute("removeItem") String removedItem,
      @ModelAttribute("payInfo") String payInfo, Model model) {
    
    if (basket != null) { // if there is a basket

      if (!updatedItem.equals("") && !qty.equals("")) {
        basket.updateItemQty(new Item(updatedItem), Integer.parseInt(qty)); // if there is an item that has an updated quantity , its quantity in the basket it updated
      }

      if (!removedItem.equals("")) {
        basket.removeItem(new Item(removedItem)); // if there is an item to be removed it is removed from the basket
      }

      if (payInfo.equals("complete")) { // if payment has been completed
        order = new Order(basket);

        model.addAttribute("order", order); // atribute so the order information can be showed on the order confirmation page
        return "customer/order-confirmation"; // return the html page that shows the the order has been confirmed
      }
      
      // atributes so the checkout page can access basket information
      model.addAttribute("basket", basket);
      model.addAttribute("tblNo", basket.getTableNo());
      model.addAttribute("basketItems", basket.getItemList());
      model.addAttribute("basketTotal", basket.getTotalPrice());
      model.addAttribute("basketEmpty", basket.getItemList().isEmpty());
    }
    return "customer/checkout"; // return what html page should be showed to the user on at the '/checkout' url
  }
  
  //function for when user visits the '/order/checkout' page
  @GetMapping("order/checkout")
  public String showCheckout(Model model) {

    basket = new Basket(5); // makes a new basket
    basket.addItem(new Item("Chips"), 1);
    basket.addItem(new Item("Hot Wings"), 3);
    basket.addItem(new Item("Coca-Cola"), 2);
    
    // atributes so the checkout page can access basket information
    model.addAttribute("basket", basket);
    model.addAttribute("tblNo", basket.getTableNo());
    model.addAttribute("basketItems", basket.getItemList());
    model.addAttribute("basketTotal", basket.getTotalPrice());
    model.addAttribute("basketEmpty", basket.getItemList().isEmpty());
    return "customer/checkout"; // return what html page should be showed to the user on at the '/checkout' url
  }

}
