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


@Controller
public class MessageController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @GetMapping("/send_message")
    String sendMessage(Model model) {
        model.addAttribute("sendMessage", new SendMessage());
        return "send_message";
    }

    @PostMapping("/send_message")
    String sendMessage(Authentication authentication, Model model, @ModelAttribute SendMessage recipient) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userFrom = principal.getUser();
        Message message = messageService.sendMessage(userFrom, recipient);
        model.addAttribute("sendMessage", new SendMessage());
        return "send_message";
    }

    @RequestMapping(value = "/get_last_message",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SendMessage getLastMessage(Authentication authentication, @RequestParam("idOfPreviousMessage") Long idOfPreviousMessage){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userRecipient = principal.getUser();
        Message message = messageService.getLastUnreadMessage(userRecipient);
        if (message == null || message.getId().equals(idOfPreviousMessage)){
            return null;
        }
        return new SendMessage(message);
    }
}
