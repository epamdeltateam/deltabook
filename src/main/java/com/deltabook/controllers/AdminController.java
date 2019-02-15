package com.deltabook.controllers;

import com.deltabook.model.Roles;
import com.deltabook.model.User;
import com.deltabook.repositories.UserRepository;
import com.deltabook.security.datails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/main_admin")
    public String main_admin(Authentication authentication, Model model) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        switch(user.getRole()) {
            case ROLE_USER: return ("/");
            case ROLE_ADMIN: return("/main_admin");
        }
        return "main_admin";
    }
}
