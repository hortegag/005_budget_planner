package com.home.budgetplanner.repository;

import java.util.List;

import com.home.budgetplanner.entity.People;

public interface PeopleDAO extends BaseDAO<People, Long> {

    public List<People> findPeopleByName(String name);
}
