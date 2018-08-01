package com.home.budgetplanner.repository;

import java.util.List;

import com.home.budgetplanner.entity.IdentificationType;

public interface IdentificationTypeDao {

    public IdentificationType findById(Long id);
    
    public void deleteById(Long id);

    

    public List<IdentificationType> getCustomers();

    public void saveCustomer(IdentificationType customer);

    public IdentificationType getCustomer(int id);

    public void deleteCustomer(int theId);

    public List<IdentificationType> searchCustomers(String theSearchName);

}
