package com.home.budgetplanner.controller.dtos;

import java.io.Serializable;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 @Data
// @NoArgsConstructor
//@AllArgsConstructor
public class TransactionTypeDTO implements Serializable {

    private Long                          id;

    private String                        name;

    private String                        description;

    private String                        entryType;


    public static TransactionTypeDTO build(TransactionType transactionType) {

       return new TransactionTypeDTO(transactionType.getId(), transactionType.getName(), transactionType.getDescription(), transactionType.getEntryType());

    }
    

    public TransactionTypeDTO(Long id, String name, String description, String entryType) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.entryType = entryType;
    }

    public static TransactionType dtoToEntity(TransactionTypeDTO transactionTypeDTO) {
        TransactionType transactionType = new TransactionType(transactionTypeDTO.getId(), transactionTypeDTO.getName(), transactionTypeDTO.getDescription(), transactionTypeDTO.getEntryType());
        return transactionType;

    }


    public TransactionTypeDTO() {
        super();
    }

}