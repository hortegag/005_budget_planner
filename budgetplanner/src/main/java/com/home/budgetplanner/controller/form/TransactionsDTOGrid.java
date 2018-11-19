package com.home.budgetplanner.controller.form;

import java.io.Serializable;
import java.util.List;

import com.home.budgetplanner.controller.dtos.TransactionsDTO;


import lombok.Data;

@Data
public class TransactionsDTOGrid implements Serializable {

    
    private int                totalPages;
    private int                currentPage;
    private long               totalRecords;
    private List<TransactionsDTO> transactionsData;

    
}
