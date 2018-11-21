package com.home.budgetplanner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.entity.Transactions;

public interface PagingTransactionsRepository extends PagingAndSortingRepository<Transactions, Long> {


    Page<Transactions> findByDescription(String description, Pageable pageable);
    
    Page<Transactions> findByDescriptionAndPeople(String description, People people, Pageable pageable);
    
  //  Page<Transactions> findAllTransactionsByPeople(People people, Pageable pageable);
    
    
    Page<Transactions> findByPeople(People people, Pageable pageable);
    
    Transactions findTopByPeopleOrderByIdDesc(People people);
    

}
