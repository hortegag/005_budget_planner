package com.home.budgetplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String authenticate() {
        return "login";
    }
    
    @RequestMapping("/loginBootstrap")
    public String loginBootstrap() {
        return "loginBootstrap";
    }
}

