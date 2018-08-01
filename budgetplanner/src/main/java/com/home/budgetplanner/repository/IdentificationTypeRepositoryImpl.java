package com.home.budgetplanner.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.home.budgetplanner.entity.IdentificationType;

@Repository
@Transactional
public class IdentificationTypeRepositoryImpl implements IdentificationTypeDao {

    @Autowired
    EntityManager em;

    public IdentificationType findById(Long id) {

        return em.find(IdentificationType.class, id);

    }

    public static void main(String[] args) {

    }

    @Override
    public List<IdentificationType> getCustomers() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public IdentificationType getCustomer(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCustomer(int theId) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<IdentificationType> searchCustomers(String theSearchName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Long id) {
        IdentificationType identificationType = findById(id);
        em.remove(identificationType);
        
    }
    
    @Override
    public IdentificationType save(IdentificationType identificationType) {
        
        if (identificationType.getId() == null){
            em.persist(identificationType);
        } else {
            em.merge(identificationType);
        }
        return identificationType;
    }

}
