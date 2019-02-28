package com.deltabook.controllers;

import com.deltabook.model.*;
import com.deltabook.model.send.SendFriendRequest;
import com.deltabook.model.send.SendMessage;
import com.deltabook.repositories.ContactRepository;
import com.deltabook.repositories.MessageRepository;
import com.deltabook.repositories.UserRepository;
import com.deltabook.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.Base64;

@Controller
public class MainController {

    @RequestMapping(value="/")
    public ModelAndView mainPage(Authentication authentication, Model model) {
        model.addAttribute("objectToFill_auth", new User());
        ModelAndView modelAndView = new ModelAndView();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User user = principal.getUser();
        String image_string;
        if (user.getPicture() != null){
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
