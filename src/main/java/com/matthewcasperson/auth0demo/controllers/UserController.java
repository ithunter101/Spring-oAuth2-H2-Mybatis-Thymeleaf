package com.matthewcasperson.auth0demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.matthewcasperson.auth0demo.model.Users;
import com.matthewcasperson.auth0demo.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signup_user(@ModelAttribute Users users, HttpServletResponse response) {
        Cookie cookie = new Cookie("isSignup", "false");
        cookie.setSecure(true);
        response.addCookie(cookie);
        if (userRepository.findByEmail(users.getEmail()) != null) {
            Cookie cookie2 = new Cookie("existance", "already");
            cookie2.setSecure(true);
            response.addCookie(cookie2);
            return "redirect:/signuppage";
        }
        userRepository.insert(users.getUserName(), users.getEmail());
        return "redirect:/home";
    }
}
