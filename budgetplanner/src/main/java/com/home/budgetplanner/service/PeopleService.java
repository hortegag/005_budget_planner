package com.home.budgetplanner.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.home.budgetplanner.entity.People;

public interface PeopleService extends BaseService<People, Long> {

     
    public List<People> findPeopleByName(String name);
    
    public Page<People> findAllByPage(Pageable pageable);


}
