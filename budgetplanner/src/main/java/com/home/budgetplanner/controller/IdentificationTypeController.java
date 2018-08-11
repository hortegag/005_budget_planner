package com.home.budgetplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.service.IdentificationTypeService;

@Controller
@RequestMapping("/identificationType")
public class IdentificationTypeController {
    
    @Autowired
    private IdentificationTypeService identificationTypeService;
    
    
    @GetMapping("/list")
    public String listIdentificationType(Model model){
            
            List<IdentificationType> identificationsType = identificationTypeService.findAll();
            
            // add the customer to the model
            model.addAttribute("identificationsType",identificationsType);
            
            return "list-identificationsType";
    }
    

}
