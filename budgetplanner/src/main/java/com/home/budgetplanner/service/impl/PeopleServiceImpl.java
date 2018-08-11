package com.home.budgetplanner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.repository.PeopleDAO;
import com.home.budgetplanner.service.PeopleService;

public class PeopleServiceImpl implements PeopleService {

    
    @Autowired
    private PeopleDAO peopleDAO;
    
    @Override
    public People findById(Long id) {
        // TODO Auto-generated method stub
        return peopleDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        peopleDAO.deleteById(id);
        
    }

    @Override
    public People save(People entity) {
        return peopleDAO.save(entity);
    }

    @Override
    public List<People> findPaginated(int startPosition, int maxResult) {
        return peopleDAO.findPaginated(startPosition, maxResult);
    }

    @Override
    public Long count() {
        return peopleDAO.count();
    }

    @Override
    public List<People> findPeopleByName(String name) {
        return peopleDAO.findPeopleByName(name);
    }

}
