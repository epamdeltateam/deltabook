package com.deltabook.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeltaController {
    @RequestMapping("/")
    public String MainPage() {
        return "main";
    }

    @RequestMapping("/registration")
    public String Registration() {
        return "registration";
    }

}
