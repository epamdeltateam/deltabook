package com.deltabook.controllers;

import com.deltabook.model.User;
import com.deltabook.security.details.UserDetailsImpl;
import com.deltabook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/upload_avatar")
    public String uploadAvatar(Model model) {
        model.addAttribute("msg", "Waiting for upload ");
        return "upload_avatar";
    }

    @PostMapping("/upload_avatar")
    public String uploadAvatarSave(Authentication authentication, Model model, @RequestParam("files") MultipartFile file) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        try {
            userService.uploadAvatar(user, file);
            model.addAttribute("msg", "Successfully uploaded files ");
        } catch (Exception ex) {
            model.addAttribute("msg", ex.getMessage());
        }
        return "upload_avatar";
    }

    @RequestMapping(value = "/{nickname}")
    public ModelAndView GenerateUserPsge(@PathVariable String nickname) {
        ModelAndView model = new ModelAndView();
        model.addObject("nickname", nickname);
        User user = userService.getUserByLogin(nickname);
        String image_string;
        if (user.getPicture() != null) {
            image_string = Base64.getEncoder().encodeToString(user.getPicture());
            model.addObject("image", image_string);
        }
        model.addObject("nickname", user.getLogin());

        model.addObject("name", user.getFirstName());
        model.addObject("surname", user.getLastName());
        model.setViewName("user_page");
        return model;
    }
}
