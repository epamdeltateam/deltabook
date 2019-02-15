package com.deltabook.controllers;

import com.deltabook.model.*;
import com.deltabook.model.send.SendFriendRequest;
import com.deltabook.model.send.SendMessage;
import com.deltabook.repositories.ContactRepository;
import com.deltabook.repositories.MessageRepository;
import com.deltabook.repositories.UserRepository;
import com.deltabook.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.deltabook.services.*;
import java.util.Base64;

@Controller
public class MainController {

    @Autowired
    private UserServiceImpl UserServiceImpl;

    @Autowired
    private ContactServiceImpl ContactServiceImpl;

    @Autowired
    private MessageServiceImpl MessageServiceImpl;


    @RequestMapping("/")
    public ModelAndView MainPage(Authentication authentication, Model model) {
        model.addAttribute("objectToFill_auth", new User());
        ModelAndView modelAndView = new ModelAndView();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        if(user.isBlocked() == true || user.isDeleted() == true ) {
            modelAndView.setViewName("login");
            return modelAndView;
        }
        String image_string;
        if (user.getPicture() != null){
            image_string = Base64.getEncoder().encodeToString(user.getPicture());
            modelAndView.addObject("image", image_string);
        }
        modelAndView.addObject("name", user.getFirstName());
        modelAndView.addObject("surname", user.getLastName());
        switch(user.getRole()) {
            case ROLE_USER: { modelAndView.addObject("role", false); break; }
            case ROLE_ADMIN: { modelAndView.addObject("role", true); break; }
        }
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(value = "/send_message")
    String SendMessage(Model model) {
        model.addAttribute("recipient", new SendMessage());
        return "send_message";
    }

    @RequestMapping(value = "/add_user")
    String AddUser(Model model) {
        model.addAttribute("sendFriendRequest", new SendFriendRequest());
        return "add_user";
    }

    @RequestMapping(value = "/enter_add_friend", method = RequestMethod.POST)
    String SaveAddFriend(Authentication authentication, Model model, @ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        User correct_user_to = UserServiceImpl.getUserByLogin(send_req.getFriendNickname());
        ContactServiceImpl.SaveContact(new Contact(user, correct_user_to,send_req.getRequestMessage() ));
        return "main";
    }

    @RequestMapping(value = "/enter_message_data")
    String SavedMessageData(Authentication authentication, Model model, @ModelAttribute SendMessage recipient) {
        User correct_recipient = UserServiceImpl.getUserByLogin(recipient.getNickanme());
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        System.out.println(recipient.getBody());
        MessageServiceImpl.SaveMessage(new Message(user, correct_recipient, recipient.getBody()));
        return "main";
    }
  
    @RequestMapping("/upload_avatar")
    public String UploadAvatar(Model model) {
        model.addAttribute("msg", "Waiting for upload ");
        return "upload_avatar";
    }

    @RequestMapping("/upload")
    public String UploadAvatarSave(Authentication authentication,Model model, @RequestParam("files") MultipartFile file) throws Exception {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        user.setPicture(file.getBytes());
        UserServiceImpl.SaveAndFlushUser(user);
        model.addAttribute("msg", "Successfully uploaded files ");
        return "upload_avatar";
    }
}
