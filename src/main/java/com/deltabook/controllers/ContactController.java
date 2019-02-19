package com.deltabook.controllers;


import com.deltabook.model.Contact;
import com.deltabook.model.User;
import com.deltabook.model.send.SendFriendRequest;
import com.deltabook.security.details.UserDetailsImpl;
import com.deltabook.services.ContactService;
import com.deltabook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @GetMapping("/friends")
    String sendRequest(Authentication authentication, Model model) {
        model.addAttribute("sendFriendRequest", new SendFriendRequest());
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userTo = principal.getUser();
        List<Contact> contactListTo = contactService.getAllRequestsToUser(userTo);
        model.addAttribute("contactReceivedList", contactListTo);
        List<Contact> contactListFrom = contactService.getAllRequestsFromUser(userTo);
        model.addAttribute("contactSentList", contactListFrom);
        List<Contact> FriendsTo = contactService.getFriendsTo(userTo);
        List<Contact> FriendsFrom = contactService.getFriendsFrom(userTo);
        model.addAttribute("FriendsTo", FriendsTo);
        model.addAttribute("FriendsFrom", FriendsFrom);
        return "friends";
    }
    @PostMapping("/send_friend_request")
    String sendRequest(Authentication authentication, Model model, @ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userFrom = principal.getUser();
        User userTo = userService.getUserByLogin(send_req.getFriendNickname());
        contactService.sendRequestFriend(userFrom, userTo,send_req.getRequestMessage() );
        return "main";
    }
    @RequestMapping(value = "/get_last_friend_request",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SendFriendRequest getLastFriendRequest(Authentication authentication, @RequestParam("idOfPreviousContact") Long idOfPreviousContact){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User friendTo = principal.getUser();

        System.out.println(idOfPreviousContact);

        Contact contact = contactService.getLastNotAcceptedRequest(friendTo);
        if (contact == null || contact.getId().equals(idOfPreviousContact)){
            return null;
        }
        return new SendFriendRequest(contact);
    }
    @PostMapping("/proceed_friend_request")
    String proccedRequest(Authentication authentication, Model model, @ModelAttribute SendFriendRequest send_req, @RequestParam(value="action", required=true) String action) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userTo = principal.getUser();
        User userFrom = userService.getUserByLogin(send_req.getFriendNickname());
        contactService.proceedFriendRequest(userFrom, userTo, action);
        return "main";
    }
}
