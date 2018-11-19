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
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.service.IdentificationTypeService;
import com.home.budgetplanner.service.PeopleService;
import com.home.budgetplanner.service.TransactionTypeService;

@Component
public class TransactionTypeValidator /* implements Validator */ {

    private static final Logger    logger = LogManager.getLogger(TransactionTypeValidator.class);

    @Autowired
    private PeopleService          peopleService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    public TransactionTypeValidator() {
        logger.info("============================================================================PeopleValidator created");

    }

    public void validateAddEntity(TransactionTypeDTO transactionnTypeDTO, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        if (StringUtils.isBlank(transactionnTypeDTO.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            transactionnTypeDTO.setName((transactionnTypeDTO.getName().toUpperCase()));

            TransactionType transactionTypeEntity = transactionTypeService.findByName(transactionnTypeDTO.getName());

            if (transactionTypeEntity != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("name");
                errorMessageBuilder.code("error.transactionType.duplicateName");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

            }

        }

        if (StringUtils.isBlank(transactionnTypeDTO.getDescription())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("description");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Description");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            transactionnTypeDTO.setDescription((transactionnTypeDTO.getDescription().toUpperCase()));

        }

        if (StringUtils.isBlank( transactionnTypeDTO.getEntryType())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("entryType");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Entry Type");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            transactionnTypeDTO.setEntryType(transactionnTypeDTO.getEntryType().toUpperCase());

        }
        

    }

    public void validateEditEntity(TransactionTypeDTO transactionTypeDTO, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" Se realiza la validacion");

        logger.info("vlor del id:" + transactionTypeDTO.getId());

        TransactionType transactionTypeOnDataBase = transactionTypeService.findById(transactionTypeDTO.getId());

        TransactionTypeDTO transactionTypeOnDataBaseDto = TransactionTypeDTO.build(transactionTypeOnDataBase);

        //logger.info("test " + peopleService.toString());

        logger.info("from database " + transactionTypeOnDataBaseDto.toString());

        if (transactionTypeOnDataBaseDto.equals(transactionTypeDTO)) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
 
            validationContext.getMessageContext().addMessage(new MessageBuilder().error().code("error.transactionType.noChangesEntity").build());

            return;
        }
        

        if (StringUtils.isBlank(transactionTypeDTO.getName())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("name");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Name");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            transactionTypeDTO.setName((transactionTypeDTO.getName().toUpperCase()));

            
            TransactionType transactionTypOnDataBase = transactionTypeService.findByNameAndIdNot(transactionTypeDTO.getName(), transactionTypeDTO.getId());
  

            if (transactionTypOnDataBase != null) {

                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("name");
                errorMessageBuilder.code("error.transactionType.duplicateName");
                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            }

        }

        if (StringUtils.isBlank(transactionTypeDTO.getDescription())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("description");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Description");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
            
        } else {
            
            transactionTypeDTO.setDescription(transactionTypeDTO.getDescription().toUpperCase());
            
        }

        if (StringUtils.isBlank(transactionTypeDTO.getEntryType())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("entryType");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Entry Type");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            
            
            
            
            transactionTypeDTO.setEntryType(transactionTypeDTO.getEntryType().toUpperCase());
            
            
            if ( ! transactionTypeOnDataBaseDto.getEntryType().equals(transactionTypeDTO.getEntryType()) ) {
                
               //TODO: validar si existen transacciones asociadas y no permitir el la actualizacion:
                
                
            }
            
        }

        

    }

}