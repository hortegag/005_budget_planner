package com.home.budgetplanner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.repository.IdentificationTypeDao;
import com.home.budgetplanner.service.IdentificationTypeService;

public class IdentificationTypeServiceImpl implements IdentificationTypeService {

    @Autowired
    private IdentificationTypeDao identificationTypeDao;

    @Override
    public IdentificationType findById(Long id) {
        return identificationTypeDao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        identificationTypeDao.deleteById(id);
    }

    @Override
    public IdentificationType save(IdentificationType identificationType) {
        return identificationTypeDao.save(identificationType);
    }

    @Override
    public List<IdentificationType> findAll() {
        return identificationTypeDao.findAll();
    }

}
