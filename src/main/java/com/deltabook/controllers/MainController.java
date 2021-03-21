package com.deltabook.controllers;

import com.deltabook.model.User;
import com.deltabook.security.details.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.Objects;

@Controller
public class MainController {

    @RequestMapping(value="/")
    public ModelAndView mainPage(Authentication authentication, Model model) {
        model.addAttribute("objectToFill_auth", new User());
        ModelAndView modelAndView = new ModelAndView();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        String image_string;
        if (Objects.nonNull(user.getPicture())){
            image_string = Base64.getEncoder().encodeToString(user.getPicture());
            modelAndView.addObject("image", image_string);
            modelAndView.addObject("hasImage", true);
        }
        else {
            modelAndView.addObject("hasImage", false);
        }
        modelAndView.addObject("name", user.getFirstName());
        modelAndView.addObject("surname", user.getLastName());
        modelAndView.setViewName("main");
        return modelAndView;
    }
}
