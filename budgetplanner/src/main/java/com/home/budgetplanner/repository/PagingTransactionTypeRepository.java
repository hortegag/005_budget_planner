package com.home.budgetplanner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;

public interface PagingTransactionTypeRepository extends PagingAndSortingRepository<TransactionType, Long> {


    Page<TransactionType> findByNameOrDescription(String name, String description, Pageable pageable);
    
    TransactionType findByName(String name);
    
    TransactionType findByNameAndIdNot(String name, Long id);
    
    List<TransactionType> findAllByOrderByIdAsc();
    
    List<TransactionType> findAllByEntryTypeOrderByIdAsc(String entryType);



     

}
