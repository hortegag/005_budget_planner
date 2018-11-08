package com.home.budgetplanner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;

public interface PagingPeopleRepository extends PagingAndSortingRepository<People, Long> {

    List<People> findByName(String name);

    // Page<People> findAll(Pageable pageable);

    // List<People> findAll();

    Page<People> findByNameOrLastName(String name, String lastName, Pageable pageable);
    
    People findByUsername(String username);
    
    People findByIdentificationAndIdentificationType(String identification,IdentificationType identificationType);
    
    People findByIdentificationAndIdentificationTypeAndIdNot(String identification, IdentificationType identificationType, Long id);
    
    
    People findByEmail(String email);

    People findByEmailAndIdNot(String email, Long id);
    
    
    People findByUsernameAndIdNot(String username, Long id);



}
