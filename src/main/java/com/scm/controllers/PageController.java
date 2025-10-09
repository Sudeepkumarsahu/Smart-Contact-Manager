package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller // Controller annotation to mark this class as a controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home") // RequestMapping annotation to map HTTP GET requests to this method
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to the view
        model.addAttribute("name", "substring technologies");
        model.addAttribute("youtubechannel", "learn code with sudeep");
        model.addAttribute("google", "https://www.google.com/");

        return "home";// Replace with actual view name
    }

    // about route
    @RequestMapping("/about")
    public String aboutPage(@RequestParam(name = "param", required = false, defaultValue = "default") String param) {
        System.out.println("About page loading. Param: " + param);
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(@RequestParam(name = "param", required = false, defaultValue = "default") String param) {
        System.out.println("Services page loading. Param: " + param);
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        // default values for form fields
        // userForm.setName("sudeep");

        model.addAttribute("userForm", userForm);// Add userForm to model to be used in view
        return new String("register");
    }

    // processing register form data
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute UserForm userForm, HttpSession session) {
        System.out.println("processing registration");
        // fetch form data
        // UserForm
        System.out.println(userForm);
        // validate form data
        // sava data to database
        // // userForm se user banaya
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .about(userForm.getAbout())
        // .password(userForm.getPassword())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePicture("https://plus.unsplash.com/premium_photo-1739786996022-5ed5b56834e2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8YW5pbWF0aW9uJTIwYXZhdGFyfGVufDB8fDB8fHww")
        // .build();

      User user =  new User();
      user.setName(userForm.getName());
      user.setAbout(userForm.getAbout());
      user.setEmail(userForm.getEmail());
      user.setPassword(userForm.getPassword());
      user.setPhoneNumber(userForm.getPhoneNumber());
      user.setProfilePicture("https://plus.unsplash.com/premium_photo-1739786996022-5ed5b56834e2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8YW5pbWF0aW9uJTIwYXZhdGFyfGVufDB8fDB8fHww");
       User savedUser = userService.saveUser(user);
       System.out.println("user saved");
        // message to user about successful registration
        //add the message 
      Message message =  Message.builder().content("Registration Successful").type(MessageType.green).build();

session.setAttribute("message",message);
        // redirect to login page
        return "redirect:/register";// Redirect to registration page after successful registration 

        
    }
}
