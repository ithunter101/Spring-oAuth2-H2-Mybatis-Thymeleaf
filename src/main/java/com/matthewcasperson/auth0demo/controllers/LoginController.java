// src/main/java/com/matthewcasperson/auth0demo/controllers/LoginController.java

package com.matthewcasperson.auth0demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/loginpage")
    public String loginPage(@CookieValue(name = "isIn", defaultValue = "yes") String isIn, @CookieValue(name = "isSignup", defaultValue = "default") String isSignup, HttpServletResponse response, Model model) {
        if (isIn != null && isIn.equals("no"))
            model.addAttribute("isExist", "no");
        if (isSignup != null && isSignup.equals("true")){
            Cookie cookie = new Cookie("isSignup", "false");
            cookie.setSecure(true);
            response.addCookie(cookie);
        }
        return "login";
    }
    @GetMapping("/login")
    public String login(HttpServletResponse response) {
        Cookie cookie = new Cookie("existance", null);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return "redirect:/oauth2/authorization/auth0";
    }
}