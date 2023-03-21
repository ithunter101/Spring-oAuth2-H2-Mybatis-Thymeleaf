package com.matthewcasperson.auth0demo.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.matthewcasperson.auth0demo.repository.UserRepository;

@Controller
public class SignupController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/signuppage")
    public String signup(@CookieValue(name = "signup_data", defaultValue = "default") String signupData, @CookieValue(name = "existance", defaultValue = "default") String existance, Model model) {
        if (signupData != null && !signupData.equals("default")){
            model.addAttribute("email", signupData);
        }else model.addAttribute("email", null);
        if (existance.equals("already")){
            model.addAttribute("existance", "true");
        }else model.addAttribute("existance", null);
        return "signup";
    }

    @GetMapping("/signup")
    public String sign_up(HttpServletResponse response){
        Cookie cookie = new Cookie("isSignup", "true");
        cookie.setSecure(true);
        response.addCookie(cookie);
        return "redirect:/login";
    }
}
