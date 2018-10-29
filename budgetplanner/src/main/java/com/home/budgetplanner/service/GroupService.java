package com.home.budgetplanner.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.home.budgetplanner.entity.Groups;

public interface GroupService extends BaseService<Groups, Long> {

    public Page<Groups> findAllByPage(Pageable pageable);
    
    public List<Groups> findAllOrderByIdAsc();

}
