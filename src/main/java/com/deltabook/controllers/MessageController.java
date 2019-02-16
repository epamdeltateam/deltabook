package com.deltabook.controllers;

import com.deltabook.model.Message;
import com.deltabook.model.User;
import com.deltabook.model.send.SendMessage;
import com.deltabook.security.details.UserDetailsImpl;
import com.deltabook.services.MessageService;
import com.deltabook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @GetMapping("/send_message")
    String send_message(Model model) {
        model.addAttribute("recipient", new SendMessage());
        return "send_message";
    }

    @PostMapping("/send_message")
    String send_message(Authentication authentication, Model model, @ModelAttribute SendMessage recipient) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userFrom = principal.getUser();
        Message message = messageService.sendMessage(userFrom, recipient);
        return "main";
    }
    @GetMapping(value = "/message_for_current_user", produces = {"text/html; charset-UTF-8"})
    public @ResponseBody
    String messageForCurrentUser(Authentication authentication) {
        if(authentication == null) return "";
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userRecipient = principal.getUser();
        Message message = messageService.findByRecipientIDOrderByCreatedAt(userRecipient);
        if(message == null)
            return "";
        else
            return "<p> New message is " + message.getBody() + "</p>" + "<p> Sender is " + message.getSenderID().getLogin() + "</p>" ;
        }
    }
