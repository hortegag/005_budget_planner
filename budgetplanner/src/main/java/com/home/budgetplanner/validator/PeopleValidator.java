package com.home.budgetplanner.validator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.binding.message.MessageContext;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.service.IdentificationTypeService;
import com.home.budgetplanner.service.PeopleService;

@Component
public class PeopleValidator /* implements Validator */ {

    private static final Logger       logger = LogManager.getLogger(PeopleValidator.class);

    @Autowired
    private IdentificationTypeService identificationTypeService;

    @Autowired
    private PeopleService             peopleService;

    public PeopleValidator() {
        logger.info("============================================================================PeopleValidator created");

    }

    // It could be enable to validate the form from the controller
    // @Override
    // public boolean supports(Class<?> clazz) {
    // return clazz.isAssignableFrom(IdentificationType.class);
    // }

    // @Override
    // public void validate(Object target, Errors errors) {
    // IdentificationType identificationType = (IdentificationType) target;
    //
    // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
    // "error.required", new Object[] { "Identification name" });
    // ValidationUtils.rejectIfEmpty(errors, "mnemonic", "error.required");
    // }

    public Event test(IdentificationType identificationType, ValidationContext validationContext) {

        if (identificationType.getName() == null) {
            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            // errorMessageBuilder.code("error.page.selectdeliveryoptions.deliverydate.required");
            // validationContext.addMessage(errorMessageBuilder.build());
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            return new EventFactorySupport().error(this);
        }

        validationContext.getMessageContext().addMessage(
                new MessageBuilder().error().source("person").code("Size.person.firstName").defaultText("Length must be between 2 and 50").build());

        /*
         * if (!orderForm.getDeliveryDate().after(DateUtils.truncate(orderForm.
         * getOrderDate(), Calendar.DAY_OF_MONTH))) { MessageBuilder
         * errorMessageBuilder = new MessageBuilder().error();
         * errorMessageBuilder.source("deliveryDate"); errorMessageBuilder.code(
         * "error.page.selectdeliveryoptions.deliverydate.in.past");
         * messageContext.addMessage(errorMessageBuilder.build()); return new
         * EventFactorySupport().error(this); }
         */
        return new EventFactorySupport().success(this);

    }

