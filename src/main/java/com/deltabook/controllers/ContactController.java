package com.deltabook.controllers;


import com.deltabook.model.Contact;
import com.deltabook.model.User;
import com.deltabook.model.send.SendFriend;
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
        List<SendFriend> friends = contactService.getAllFriends(userTo);
        model.addAttribute("Friends", friends);
        return "friends";
    }
    @PostMapping("/send_friend_request")
    String sendRequest(Authentication authentication, Model model, @ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userFrom = principal.getUser();
        User userTo = userService.getUserByLogin(send_req.getFriendNickname());
        boolean checkContact = contactService.checkIsContactExists(userFrom,userTo);
        if(userTo != null && userFrom.getLogin() !=userTo.getLogin() && checkContact == false ) {
            contactService.sendRequestFriend(userFrom, userTo, send_req.getRequestMessage());
        }
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
    @PostMapping("/accept_friend_request")
    String acceptFriendRequest(Authentication authentication, Model model, @ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userTo = principal.getUser();
        User userFrom = userService.getUserByLogin(send_req.getFriendNickname());
        contactService.confirmRequest(userFrom, userTo);
        return "main";
    }
    @PostMapping("/decline_friend_request")
    String declineFriendRequest(Authentication authentication, Model model,@ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userTo = principal.getUser();
        User userFrom = userService.getUserByLogin(send_req.getFriendNickname());
        contactService.declineRequest(userFrom, userTo);
        return "main";
    }
}
