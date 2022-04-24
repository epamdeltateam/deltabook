package com.deltabook.controllers;

import com.deltabook.model.User;
import com.deltabook.model.send.SendChangeUser;
import com.deltabook.security.details.UserDetailsImpl;
import com.deltabook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    private final static String USER_CAN_NOT_DELETE_HIMSELF_ERROR = "Пользователь не может удалить сам себя.";

    @RequestMapping("/main_admin")
    public String mainAdmin(Authentication authentication, Model model) {
        model.addAttribute("SendChangeUser", new SendChangeUser());
        return "main_admin";
    }

    @RequestMapping("/change_user_last_name")
    public String changeUserLastName(Model model, @ModelAttribute SendChangeUser sendChangeUser) {
        String newLastName = sendChangeUser.getNewLastName();
        if(newLastName != null && !newLastName.isEmpty()) {
            userService.changeLastNameUser(sendChangeUser);
            model.addAttribute("SendChangeUser", new SendChangeUser());
            return "main_admin";
        }
        else {
            model.addAttribute("errorText", "Новая фамилия не может быть пустой строкой.");
            return "error";
        }
    }

    @RequestMapping("/delete_user_temp")
    public String deleteUserTemp(Authentication authentication, Model model, @ModelAttribute SendChangeUser sendChangeUser) {
        model.addAttribute("SendChangeUser", new SendChangeUser());
        User currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (!sendChangeUser.getNickName().equals(currentUser.getLogin())) {
            userService.deleteUserTemp(sendChangeUser);
            return "main_admin";
        } else {
            model.addAttribute("errorText", USER_CAN_NOT_DELETE_HIMSELF_ERROR);
            return "error";
        }
    }

    @RequestMapping("/delete_user_total")
    public String deleteUserTotal(Authentication authentication, Model model, @ModelAttribute SendChangeUser sendChangeUser) {
        model.addAttribute("SendChangeUser", new SendChangeUser());
        User currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (!sendChangeUser.getNickName().equals(currentUser.getLogin())) {
            userService.deleteUserTotal(sendChangeUser);
            return "main_admin";
        } else {
            model.addAttribute("errorText", USER_CAN_NOT_DELETE_HIMSELF_ERROR);
            return "error";
        }
    }

}