package com.home.budgetplanner.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.repository.PagingGroupsRepository;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.service.GroupService;
import com.home.budgetplanner.service.PeopleService;

@Service
public class GroupServiceImpl implements GroupService, Serializable {

    @Autowired
    private PagingGroupsRepository pagingGroupsRepository;

    @Override
    public Groups findById(Long id) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Groups save(Groups entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Groups> findPaginated(int startPosition, int maxResult) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long count() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Groups> findAllByPage(Pageable pageable) {
        return pagingGroupsRepository.findAll(pageable);

    }

    @Override
    public List<Groups> findAllOrderByIdAsc() {
        // TODO Auto-generated method stub
        return pagingGroupsRepository.findAllByOrderByIdAsc();
    }

}
