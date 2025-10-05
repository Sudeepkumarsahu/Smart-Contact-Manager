package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // Controller annotation to mark this class as a controller
public class PageController {

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

}
