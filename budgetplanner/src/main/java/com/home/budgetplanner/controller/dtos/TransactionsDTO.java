package com.home.budgetplanner.controller.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.entity.Transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @NoArgsConstructor
// @AllArgsConstructor
public class TransactionsDTO implements Serializable {

    private Long                          id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate                     transactionDate;

    private String                        transactionDateString;

    private String                        description;

    private String                        currentBalance;

    private String                        value;

    private String                        transactionType;
    private String                        person;
    private String                        transactionTypeId;
    
    private String                        transactionTypeEntryType;

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static TransactionsDTO build(Transactions transactions) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        String text = transactions.getTransactionDate().format(formatter);

        return new TransactionsDTO(transactions.getId(), transactions.getTransactionDate(), text, transactions.getDescription(),
                transactions.getCurrentBalance().toString(), transactions.getValue().toString(), transactions.getTransactionType().getName(),
                transactions.getTransactionType().getId().toString(), transactions.getTransactionType().getEntryType());

    }

    public static Transactions dtoToEntity(TransactionsDTO transactionsDTO, TransactionType transactionType, People people) {

  //    Transactions transactions =  new Transactions(transactionsDTO.getId(), transactionsDTO.getTransactionDate(), transactionsDTO.getDescription(),
  //              new BigDecimal(transactionsDTO.getCurrentBalance()), new BigDecimal(transactionsDTO.getValue()), transactionType, people);
      
      Transactions transactions =  new Transactions(transactionsDTO.getId(), transactionsDTO.getTransactionDate(), transactionsDTO.getDescription(),
              new BigDecimal(0), new BigDecimal(transactionsDTO.getValue()), transactionType, people);


   
        return transactions;

    }


    public TransactionsDTO() {
        super();
    }

    public TransactionsDTO(Long id, LocalDate transactionDate, String transactionDateString, String description, String currentBalance, String value,
            String transactionType, String transactionTypeId, String transactionTypeEntryType) {
        super();
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionDateString = transactionDateString;
        this.description = description;
        this.currentBalance = currentBalance;
        this.value = value;
        this.transactionType = transactionType;
        this.transactionTypeId = transactionTypeId;
        this.transactionTypeEntryType = transactionTypeEntryType;

    }

}