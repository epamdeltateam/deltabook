package com.deltabook.controllers;

import com.deltabook.model.User;
import com.deltabook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final int WEAK_STRENTH = 1;
    private final int FEAR_STRENGTH = 5;
    private final int STRONG_STRENGTH = 7;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("objectToFill", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User insertedObject, Model model) {
        userService.registerUser(insertedObject);
        model.addAttribute("objectToFill_auth", new User ());
        return "main";
    }
    @GetMapping(value = "/checkStrength", produces = {"text/html; charset-UTF-8"})
    public @ResponseBody
    String checkString(@RequestParam String password) {
        if(password.length() >= WEAK_STRENTH & password.length() < FEAR_STRENGTH  )
        return "слабый";
        else if(password.length() >= FEAR_STRENGTH & password.length() < STRONG_STRENGTH)
            return "средний";
        else if(password.length() >= STRONG_STRENGTH)
            return "сильный";
        return "";
    }




}
