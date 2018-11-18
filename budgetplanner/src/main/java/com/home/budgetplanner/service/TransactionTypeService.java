package com.home.budgetplanner.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;

public interface TransactionTypeService extends BaseService<TransactionType, Long> {


    public Page<TransactionTypeDTO> findTransactionsTypeByNameOrDescriptionPage(String name, String description, Pageable pageable);

    public Page<TransactionTypeDTO> findAllTransactionsTypeByPage(Pageable pageable);
    
    
    
}
