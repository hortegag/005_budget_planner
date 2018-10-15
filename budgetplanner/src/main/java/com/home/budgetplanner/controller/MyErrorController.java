package com.home.budgetplanner.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.home.budgetplanner.BudgetplannerApplication;

//@Controller
public class MyErrorController implements ErrorController {
    private static final Logger logger = LogManager.getLogger(BudgetplannerApplication.class);


   // @RequestMapping("/error")
    public String handleError(HttpServletRequest httpRequest) {
        // do something like logging
        //logger.error("error",ex);
        
        Integer statusCode = (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) httpRequest.getAttribute("javax.servlet.error.exception");
        
        logger.error("errrrrrrrrr2",exception);

        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
