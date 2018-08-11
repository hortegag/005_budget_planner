package com.home.budgetplanner.service;


import java.util.List;

import com.home.budgetplanner.entity.People;

public interface PeopleService extends BaseService<People, Long> {

     
    public List<People> findPeopleByName(String name);

}
