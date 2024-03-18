package com.kavya.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kavya.demo.Service.CatererService;
import com.kavya.demo.Service.CustomerService;
import com.kavya.demo.model.Caterer;
import com.kavya.demo.model.Customer;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CatererService catererService;

    @GetMapping("/profile")
    public String viewCustomerProfile(HttpSession session, Model model) {
        // Check if the user is logged in
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            // Redirect to login if not logged in
            return "redirect:/login";
        }

        // Retrieve the customer from the database based on the email
        Customer customer = customerService.getUserByEmail(userEmail);
        if (customer == null) {
            // Handle scenario if user not found
            // You can redirect to login with an error message
            return "redirect:/login";
        }

        // Add the customer details to the model
        model.addAttribute("customer", customer);

        // Return the customer profile page
        return "customer-profile";
    }

    @GetMapping("/caterer/profile")
    public String viewCatererProfile(HttpSession session, Model model) {
        // Check if the user is logged in
        String catererEmail = (String) session.getAttribute("catererEmail");
        System.out.println(catererEmail);
        // if (catererEmail == null) {
        //     // Redirect to login if not logged in
        //     return "redirect:/login";
        // }
    
        // Retrieve the caterer from the database based on the email
        Caterer caterer = catererService.getCatererByEmail(catererEmail);
        System.out.println(caterer);
        if (caterer == null) {
            // Handle scenario if caterer not found
            // You can redirect to login with an error message
            return "redirect:/login";
        }
    
        // Add the caterer details to the model
        model.addAttribute("caterer", caterer);
    
        // Return the caterer profile page
        return "caterer-profile";
    }
    
    
}
