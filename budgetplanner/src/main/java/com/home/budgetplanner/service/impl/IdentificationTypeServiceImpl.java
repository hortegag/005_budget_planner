package com.home.budgetplanner.service.impl;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.repository.IdentificationTypeDao;
import com.home.budgetplanner.repository.PagingIdentificationTypeRepository;
import com.home.budgetplanner.service.IdentificationTypeService;

import com.google.common.collect.Lists;

//@Service("identificationTypeService")
@Service
// @Component
// @Scope( value="session")
public class IdentificationTypeServiceImpl implements IdentificationTypeService, Serializable {

    // This bean has to be transient, to save the the service as a variable in
    // the spring web flow.
    // Another option could be create an Action Object with the annotation
    // Component and that declare de service variable with the transient
    // modifier

    @Autowired
    private transient IdentificationTypeDao identificationTypeDao;

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

    // @Override
    // public List<IdentificationType> findAll() {
    // return identificationTypeDao.findAll();
    // }

    @Override
    public List<IdentificationType> findByName(String searchName, int startPosition, int maxResult) {
        return identificationTypeDao.findByName(searchName, startPosition, maxResult);
    }

    public IdentificationType saveNewData() {
        IdentificationType identificationType = new IdentificationType();

        identificationType.setName("Hector");
        identificationType.setDescription("HectorDescription");
        identificationType.setMnemonic("hor");

        return identificationTypeDao.save(identificationType);
        // return "";
    }

    @Override
    public List<Tuple> findByIdentificationType(IdentificationType identificationType, int startPosition, int maxResult) {
        // TODO Auto-generated method stub
        return identificationTypeDao.findByIdentificationType(identificationType, startPosition, maxResult);
    }
    
    @Override
    public IdentificationType findOneByIdentificationType(IdentificationType identificationType, int startPosition, int maxResult) {
        // TODO Auto-generated method stub
        return identificationTypeDao.findOneByIdentificationType(identificationType, startPosition, maxResult);
    }

    @Autowired
    private transient PagingIdentificationTypeRepository pagingIdentificationTypeRepository;

    @Transactional
    public List<IdentificationType> testHector(String name) {
        return pagingIdentificationTypeRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IdentificationType> findAll() {

        return Lists.newArrayList(pagingIdentificationTypeRepository.findAll());
    }

}
