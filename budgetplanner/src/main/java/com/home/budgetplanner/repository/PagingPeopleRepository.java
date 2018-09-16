package com.home.budgetplanner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.home.budgetplanner.entity.People;

public interface PagingPeopleRepository extends PagingAndSortingRepository<People, Long>  {
    
    
    
    List<People> findByName(String name);
    
   // Page<People> findAll(Pageable pageable);
    
    
    //List<People> findAll();


}
