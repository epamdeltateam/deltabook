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
import java.util.Objects;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @GetMapping("/friends")
    private String sendRequest(Authentication authentication, Model model) {
        model.addAttribute("sendFriendRequest", new SendFriendRequest());
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userTo = principal.getUser();
        List<Contact> contactListTo = contactService.getAllRequestsToUser(userTo);
        model.addAttribute("contactReceivedList", contactListTo);
        List<Contact> contactListFrom = contactService.getAllRequestsFromUser(userTo);
        model.addAttribute("contactSentList", contactListFrom);
        List<SendFriend> friends = contactService.getAllFriends(userTo);
        model.addAttribute("Friends", friends);
        return "/friends";
    }

    @PostMapping("/send_friend_request")
    private String sendRequest(Authentication authentication, Model model, @ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userFrom = principal.getUser();
        User userTo = userService.getUserByLogin(send_req.getFriendNickname());

        String errorText = null;

        if (Objects.isNull(userTo)) {
            errorText = "Пользователя с таким никнеймом не существует!";
        }
        if (userTo != null && userFrom.getLogin().equals(userTo.getLogin())) {
            errorText = "Вы не можете отправить приглашение самому себе!";
        }

        if (errorText == null) {
            contactService.sendRequestFriend(userFrom, userTo, send_req.getRequestMessage());
            return "redirect:friends";
        } else {
            model.addAttribute("errorText", errorText);
            return "/error_friend_request";
        }
    }

    @RequestMapping(value = "/get_last_friend_request", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private SendFriendRequest getLastFriendRequest(Authentication authentication, @RequestParam("idOfPreviousContact") Long idOfPreviousContact) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User friendTo = principal.getUser();

        Contact contact = contactService.getLastNotAcceptedRequest(friendTo);
        if (Objects.isNull(contact) || contact.getId().equals(idOfPreviousContact)) {
            return null;
        }
        return new SendFriendRequest(contact);
    }

    @PostMapping("/accept_friend_request")
    private String acceptFriendRequest(Authentication authentication, @ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userTo = principal.getUser();
        User userFrom = userService.getUserByLogin(send_req.getFriendNickname());
        contactService.confirmRequest(userFrom, userTo);
        return "redirect:friends";
    }

    @PostMapping("/decline_friend_request")
    private String declineFriendRequest(Authentication authentication, @ModelAttribute SendFriendRequest send_req) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userTo = principal.getUser();
        User userFrom = userService.getUserByLogin(send_req.getFriendNickname());
        contactService.declineRequest(userFrom, userTo);
        return "redirect:friends";
    }
}
