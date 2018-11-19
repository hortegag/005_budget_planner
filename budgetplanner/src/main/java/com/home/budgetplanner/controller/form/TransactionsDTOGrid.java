package com.home.budgetplanner.controller.form;

import java.io.Serializable;
import java.util.List;

import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.Transactions;

import lombok.Data;

@Data
public class TransactionsDTOGrid implements Serializable {

    private int                totalPages;
    private int                currentPage;
    private long               totalRecords;
    private List<Transactions> transactionsData;

}
