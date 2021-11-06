package com.deltabook.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    @RequestMapping("/error_auth")
    public String authError() {
        return "error_auth";
    }

    @RequestMapping("/error_friend_request")
    public String friendRequestError() {
        return "error_friend_request";
    }

    @RequestMapping("/error_send_message")
    public String sendMessageError() {
        return "error_auth";
    }
}
