package com.home.budgetplanner.validator;

import java.util.List;

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
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.service.IdentificationTypeService;

@Component
public class IdentificationTypeValidator /* implements Validator */ {

    private static final Logger       logger = LogManager.getLogger(IdentificationTypeValidator.class);

    @Autowired
    private IdentificationTypeService identificationTypeService;

    public IdentificationTypeValidator() {
        logger.info("============================================================================IdentificationTypeValidator created");

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

    public void validateAddEntity(IdentificationType identificationType, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        if (StringUtils.isBlank(identificationType.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Identification name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            // return new EventFactorySupport().error(this);
        } else {
            identificationType.setName(StringUtils.capitalize(identificationType.getName().toLowerCase()));

            List<IdentificationType> identifications = identificationTypeService.findByName(identificationType.getName(), 1, 1);

            if (identifications != null && identifications.size() != 0) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("name");
                errorMessageBuilder.code("error.identificationType.duplicateName");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            }

        }

        if (StringUtils.isBlank(identificationType.getMnemonic())) {

            logger.info("el vaobjeto no tiene nombres");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("mnemonic");
            errorMessageBuilder.code("error.required");
            errorMessageBuilder.resolvableArg("Identification mnemonic");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {

            identificationType.setMnemonic(identificationType.getMnemonic().toUpperCase());

            List<Tuple> identifications = identificationTypeService.findByIdentificationType(identificationType, 1, 1);

            if (identifications != null && identifications.size() != 0) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("mnemonic");
                errorMessageBuilder.code("error.identificationType.duplicateMnemonic");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            }

            // validacion por mnemonic
            /*
             * if
             * (identificationTypeService.findByName(identificationType.getName(
             * ), 1, 1) !=null) {
             * 
             * MessageBuilder errorMessageBuilder = new
             * MessageBuilder().error(); errorMessageBuilder.source("name");
             * errorMessageBuilder.code("error.identificationType.duplicateName"
             * ); validationContext.getMessageContext().addMessage(
             * errorMessageBuilder.build()); }
             */
        }

        if (StringUtils.isBlank(identificationType.getDescription())) {
            logger.info("el vaobjeto no tiene nombres");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("description");
            errorMessageBuilder.code("error.required");
            errorMessageBuilder.resolvableArg("Description");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        }

    }

    public void validateEditEntityWithOutReturn(IdentificationType identificationType, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        logger.info("vlor del id:" + identificationType.getId());

        IdentificationType identificationTypeOnDataBase = identificationTypeService.findById(identificationType.getId());

        logger.info("test " + identificationType.toString());

        logger.info("from database " + identificationTypeOnDataBase.toString());

        if (identificationTypeOnDataBase.equals(identificationType)) {
            // logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ya existe ");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("global");

            errorMessageBuilder.code("error.identificationType.noChangesEntity");

            // errorMessageBuilder.resolvableArg("Identification name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            // validationContext.getMessageContext().addMessage( new
            // MessageBuilder().error().defaultText("No room is available at
            // this hotel").build());

            validationContext.getMessageContext().addMessage(new MessageBuilder().error().code("error.identificationType.noChangesEntity").build());

            return;
        }

        if (StringUtils.isBlank(identificationType.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();

            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Identification name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {
            logger.info("++++++++++++++++++++++++++++++++ingreso a la validacion");

            identificationType.setName(StringUtils.capitalize(identificationType.getName().toLowerCase()));

            // List<IdentificationType> identifications =
            // identificationTypeService.findByName(identificationType.getName(),
            // 1, 1);
            //
            // if (identifications != null && identifications.size() != 0) {
            //
            // MessageBuilder errorMessageBuilder = new
            // MessageBuilder().error();
            // errorMessageBuilder.source("name");
            // errorMessageBuilder.code("error.identificationType.duplicateName");
            // validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            // }

            IdentificationType identificationTypeToQuery = new IdentificationType();
            identificationTypeToQuery.setName(identificationType.getName());

            // List<Tuple> identifications =
            // identificationTypeService.findByIdentificationType(identificationType,
            // 1, 1);

            List<Tuple> identifications = identificationTypeService.findByIdentificationType(identificationTypeToQuery, 1, 1);
            // List<IdentificationType> identifications =
            // identificationTypeService.findByName(identificationType.getName(),
            // 1, 1);

            if (identifications != null && identifications.size() != 0) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("name");
                errorMessageBuilder.code("error.identificationType.duplicateName");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            }

        }

        if (StringUtils.isBlank(identificationType.getMnemonic())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("mnemonic");
            errorMessageBuilder.code("error.required");
            errorMessageBuilder.resolvableArg("Identification mnemonic");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            identificationType.setMnemonic(identificationType.getMnemonic().toUpperCase());

            // List<Tuple> identifications =
            // identificationTypeService.findByIdentificationType(identificationType,
            // 1, 1);
            //
            // if (identifications != null && identifications.size() != 0) {
            //
            // MessageBuilder errorMessageBuilder = new
            // MessageBuilder().error();
            // errorMessageBuilder.source("mnemonic");
            // errorMessageBuilder.code("error.identificationType.duplicateMnemonic");
            // validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            // }

        }

        if (StringUtils.isBlank(identificationType.getDescription())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("description");
            errorMessageBuilder.code("error.required");
            errorMessageBuilder.resolvableArg("Description");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        }

    }

    public void validateEditEntity(IdentificationType identificationType, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        logger.info("vlor del id:" + identificationType.getId());

        IdentificationType identificationTypeOnDataBase = identificationTypeService.findById(identificationType.getId());

        logger.info("test " + identificationType.toString());

        logger.info("from database " + identificationTypeOnDataBase.toString());

        if (identificationTypeOnDataBase.equals(identificationType)) {
            // logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ya existe ");

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("global");

            errorMessageBuilder.code("error.identificationType.noChangesEntity");

            // errorMessageBuilder.resolvableArg("Identification name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            // validationContext.getMessageContext().addMessage( new
            // MessageBuilder().error().defaultText("No room is available at
            // this hotel").build());

            validationContext.getMessageContext().addMessage(new MessageBuilder().error().code("error.identificationType.noChangesEntity").build());

            return;
        }

        if (StringUtils.isBlank(identificationType.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();

            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Identification name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            return;

        }

        if (StringUtils.isBlank(identificationType.getMnemonic())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("mnemonic");
            errorMessageBuilder.code("error.required");
            errorMessageBuilder.resolvableArg("Identification mnemonic");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            return;

        }

        logger.info("++++++++++++++++++++++++++++++++ingreso a la validacion");

        identificationType.setName(StringUtils.capitalize(identificationType.getName().toLowerCase()));
        identificationType.setMnemonic(identificationType.getMnemonic().toUpperCase());

        IdentificationType identification = identificationTypeService.findOneByIdentificationType(identificationType, 1, 1);
        // List<IdentificationType> identifications =
        // identificationTypeService.findByName(identificationType.getName(), 1,
        // 1);
        
        logger.info("********************************************************");

        logger.info(identificationType);
        
        logger.info("--------------------------------------------------------");

        
        logger.info(identification);

        
        logger.info("-++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        
        

        if (identification != null && identification.getName() != null) {

            if (identificationType.getName().equals(identification.getName())) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("name");
                errorMessageBuilder.code("error.identificationType.duplicateName");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

            if (identificationType.getMnemonic().equals(identification.getMnemonic())) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("mnemonic");
                errorMessageBuilder.code("error.identificationType.duplicateMnemonic");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

        }

        if (StringUtils.isBlank(identificationType.getDescription())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("description");
            errorMessageBuilder.code("error.required");
            errorMessageBuilder.resolvableArg("Description");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        }

    }

    public void validateAddEntityFuncionalDos(IdentificationType identificationType, MessageContext validationContext) {

        logger.info("Se realiza la validacionfffffffffffffffffffffffffffffffffffffffffffffff");
        if (identificationType.getName() == null) {
            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            // errorMessageBuilder.code("error.page.selectdeliveryoptions.deliverydate.required");
            // validationContext.addMessage(errorMessageBuilder.build());
            validationContext.addMessage(errorMessageBuilder.build());
            // return new EventFactorySupport().error(this);
        }
    }

}