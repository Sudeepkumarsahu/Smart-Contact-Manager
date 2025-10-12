package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null)
            return;
        System.out.println("Adding logged in user information to the model");
        String userName = Helper.getEmailOfloggedInUser(authentication);

        logger.info("user logged in : {}", userName);

        // get user from database
        User user = userService.getUserByEmail(userName);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser", user);
    }
}
