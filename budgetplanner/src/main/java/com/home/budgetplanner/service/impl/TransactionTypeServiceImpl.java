package com.home.budgetplanner.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.engine.groups.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.repository.PagingTransactionTypeRepository;
import com.home.budgetplanner.repository.PeopleDAO;
import com.home.budgetplanner.service.PeopleService;
import com.home.budgetplanner.service.TransactionTypeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService, Serializable {

    private static final Logger              logger = LogManager.getLogger(TransactionTypeServiceImpl.class);

 

    @Autowired
    private transient PagingTransactionTypeRepository pagingTransactionTypeRepository;

//    @Autowired
//    private transient PasswordEncoder        passwordEncoder;

    @Override
    public TransactionType findById(Long id) {

        logger.info(">>>>>>>>>>>>>>>>>>>>>> se realiza la consulta por id " + id);
        
        return (pagingTransactionTypeRepository.findById(id).isPresent() )? pagingTransactionTypeRepository.findById(id).get():null;
    }


    @Override
    public void deleteById(Long id) {
        pagingTransactionTypeRepository.deleteById(id);

    }

    @Override
    public TransactionType save(TransactionType entity) {


        return pagingTransactionTypeRepository.save(entity);
    }

   
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionTypeDTO> findTransactionsTypeByNameOrDescriptionPage(String name, String description, Pageable pageable) {
    
        
        Page<TransactionType> transactionTypeList = pagingTransactionTypeRepository.findByNameOrDescription(name, description, pageable);

        return new PageImpl<>(transformEntitysInDTOs(transactionTypeList), pageable, transactionTypeList.getTotalElements());
        
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionTypeDTO> findAllTransactionsTypeByPage(Pageable pageable) {

        Page<TransactionType> transactionTypeList = pagingTransactionTypeRepository.findAll(pageable);


        return new PageImpl<>(transformEntitysInDTOs(transactionTypeList), pageable, transactionTypeList.getTotalElements());

    }

    private List<TransactionTypeDTO> transformEntitysInDTOs(Iterable<TransactionType> transactionTypeList) {
        List<TransactionTypeDTO> results = new ArrayList<>();
        for (TransactionType transactionType : transactionTypeList) {
            results.add(TransactionTypeDTO.build(transactionType));
        }
        return results;
    }


    @Override
    public List<TransactionType> findPaginated(int startPosition, int maxResult) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Long count() {
        
        return pagingTransactionTypeRepository.count();
    }


    @Override
    public TransactionType findByName(String name) {
        
        return pagingTransactionTypeRepository.findByName(name);
        
    }


    @Override
    public TransactionType findByNameAndIdNot(String name, Long id) {
        
        return pagingTransactionTypeRepository.findByNameAndIdNot(name, id);
        
        
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<TransactionType> findAllByOrderByIdAsc(){
     
        return pagingTransactionTypeRepository.findAllByOrderByIdAsc();
        
    }


}       
