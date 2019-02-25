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
import java.util.Base64;
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
        List<Message> messageList = new ArrayList<Message>();
        User userRecipient = userService.getUserByLogin(recipient);
        User userSender = userService.getUserByLogin(sender);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String principalLogin = principal.getUser().getLogin();
        messageList = messageService.generatedDialogBetweenUsers( userRecipient, userSender, principalLogin);
        model.addAttribute("messageList", messageList);
        model.addAttribute("recipientLogin", userRecipient.getLogin());
        model.addAttribute("senderLogin", userSender.getLogin());
        String recipientPic = "", senderPic = " ";
        if (userRecipient.getPicture() != null){
            recipientPic = Base64.getEncoder().encodeToString(userRecipient.getPicture());
        }
        if (userSender.getPicture() != null){
            senderPic = Base64.getEncoder().encodeToString(userSender.getPicture());
        }
        model.addAttribute("recipientPic", recipientPic);
        model.addAttribute("senderPic", senderPic);
        model.addAttribute("sendMessage", new SendMessage());
        return "dialog_between_users";
    }
    @RequestMapping(value = "/get_updated_dialog",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<SendMessage> getUpdatedDialog(Authentication authentication, @RequestParam(value="senderLogin", required=false) String  senderLogin, @RequestParam(value="recipientLogin", required=false) String  recipientLogin, Model model, @RequestParam(value="body", required=false) String body ){
        if(body != null && senderLogin != null && recipientLogin != null ) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setBody(body);
            UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
            User sender = principal.getUser();
            User recipient = userService.getUserByLogin(recipientLogin);
            if(principal.getUser().getLogin() == recipient.getLogin() ) {
                recipient = userService.getUserByLogin(senderLogin);
            }
            sendMessage.setNickName(recipient.getLogin());
            Message message = messageService.sendMessage(sender, sendMessage);
            recipientLogin = recipient.getLogin();
            senderLogin = sender.getLogin();

        }
        List<Message> messageList = messageService.UpdatedDialogBetweenUsers( recipientLogin, senderLogin,authentication, model);
        model.addAttribute("messageList",messageList);
        List<SendMessage> sendMessageList = new ArrayList<SendMessage>();
        for (Message msg : messageList) {
            msg.setRead(true);
            messageService.UpdateMessage(msg);
            sendMessageList.add(new SendMessage(msg));
        }
        return sendMessageList;
    }
}
