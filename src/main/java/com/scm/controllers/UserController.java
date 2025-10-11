package com.scm.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.helpers.Helper;

@Controller
@RequestMapping("/user")

public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    // user dashboard page
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Authentication authentication) {
String userName = Helper.getEmailOfloggedInUser(authentication);
        logger.info("user logged in : {}",userName);
        return "user/profile";
    }
    // user add contact page
    // user view page
    // user edit contact
    // user delete contact
    // user search contact
}
