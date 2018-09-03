package com.home.budgetplanner.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.controller.form.SearchIdentificationTypeForm;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.service.IdentificationTypeService;
import com.home.budgetplanner.validator.IdentificationTypeValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/identificationType")
@SessionAttributes("identificationType")
public class IdentificationTypeController {

    private static final Logger       logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    private IdentificationTypeService identificationTypeService;

    @GetMapping("/list")
    public String listIdentificationType(Model model) {

        List<IdentificationType> identificationsType = identificationTypeService.findAll();

        // add the customer to the model
        model.addAttribute("identificationsType", identificationsType);

        return "identificationType/list-identificationsType";
    }

    @PostMapping("/list")
    public String searchCustomers(@RequestParam("searchName") String searchName,
            @RequestParam(name = "startPosition", defaultValue = "1") int startPosition,
            @RequestParam(name = "maxResult", defaultValue = "10") int maxResult, Model theModel) {

        List<IdentificationType> indentifications = identificationTypeService.findByName(searchName, startPosition, maxResult);

        theModel.addAttribute("identificationsType", indentifications);

        return "identificationType/list-identificationsType";
    }

    // @GetMapping("/showFormForAdd")
    // public String showFormForAdd(Model model) {
    //
    // IdentificationType customer = new IdentificationType();
    // model.addAttribute("identificationType", customer);
    //
    // return "identificationType/identificationType-form";
    // }
    //
    // @PostMapping("/saveIdentificationType")
    // public String saveIdentificationType(@Valid
    // @ModelAttribute("identificationType") IdentificationType
    // identificationType, BindingResult result) {
    //
    // if (result.hasErrors()) {
    // return "identificationType/identificationType-form";
    // }
    // // save the customer using service
    //
    // identificationTypeService.save(identificationType);
    //
    // return "redirect:/identificationType/list";
    // }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {

        IdentificationType identificationType = new IdentificationType();
        model.addAttribute("identificationType", identificationType);

        return "identificationType/identificationType-form";
    }

    @PostMapping("/showFormForAdd")
    public String saveIdentificationType(@Valid @ModelAttribute("identificationType") IdentificationType identificationType, BindingResult result,
            SessionStatus status) {
        logger.info("***********************");

        logger.info(identificationType);
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            // String defaultMessage ="Attempting to bind disallowed fields: " +
            StringUtils.arrayToCommaDelimitedString(suppressedFields);
            // String objectName ="Binding problem";
            // ObjectError error = new ObjectError(objectName, defaultMessage);
            // result.addError(error );
            // There should be a best practice to pass this validation and show
            // it on the form

            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        if (result.hasErrors()) {
            // return "identificationType/identificationType-form";
            return "redirect:/identificationType/showFormForAdd";
        }
        System.out.print(identificationType);
        // save the customer using service

        identificationTypeService.save(identificationType);
        status.setComplete();
        return "redirect:/identificationType/list";
    }

    @InitBinder("identificationType")
    public void initBinder(WebDataBinder binder) {

        // binder.setAllowedFields("id", "name", "mnemonic", "description");

        //se comenta momentaneamente para probar validador de web flow
       // binder.addValidators(new IdentificationTypeValidator());
    }

    public SearchIdentificationTypeForm initializeForm() {
        SearchIdentificationTypeForm searchIdentificationTypeForm = new SearchIdentificationTypeForm();

        return searchIdentificationTypeForm;
    }

    public List<IdentificationType> searchIdentifications(SearchIdentificationTypeForm searchIdentificationTypeForm) {
        List<IdentificationType> indentifications = identificationTypeService.findByName(searchIdentificationTypeForm.getName(), searchIdentificationTypeForm.getStartPosition(),
                searchIdentificationTypeForm.getMaxResult());

        return indentifications;
    }
    
    public IdentificationType initializeIdentificationType() {

        IdentificationType identificationType = new IdentificationType();

        return identificationType;
    }
    
    
    public String saveNewIdentificationType(@Valid @ModelAttribute("identificationType") IdentificationType identificationType, BindingResult result,
            SessionStatus status) {
        logger.info("***********************");

        logger.info(identificationType);
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            // String defaultMessage ="Attempting to bind disallowed fields: " +
            StringUtils.arrayToCommaDelimitedString(suppressedFields);
            // String objectName ="Binding problem";
            // ObjectError error = new ObjectError(objectName, defaultMessage);
            // result.addError(error );
            // There should be a best practice to pass this validation and show
            // it on the form

            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        if (result.hasErrors()) {
            // return "identificationType/identificationType-form";
            return "redirect:/identificationType/showFormForAdd";
        }
        System.out.print(identificationType);
        // save the customer using service

        identificationTypeService.save(identificationType);
        status.setComplete();
        return "redirect:/identificationType/list";
    }
    


}
