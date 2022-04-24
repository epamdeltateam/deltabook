package com.deltabook.controllers;

import com.deltabook.model.User;
import com.deltabook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    private String getRegistrationPage(Model model) {
        model.addAttribute("objectToFill", new User());
        return "registration";
    }

    @PostMapping("/registration")
    private String registerUser(Model model, @ModelAttribute User insertedObject) {
        StringBuilder errorSb = new StringBuilder();
        if (insertedObject.getLogin().isEmpty()) {
            errorSb.append("Поле с никнеймом не может быть пустым. <br>");
        }
        if (insertedObject.getFirstName().isEmpty()) {
            errorSb.append("Поле с именем не может быть пустым. <br>");
        }
        if (insertedObject.getLastName().isEmpty()) {
            errorSb.append("Поле с фамилией не может быть пустым. <br>");
        }
        if (insertedObject.getPassword().isEmpty()) {
            errorSb.append("Поле с паролем не может быть пустым.");
        }
        if (errorSb.length() == 0) {
            userService.registerUser(insertedObject);
            return "login";
        } else {
            model.addAttribute("errorText", errorSb.toString());
            return "error_register";
        }
    }

    @GetMapping(value = "/checkStrength", produces = {"text/html; charset-UTF-8"})
    private @ResponseBody
    String checkPassword(@RequestParam String password) {
        final int WEAK_STRENTH = 1;
        final int FEAR_STRENGTH = 5;
        final int STRONG_STRENGTH = 7;

        if (password.length() >= WEAK_STRENTH & password.length() < FEAR_STRENGTH) {
            return "слабый";
        } else if (password.length() >= FEAR_STRENGTH & password.length() < STRONG_STRENGTH) {
            return "средний";
        } else if (password.length() >= STRONG_STRENGTH) {
            return "сильный";
        }
        return "";
    }

}
