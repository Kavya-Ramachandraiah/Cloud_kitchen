package com.kavya.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.kavya.demo.Service.CatererService;
import com.kavya.demo.model.Caterer;

import jakarta.servlet.http.HttpSession;

@Controller
public class CatererController {

    @Autowired
    private CatererService catererService;

    @PostMapping("/catererSignup")
    public String createCaterer(@ModelAttribute("caterer") Caterer caterer, Model model) {
        // Create the caterer using the service method
        catererService.createCaterer(caterer);

        // Add success message
        String registrationSuccessMessage = "Caterer registration successful. Please login.";
        model.addAttribute("successMessage", registrationSuccessMessage);

        // Redirect to the login page
        return "caterer_login";
    }

    @GetMapping("/catererSignup")
    public String showCatererSignupForm(Model model) {
        model.addAttribute("caterer", new Caterer());
        return "caterer_signup";
    }

    @GetMapping("/catererLogin")
    public String showLoginForm() {
        return "caterer_login";
    }

    @GetMapping("/catererWelcome")
    public String showWelcomePage() {
        return "caterer_welcome";
    }

    @GetMapping("/catererLogout")
    public String logout(HttpSession session) {
        // Invalidate session and logout
        session.invalidate();
        return "redirect:/catererLogin";
    }

    @PostMapping("/catererLogin")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if (catererService.isValidCaterer(email, password)) {
            // Generate and store token in session
            String token = catererService.generateToken(email);
            session.setAttribute("catererEmail", email);
            session.setAttribute("token", token);
            return "caterer_welcome";
        } else {
            String loginFailed = "Invalid credentials";
            model.addAttribute("errorMessage", loginFailed);
            return "caterer_login";
        }
    }

    // Add more methods as needed

}
