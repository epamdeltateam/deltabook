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
    private final static String USER_DOES_NOT_EXIST_ERROR = "Пользователя с таким никнеймом не существует!";

    @RequestMapping("/main_admin")
    private String mainAdmin(Authentication authentication, Model model) {
        model.addAttribute("SendChangeUser", new SendChangeUser());
        return "main_admin";
    }

    @RequestMapping("/change_user_last_name")
    private String changeUserLastName(Model model, @ModelAttribute SendChangeUser sendChangeUser) {
        String newLastName = sendChangeUser.getNewLastName();
        String errorText = null;
        User user = null;

        if(newLastName == null || newLastName.isEmpty()) {
            errorText = "Новая фамилия не может быть пустой строкой.";
        }
        else {
            user = userService.getUserByLogin(sendChangeUser.getNickName());
            if(user == null) {
                errorText = USER_DOES_NOT_EXIST_ERROR;
            }
        }

        if (errorText == null) {
            userService.changeLastNameUser(sendChangeUser, user);
            return "main_admin";
        } else {
            model.addAttribute("errorText", errorText);
            return "error";
        }
    }

    @RequestMapping("/delete_user_temp")
    private String deleteUserTemp(Authentication authentication, Model model, @ModelAttribute SendChangeUser sendChangeUser) {
        User currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String errorText = null;
        User user = null;
        if (sendChangeUser.getNickName().equals(currentUser.getLogin())) {
            errorText = USER_CAN_NOT_DELETE_HIMSELF_ERROR;
        } else {
            user = userService.getUserByLogin(sendChangeUser.getNickName());
            if (user == null) {
                errorText = USER_DOES_NOT_EXIST_ERROR;
            }
        }

        if (errorText == null) {
            userService.deleteUserTemp(user);
            return "main_admin";
        } else {
            model.addAttribute("errorText", errorText);
            return "error";
        }
    }

    @RequestMapping("/delete_user_total")
    private String deleteUserTotal(Authentication authentication, Model model, @ModelAttribute SendChangeUser sendChangeUser) {
        User currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String errorText = null;
        User user = null;
        if (sendChangeUser.getNickName().equals(currentUser.getLogin())) {
            errorText = USER_CAN_NOT_DELETE_HIMSELF_ERROR;
        } else {
            user = userService.getUserByLogin(sendChangeUser.getNickName());
            if (user == null) {
                errorText = USER_DOES_NOT_EXIST_ERROR;
            }
        }

        if (errorText == null) {
            userService.deleteUserTotal(user);
            return "main_admin";
        } else {
            model.addAttribute("errorText", errorText);
            return "error";
        }
    }

}