    public void validateAddEntityFuncionalTres(IdentificationType identificationType, Errors errors) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        // ValidationUtils.rejectIfEmpty(validationContext.getMessageContext().g,
        // "mnemonic", "error.required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required", new Object[] { "Identification name" });

    }

    public void validateAddEntity(People people, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        if (StringUtils.isBlank(people.getIdentification())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("identification");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Identification");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            people.setIdentification((people.getIdentification().toLowerCase()));

            People peopleDao = peopleService.findByIdentificationAndIdentificationType(people.getIdentification(), people.getIdentificationType());

            if (peopleDao != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("identification");
                errorMessageBuilder.code("error.people.duplicateIdentification");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

                errorMessageBuilder.source("identificationType");
                errorMessageBuilder.code("error.people.duplicateIdentification");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            }

        }

        if (StringUtils.isBlank(people.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setName(people.getName().toLowerCase());

        }

        if (StringUtils.isBlank(people.getLastName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("lastName");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Last Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setLastName(people.getLastName().toLowerCase());

        }

        if (StringUtils.isBlank(people.getEmail())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("email");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Email");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setEmail(people.getEmail().toLowerCase());

            Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            Matcher m = p.matcher(people.getEmail());

            if (!m.find()) {

                // if
                // (!people.getPassword().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))
                // {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("email");
                errorMessageBuilder.code("error.people.emailPattern");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            } else {
                
                People peopleDao = peopleService.findByEmail(people.getEmail());
                
                
                if (peopleDao != null) {

                    MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                    errorMessageBuilder.source("email");
                    errorMessageBuilder.code("error.people.duplicateEmail");
                    validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
                }
                
                
                
            }

        }

        if (people.getBornDate() == null) {

            logger.info("ffffffffffffffffffffffffffffffffffff");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("bornDate");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Born Date");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            logger.info("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");

            // fixme: handle typemissMatch problem
            LocalDate today = LocalDate.now();
            today = today.minus(18, ChronoUnit.YEARS);

            if (people.getBornDate().isAfter(today)) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("bornDate");
                errorMessageBuilder.code("error.people.adult");

                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

        }

        if (StringUtils.isBlank(people.getUsername())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("username");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("User name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {

            people.setUsername(people.getUsername().toLowerCase());

            People peopleDao = peopleService.findByUsername(people.getUsername());

            if (peopleDao != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("username");
                errorMessageBuilder.code("error.people.duplicateUsername");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }
        }

        if (StringUtils.isBlank(people.getPassword())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("password");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Password");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            // people.setPassword(people.getPassword().toLowerCase());

            // if
            // (!people.getPassword().matches("\\Q^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$\\E"))
            // {
            //
            // MessageBuilder errorMessageBuilder = new
            // MessageBuilder().error();
            // errorMessageBuilder.source("password");
            // errorMessageBuilder.code("error.people.passwordPattern");
            // validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            //
            // }

            Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
            Matcher m = p.matcher(people.getPassword());

            if (!m.find()) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("password");
                errorMessageBuilder.code("error.people.passwordPattern");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

            //
            // Pattern p =
            // Pattern.compile("\\Q^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$\\E");
            // Matcher m = p.matcher(people.getPassword());
            //
            // if (m.matches()){
            //
            // }

        }

        if (people.getGroups() == null || people.getGroups().size() == 0) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("groups");
            errorMessageBuilder.code("error.people.groups.mandatory");

            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            //people.setPassword(people.getPassword().toLowerCase());
        }

        // if (StringUtils.isBlank(identificationType.getMnemonic())) {
        //
        // logger.info("el vaobjeto no tiene nombres");
        //
        // MessageBuilder errorMessageBuilder = new MessageBuilder().error();
        // errorMessageBuilder.source("mnemonic");
        // errorMessageBuilder.code("error.required");
        // errorMessageBuilder.resolvableArg("Identification mnemonic");
        // validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        // } else {
        //
        // identificationType.setMnemonic(identificationType.getMnemonic().toUpperCase());
        //
        // List<Tuple> identifications =
        // identificationTypeService.findByIdentificationType(identificationType,
        // 1, 1);
        //
        // if (identifications != null && identifications.size() != 0) {
        //
        // MessageBuilder errorMessageBuilder = new MessageBuilder().error();
        // errorMessageBuilder.source("mnemonic");
        // errorMessageBuilder.code("error.identificationType.duplicateMnemonic");
        // validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        // }
        //
        // }
        //
        // if (StringUtils.isBlank(identificationType.getDescription())) {
        // logger.info("el vaobjeto no tiene nombres");
        //
        // MessageBuilder errorMessageBuilder = new MessageBuilder().error();
        // errorMessageBuilder.source("description");
        // errorMessageBuilder.code("error.required");
        // errorMessageBuilder.resolvableArg("Description");
        // validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        // }

    }

    public void validateEditEntity(People people, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        logger.info("vlor del id:" + people.getId());

        People peopleOnDataBase = peopleService.findById(people.getId());

        logger.info("test " + peopleService.toString());

        logger.info("from database " + peopleOnDataBase.toString());

        if (peopleOnDataBase.equals(people)) {
            // logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ya existe ");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("global");

            errorMessageBuilder.code("error.people.noChangesEntity");

            // errorMessageBuilder.resolvableArg("Identification name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            // validationContext.getMessageContext().addMessage( new
            // MessageBuilder().error().defaultText("No room is available at
            // this hotel").build());

            // validationContext.getMessageContext().addMessage(new
            // MessageBuilder().error().code("error.identificationType.noChangesEntity").build());

            return;
        }

        if (StringUtils.isBlank(people.getIdentification())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("identification");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Identification");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            people.setIdentification((people.getIdentification().toLowerCase()));

            People peopleDao = peopleService.findByIdentificationAndIdentificationTypeAndIdNot(people.getIdentification(),
                    people.getIdentificationType(), people.getId());

            if (peopleDao != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("identification");
                errorMessageBuilder.code("error.people.duplicateIdentification");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

                errorMessageBuilder.source("identificationType");
                errorMessageBuilder.code("error.people.duplicateIdentification");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

        }

        if (StringUtils.isBlank(people.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setName(people.getName().toLowerCase());

        }

        if (StringUtils.isBlank(people.getLastName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("lastName");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Last Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setLastName(people.getLastName().toLowerCase());

        }
        
        
        
        if (StringUtils.isBlank(people.getEmail())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("email");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Email");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setEmail(people.getEmail().toLowerCase());

            Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            Matcher m = p.matcher(people.getEmail());

            if (!m.find()) {

                // if
                // (!people.getPassword().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))
                // {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("email");
                errorMessageBuilder.code("error.people.emailPattern");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            } else {
                
                People peopleDao = peopleService.findByEmailAndIdNot(people.getEmail(),people.getId());
                
                
                if (peopleDao != null) {

                    MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                    errorMessageBuilder.source("email");
                    errorMessageBuilder.code("error.people.duplicateEmail");
                    validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
                }
                
            }

        }

        if (people.getBornDate() == null) {

            logger.info("ffffffffffffffffffffffffffffffffffff");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("bornDate");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Born Date");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            logger.info("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");

            // fixme: handle typemissMatch problem
            LocalDate today = LocalDate.now();
            today = today.minus(18, ChronoUnit.YEARS);

            if (people.getBornDate().isAfter(today)) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("bornDate");
                errorMessageBuilder.code("error.people.adult");

                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

        }
        
        
        if (StringUtils.isBlank(people.getUsername())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("username");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("User name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {

            people.setUsername(people.getUsername().toLowerCase());

            People peopleDao = peopleService.findByUsernameAndIdNot(people.getUsername(), people.getId());

            if (peopleDao != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("username");
                errorMessageBuilder.code("error.people.duplicateUsername");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }
        }

        if (StringUtils.isBlank(people.getPassword())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("password");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Password");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            // people.setPassword(people.getPassword().toLowerCase());

            // if
            // (!people.getPassword().matches("\\Q^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$\\E"))
            // {
            //
            // MessageBuilder errorMessageBuilder = new
            // MessageBuilder().error();
            // errorMessageBuilder.source("password");
            // errorMessageBuilder.code("error.people.passwordPattern");
            // validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            //
            // }

            Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
            Matcher m = p.matcher(people.getPassword());

            if (!m.find()) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("password");
                errorMessageBuilder.code("error.people.passwordPattern");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

            //
            // Pattern p =
            // Pattern.compile("\\Q^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$\\E");
            // Matcher m = p.matcher(people.getPassword());
            //
            // if (m.matches()){
            //
            // }

        }

        if (people.getGroups() == null || people.getGroups().size() == 0) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("groups");
            errorMessageBuilder.code("error.people.groups.mandatory");

            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } //else {
            //people.setPassword(people.getPassword().toLowerCase());
        //}

    }
    
    
    
    
    
    
    
    
    
    
    public void validateEditEntity(PeopleDTO people, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        logger.info("vlor del id:" + people.getId());

        People peopleOnDataBase = peopleService.findById(people.getId());

        logger.info("test " + peopleService.toString());

        logger.info("from database " + peopleOnDataBase.toString());

        if (peopleOnDataBase.equals(people)) {
            // logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ya existe ");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("global");

            errorMessageBuilder.code("error.people.noChangesEntity");

            // errorMessageBuilder.resolvableArg("Identification name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            // validationContext.getMessageContext().addMessage( new
            // MessageBuilder().error().defaultText("No room is available at
            // this hotel").build());

            // validationContext.getMessageContext().addMessage(new
            // MessageBuilder().error().code("error.identificationType.noChangesEntity").build());

            return;
        }

        if (StringUtils.isBlank(people.getIdentification())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("identification");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Identification");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            people.setIdentification((people.getIdentification().toLowerCase()));

            People peopleDao =null;
//            People peopleDao = peopleService.findByIdentificationAndIdentificationTypeAndIdNot(people.getIdentification(),
//                    people.getIdentificationType(), people.getId());

            if (peopleDao != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("identification");
                errorMessageBuilder.code("error.people.duplicateIdentification");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

                errorMessageBuilder.source("identificationType");
                errorMessageBuilder.code("error.people.duplicateIdentification");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

        }

        if (StringUtils.isBlank(people.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setName(people.getName().toLowerCase());

        }

        if (StringUtils.isBlank(people.getLastName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("lastName");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Last Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setLastName(people.getLastName().toLowerCase());

        }
        
        
        
        if (StringUtils.isBlank(people.getEmail())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("email");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Email");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            people.setEmail(people.getEmail().toLowerCase());

            Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            Matcher m = p.matcher(people.getEmail());

            if (!m.find()) {

                // if
                // (!people.getPassword().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))
                // {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("email");
                errorMessageBuilder.code("error.people.emailPattern");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            } else {
                
                People peopleDao = peopleService.findByEmailAndIdNot(people.getEmail(),people.getId());
                
                
                if (peopleDao != null) {

                    MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                    errorMessageBuilder.source("email");
                    errorMessageBuilder.code("error.people.duplicateEmail");
                    validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
                }
                
            }

        }

        if (people.getBornDate() == null) {

            logger.info("ffffffffffffffffffffffffffffffffffff");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("bornDate");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Born Date");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            logger.info("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");

            // fixme: handle typemissMatch problem
            LocalDate today = LocalDate.now();
            today = today.minus(18, ChronoUnit.YEARS);

            if (people.getBornDate().isAfter(today)) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("bornDate");
                errorMessageBuilder.code("error.people.adult");

                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

        }
        
        
        if (StringUtils.isBlank(people.getUsername())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("username");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("User name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {

            people.setUsername(people.getUsername().toLowerCase());

            People peopleDao = peopleService.findByUsernameAndIdNot(people.getUsername(), people.getId());

            if (peopleDao != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("username");
                errorMessageBuilder.code("error.people.duplicateUsername");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }
        }



        if (people.getGroups() == null || people.getGroups().size() == 0) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("groups");
            errorMessageBuilder.code("error.people.groups.mandatory");

            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } 

    }


}