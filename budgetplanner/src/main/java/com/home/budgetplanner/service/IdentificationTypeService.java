package com.home.budgetplanner.service;

import com.home.budgetplanner.entity.IdentificationType;

public interface IdentificationTypeService {

    public IdentificationType findById(Long id);

    public void deleteById(Long id);

    public IdentificationType save(IdentificationType identificationType);

}
