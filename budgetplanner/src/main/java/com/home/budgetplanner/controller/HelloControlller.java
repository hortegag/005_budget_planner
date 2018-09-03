package com.home.budgetplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/hello")
public class HelloControlller {
    
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(defaultValue = "hecThor") String name, Model model) {

        model.addAttribute("message", "Hello, " + name);
        return "hello";
    }
}
