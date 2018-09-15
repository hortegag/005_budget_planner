package com.home.budgetplanner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.home.budgetplanner.entity.IdentificationType;

public interface PagingIdentificationTypeRepository extends PagingAndSortingRepository<IdentificationType, Long>  {
    
    
    
    List<IdentificationType> findByName(String name);
    
    Page<IdentificationType> findAll(Pageable pageable);
    
    
    List<IdentificationType> findAll();


}
