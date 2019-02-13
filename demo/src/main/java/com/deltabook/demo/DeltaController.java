package com.deltabook.demo;

import com.deltabook.demo.model.User;
import com.deltabook.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class DeltaController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String MainPage(Model model) {
        model.addAttribute("objectToFill_auth", new User ());
        return "main";
    }

    @RequestMapping("/registration")
    public String Registration(Model model) {
        model.addAttribute("objectToFill", new User ());
        return "registration";
    }

    @RequestMapping( value = "/enter_reg", method = RequestMethod.POST)
    String sum(@ModelAttribute User insertedObject, Model model) {
        userRepository.save(new User(insertedObject.getLogin(), insertedObject.getPassword()));
        model.addAttribute("objectToFill_auth", new User ());
        return "main";
    }
    @RequestMapping( value = "/enter_auth", method = RequestMethod.POST)
    String sum2(@ModelAttribute User insertedObject, Model model) {
        model.addAttribute("objectToFill_auth", new User ());
        Iterable<User> iter =  userRepository.findLogPass(insertedObject.getLogin(), insertedObject.getPassword());
        Collection<User> list = new ArrayList<User>();
        for (User item : iter) {
            list.add(item);
        }
        if(list.isEmpty() == true)
        return "error_auth";

        return "main";

    }
}
