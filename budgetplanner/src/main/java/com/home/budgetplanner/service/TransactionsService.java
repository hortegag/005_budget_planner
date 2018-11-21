package com.home.budgetplanner.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.controller.dtos.TransactionsDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.entity.Transactions;

public interface TransactionsService extends BaseService<Transactions, Long> {

    public Page<TransactionsDTO> findTransactionsByDescriptionPage(String description, Pageable pageable);

    public Page<TransactionsDTO> findAllTransactionsByPage(Pageable pageable);

    public Page<TransactionsDTO> findAllTransactionsByPeople(People people, Pageable pageable);

    public Page<TransactionsDTO> findByDescriptionAndPeople(String description, People people, Pageable pageable);
    
    public Transactions  findTopByPeopleOrderByIdDesc(People people);

}
