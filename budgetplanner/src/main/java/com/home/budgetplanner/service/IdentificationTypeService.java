package com.home.budgetplanner.service;

import java.util.List;

import com.home.budgetplanner.entity.IdentificationType;

public interface IdentificationTypeService {

    public IdentificationType findById(Long id);

    public void deleteById(Long id);

    public IdentificationType save(IdentificationType identificationType);
    
    public List<IdentificationType> findAll();
    
    public List<IdentificationType> findByName(String searchName, int startPosition, int maxResult);

}
