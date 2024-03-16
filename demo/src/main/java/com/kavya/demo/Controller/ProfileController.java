package com.kavya.demo.Controller;

import com.kavya.demo.Service.CustomerService;
import com.kavya.demo.model.Customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
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

        // Return the profile page
        return "profile";
    }
}
