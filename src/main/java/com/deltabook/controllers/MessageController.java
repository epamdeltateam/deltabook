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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


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

    @GetMapping("/dialogs")
    String dialogs(Authentication authentication, Model model) {
        List<User> sendersList = new ArrayList<User>();
        List<Message> messageList = new ArrayList<Message>();
        List<List<Message>> dialogsList = new ArrayList<List<Message>>();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userRecipient = principal.getUser();
        User userSender = null;
        sendersList = messageService.getAllChatCompanionsOfUser(userRecipient);
        for (User user : sendersList) {
            userSender = user;
            messageList = messageService.getDialog(userRecipient,userSender );
            dialogsList.add(messageList);
        }
        model.addAttribute("dialogsList", dialogsList);
        return "dialogs";
    }

    @RequestMapping(value = "dialog/{recipient}/{sender}", method=RequestMethod.GET)
    public String generateDialog(@PathVariable String recipient, @PathVariable String sender, Authentication authentication, Model model) {
        Model model_generated = messageService.generatedDialogBetweenUsers( recipient, sender,authentication, model);
        return "dialog_between_users";
    }

    @PostMapping("/send_message_in_dialog")
    ModelAndView sendMessageInDialog(Authentication authentication, @ModelAttribute SendMessage recipient) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userFrom = principal.getUser();
        Message message = messageService.sendMessage(userFrom, recipient);
        String url = "redirect:/dialog/";
        url += recipient.getNickName();
        url += "/";
        url += userFrom.getLogin();
        ModelAndView modelAndView = new ModelAndView(url);
        modelAndView.addObject("sendMessage", new SendMessage());
        return modelAndView;
    }
    @RequestMapping(value = "/get_updated_dialog",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<SendMessage> getUpdatedDialog(Authentication authentication, @RequestParam("senderLogin") String  senderLogin, @RequestParam("recipientLogin") String  recipientLogin, Model model){
        List<Message> messageList = messageService.UpdatedDialogBetweenUsers( recipientLogin, senderLogin,authentication, model);
        model.addAttribute("messageList",messageList);
        List<SendMessage> sendMessageList = new ArrayList<SendMessage>();
        for (Message msg : messageList) {
            sendMessageList.add(new SendMessage(msg));
        }
        return sendMessageList;
    }
}
