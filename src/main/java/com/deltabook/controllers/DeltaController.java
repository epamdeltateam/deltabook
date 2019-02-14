package com.deltabook.controllers;

import com.deltabook.model.*;
import com.deltabook.repositories.ContactRepository;
import com.deltabook.repositories.MessageRepository;
import com.deltabook.repositories.UserRepository;
import com.deltabook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.Base64;

@Controller
public class DeltaController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MessageRepository messageRepository;

    private User currentUser;

    @RequestMapping("/")
    public String MainPage(Model model) {
        model.addAttribute("objectToFill_auth", new User());
        return "main";
    }

    @RequestMapping(value = "/enter_auth", method = RequestMethod.POST)
    String auth(@ModelAttribute("UserObj") User insertedObject, Model model) {
        model.addAttribute("objectToFill_auth", new User());
        User user = userService.getUserByLogin(insertedObject.getLogin());
        if (user != null && userService.checkPassword(insertedObject)) {
            currentUser = user;
            return "user_panel";
        }
        return "error_auth";

    }

    @RequestMapping(value = "/user_panel")
    ModelAndView user_panel(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        String image_string;
        image_string = Base64.getEncoder().encodeToString(currentUser.getPicture());
        modelAndView.addObject("image", image_string);
        modelAndView.addObject("name", currentUser.getFirstName());
        modelAndView.addObject("surname", currentUser.getLastName());
        modelAndView.setViewName("user_panel");
        return modelAndView;
    }

    @RequestMapping(value = "/exit_user_panel")
    String exit_user_panel(Model model) {
        currentUser = null;
        model.addAttribute("objectToFill_auth", new User());
        return "main";
    }

    @RequestMapping(value = "/send_message")
    String send_message(Model model) {
        model.addAttribute("recipient", new SendMessage());
        return "send_message";
    }

    @RequestMapping(value = "/add_user")
    String add_user(Model model) {
        model.addAttribute("sendFriendRequest", new SendFriendRequest());
        return "add_user";
    }

    @RequestMapping(value = "/enter_add_friend", method = RequestMethod.POST)
    String enter_add_friend(Model model, @ModelAttribute SendFriendRequest send_req) {

        User correct_user_to = userRepository.findUserByLogin(send_req.getFriendNickname());
        contactRepository.save(new Contact(currentUser, correct_user_to,send_req.getRequestMessage() ));
        return "user_panel";
    }

    @RequestMapping(value = "/enter_message_data")
    String send_message(Model model, @ModelAttribute SendMessage recipient) {
        User correct_recipient = userRepository.findUserByLogin(recipient.getNickanme());

        System.out.println(recipient.getBody());
        messageRepository.save(new Message(currentUser, correct_recipient, recipient.getBody()));
        return "user_panel";
    }
  
    @RequestMapping("/upload_avatar")
    public String UploadPage(Model model) {
        model.addAttribute("msg", "Waiting for upload ");
        return "upload_avatar";
    }

    @RequestMapping("/upload")
    public String upload(Model model, @RequestParam("files") MultipartFile file) throws Exception {
        currentUser.setPicture(file.getBytes());
        userRepository.saveAndFlush(currentUser);
        model.addAttribute("msg", "Successfully uploaded files ");
        return "upload_avatar";
    }
}
