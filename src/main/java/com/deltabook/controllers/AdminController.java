package com.deltabook.controllers;

import com.deltabook.model.send.SendChangeUser;
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

    @RequestMapping("/main_admin")
    public String mainAdmin(Authentication authentication, Model model) {
        model.addAttribute("SendChangeUser", new SendChangeUser());
        return "main_admin";
    }
    @RequestMapping("/change_user_last_name")
    public String changeUserLastName(Authentication authentication, Model model, @ModelAttribute SendChangeUser SendChangeUser) {
        userService.changeLastNameUser(SendChangeUser);
        model.addAttribute("SendChangeUser", new SendChangeUser());
        return "main_admin";
    }
    @RequestMapping("/delete_user_temp")
    public String deleteUserTemp(Authentication authentication, Model model, @ModelAttribute SendChangeUser SendChangeUser) {
        model.addAttribute("SendChangeUser", new SendChangeUser());
        userService.deleteUserTemp(SendChangeUser);
        return "main_admin";
    }
    @RequestMapping("/delete_user_total")
    public String deleteUserTotal(Authentication authentication, Model model, @ModelAttribute SendChangeUser SendChangeUser) {
        model.addAttribute("SendChangeUser", new SendChangeUser());
        userService.deleteUserTotal(SendChangeUser);
        return "main_admin";
    }

}
