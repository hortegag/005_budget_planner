package com.home.budgetplanner.validator;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.home.budgetplanner.controller.dtos.TransactionsDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.entity.Transactions;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.service.IdentificationTypeService;
import com.home.budgetplanner.service.PeopleService;
import com.home.budgetplanner.service.TransactionTypeService;
import com.home.budgetplanner.service.TransactionsService;

@Component
public class TransactionValidator {

    private static final Logger    logger = LogManager.getLogger(TransactionValidator.class);

    @Autowired
    private PeopleService          peopleService;

    @Autowired
    private TransactionTypeService transactionTypeService;
    
    @Autowired
    private TransactionsService transactionService;

    public TransactionValidator() {
        logger.info("============================================================================TransactionValidator created");

    }

    public void validateAddEntity(TransactionsDTO transactionsDTO, ValidationContext validationContext) {

        // This one is being called automatically by spring web flow
        logger.info(" ******************************__________________================================================================");
        
        
        
        
        if (transactionsDTO.getTransactionDate() == null) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("transactionDate");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Transaction Date");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } 
        
        

        if (StringUtils.isBlank(transactionsDTO.getDescription())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("description");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Description");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());

        } else {

            transactionsDTO.setDescription((transactionsDTO.getDescription().toUpperCase()));
        }

        if (StringUtils.isBlank(transactionsDTO.getValue())) {

            MessageBuilder errorMessageBuilder = new MessageBuilder().error();
            errorMessageBuilder.source("value");
            errorMessageBuilder.code("error.required");

            errorMessageBuilder.resolvableArg("Value");
            validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
        } else {
            
            
            if ( NumberUtils.isParsable(transactionsDTO.getValue()) ) {
                BigDecimal value = new BigDecimal(transactionsDTO.getValue());
                value = value.abs();

            } else {
                
                MessageBuilder errorMessageBuilder = new MessageBuilder().error();
                errorMessageBuilder.source("value");
                errorMessageBuilder.code("error.transaction.validNumber");

                validationContext.getMessageContext().addMessage(errorMessageBuilder.build());
                
            }
                
            
            
           // String userName = SecurityContextHolder.getContext().getAuthentication().getName();

           // People people = peopleService.findByUsername(userName);
            
            //Transactions transaction = transactionService.findTopByPeopleOrderByIdDesc(people);
            
            
            
            

            
            //if (transactionsDTO.getTransactionTypeEntryType().equals("DEBIT")){
            //    value = value.multiply(new BigDecimal(-1));
            //}
            
            
            
            //It is better to manage the current Balance in the people entity because, the user can change to debits o credits. and that is going to affect the balance each time
            //the user makes an update.
            //transactionsDTO.setCurrentBalance( String.valueOf(
            //transaction.getCurrentBalance().add(  value  ) ));
            
           // transactionsDTO.setDescription((transactionnTypeDTO.getDescription().toUpperCase()));
            
            

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