package com.scm.controllers;

import static java.lang.System.err;
import static java.lang.System.out;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    private ContactService contactService;
    private UserService userService;
    private ImageService imageService;

    public ContactController(ContactService contactService, UserService userService, ImageService imageService) {
        this.contactService = contactService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @RequestMapping("/add")
    // add contact page: handler
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        // contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {
        // process the form data

        // validate form
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> logger.info(err.toString()));
            session.setAttribute("message", Message.builder()
                    .content("please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfloggedInUser(authentication);
        // form -> contact
        User user = userService.getUserByEmail(username);

        // process the contact picure

        // image process

        // file upload code
        String fileName = UUID.randomUUID().toString();
        String fileURL = imageService.uploadImage(contactForm.getContactImage(), fileName);
        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename());

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(fileName);

        contactService.save(contact);
        out.println(contactForm);

        // set the contact picture url

        // set message to be displayed on the view
        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build()

        );

        return "redirect:/user/contacts/add";
    }

    // view contacts
    @RequestMapping
    public String viewContacts(Model model, Authentication authentication) {

        // load all the user contacts

        String userName = Helper.getEmailOfloggedInUser(authentication);
        User user = userService.getUserByEmail(userName);

        List<Contact> contacts = contactService.getByUser(user);
        model.addAttribute("contacts", contacts);
        return "user/contacts";
    }
}