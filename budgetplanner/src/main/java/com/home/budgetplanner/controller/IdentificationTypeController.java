package com.home.budgetplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.service.IdentificationTypeService;

@Controller
@RequestMapping("/identificationType")
public class IdentificationTypeController {

    @Autowired
    private IdentificationTypeService identificationTypeService;

    @GetMapping("/list")
    public String listIdentificationType(Model model) {

        List<IdentificationType> identificationsType = identificationTypeService.findAll();

        // add the customer to the model
        model.addAttribute("identificationsType", identificationsType);

        return "identificationType/list-identificationsType";
    }

    @PostMapping("/search")
    public String searchCustomers(@RequestParam("searchName") String searchName,
            @RequestParam(name = "startPosition", defaultValue = "1") int startPosition,
            @RequestParam(name = "maxResult", defaultValue = "10") int maxResult, Model theModel) {

        List<IdentificationType> indentifications = identificationTypeService.findByName(searchName, startPosition, maxResult);

        theModel.addAttribute("identificationsType", indentifications);

        return "identificationType/list-identificationsType";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {

        IdentificationType customer = new IdentificationType();
        model.addAttribute("identificationType", customer);

        return "identificationType/identificationType-form";
    }

    @PostMapping("/saveIdentificationType")
    public String saveIdentificationType(@ModelAttribute("identificationType") IdentificationType identificationType) {

        // save the customer using service

        identificationTypeService.save(identificationType);

        return "redirect:/identificationType/list";
    }

}
