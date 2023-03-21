// src/main/java/com/matthewcasperson/auth0demo/controllers/HomeController.java

package com.matthewcasperson.auth0demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.matthewcasperson.auth0demo.model.Users;
import com.matthewcasperson.auth0demo.repository.UserRepository;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String main(@CookieValue(name="isSignup", defaultValue = "default") String isSignup,
    @CookieValue(name="existance", defaultValue = "default") String data) {
        if (isSignup.equals("true") || data.equals("already")){
            return "redirect:/logout";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpServletResponse response, Model model, @CookieValue(name = "isSignup", defaultValue = "default") String isSignup,
    @CookieValue(name="existance", defaultValue = "default") String eData){
        if (eData.equals("already")) return "redirect:/logout";
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication.isAuthenticated()){
            OAuth2AuthenticationToken oauthToken =  
                    (OAuth2AuthenticationToken) authentication;
            if (isSignup != null && isSignup.equals("true")){
                Cookie cookie = new Cookie("signup_data", oauthToken.getPrincipal().getAttribute("email"));
                cookie.setSecure(true);
                response.addCookie(cookie);
                return "redirect:/signuppage";
            }
            String userName = userRepository.findByEmail(oauthToken.getPrincipal().getAttribute("email"));
            if (userName != null) {
                Cookie cookie = new Cookie("signup_data", null);
                cookie.setSecure(true);
                response.addCookie(cookie);
                model.addAttribute("user", oauthToken.getPrincipal().getAttributes());
                model.addAttribute("username", userName);
                return "home";
            }
            else {
                Cookie cookie = new Cookie("isIn", "no");
                cookie.setSecure(true);
                response.addCookie(cookie);
                return "redirect:/logout";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/out")
    public String logout(@CookieValue(name = "isIn", defaultValue = "yes") String isIn){
        if (isIn.equals("no")){
            return "redirect:/loginpage";
        }
        return "redirect:/";
    }
}