package com.home.budgetplanner.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.engine.groups.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.controller.dtos.TransactionsDTO;
import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.entity.Transactions;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.repository.PagingTransactionTypeRepository;
import com.home.budgetplanner.repository.PagingTransactionsRepository;
import com.home.budgetplanner.repository.PeopleDAO;
import com.home.budgetplanner.service.PeopleService;
import com.home.budgetplanner.service.TransactionTypeService;
import com.home.budgetplanner.service.TransactionsService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionsServiceImpl implements TransactionsService, Serializable {

    private static final Logger              logger = LogManager.getLogger(TransactionsServiceImpl.class);

 

    @Autowired
    private transient PagingTransactionsRepository pagingTransactionsRepository;

//    @Autowired
//    private transient PasswordEncoder        passwordEncoder;

    @Override
    public Transactions findById(Long id) {

        logger.info(">>>>>>>>>>>>>>>>>>>>>> se realiza la consulta por id " + id);
        
        
         Optional<Transactions> transactionType = pagingTransactionsRepository.findById(id);
         
        
        return ( transactionType.isPresent()  )? transactionType.get() :null;
    }


    @Override
    public void deleteById(Long id) {
        pagingTransactionsRepository.deleteById(id);

    }

    @Override
    @Transactional

    public Transactions save(Transactions entity) {

        return pagingTransactionsRepository.save(entity);
    }

   
    @Override
    @Transactional(readOnly = true)
    
    public Page<TransactionsDTO> findTransactionsByDescriptionPage(String description, Pageable pageable) {
    
        
        Page<Transactions> transactionsList = pagingTransactionsRepository.findByDescription(description, pageable);
        return new PageImpl<>(transformEntitysInDTOs(transactionsList), pageable, transactionsList.getTotalElements());
        
    }
    
    
    
    @Override
    @Transactional(readOnly = true)
    
    public Page<TransactionsDTO> findByDescriptionAndPeople(String description, People people, Pageable pageable) {
    
        Page<Transactions> transactionsList = pagingTransactionsRepository.findByDescriptionAndPeople(description, people, pageable);
        return new PageImpl<>(transformEntitysInDTOs(transactionsList), pageable, transactionsList.getTotalElements());
        
    }
    
    
    @Override
    @Transactional(readOnly = true)

    public Page<TransactionsDTO> findAllTransactionsByPeople(People people, Pageable pageable) {
    
//        Page<Transactions> transactionsList = pagingTransactionsRepository.findAllTransactionsByPeople(people, pageable);
        
        Page<Transactions> transactionsList = pagingTransactionsRepository.findByPeople(people, pageable);
        
        
        
        return new PageImpl<>(transformEntitysInDTOs(transactionsList), pageable, transactionsList.getTotalElements());
        
    }
    
    
    
    

    @Override
    @Transactional(readOnly = true)
    
    public Page<TransactionsDTO> findAllTransactionsByPage(Pageable pageable) {

        Page<Transactions> transactionsList = pagingTransactionsRepository.findAll(pageable);
        return new PageImpl<>(transformEntitysInDTOs(transactionsList), pageable, transactionsList.getTotalElements());

    }

    private List<TransactionsDTO> transformEntitysInDTOs(Iterable<Transactions> transactionsList) {
        List<TransactionsDTO> results = new ArrayList<>();
        for (Transactions transactions : transactionsList) {
            results.add(TransactionsDTO.build(transactions));
        }
        return results;
    }





    @Override
    public Long count() {
        
        return pagingTransactionsRepository.count();
    }


    @Override
    public List<Transactions> findPaginated(int startPosition, int maxResult) {
        // TODO Auto-generated method stub
        return null;
    }


 

    public Transactions  findTopByPeopleOrderByIdDesc(People people){
        
        
        
        
        
        return pagingTransactionsRepository.findTopByPeopleOrderByIdDesc(people);
        
    }
    
    
//    @Transactional()
//     public Transactions saveTest(TransactionsDTO transactionsDTO) {
//        
//        
//        
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        People people = peopleService.findByUsername(userName);
//
//        TransactionType transactionType = transactionTypeService.findByName(transactionsDTO.getTransactionType());
//        
//        Transactions transaction = TransactionsDTO.dtoToEntity(transactionsDTO, transactionType, people);
//
//        transactionsService.save(transaction);
//        
//
//        return pagingTransactionsRepository.save(entity);
//    }



 
}       
