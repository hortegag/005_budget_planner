package com.home.budgetplanner.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.repository.PeopleDAO;
import com.home.budgetplanner.service.PeopleService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeopleServiceImpl implements PeopleService, Serializable {

    private static final Logger              logger = LogManager.getLogger(PeopleServiceImpl.class);

    @Autowired
    private transient PeopleDAO              peopleDAO;

    @Autowired
    private transient PagingPeopleRepository pagingPeopleRepository;

    @Autowired
    private transient PasswordEncoder        passwordEncoder;

    @Override
    public People findById(Long id) {

        logger.info(">>>>>>>>>>>>>>>>>>>>>> se realiza la consulta por id " + id);
        // TODO Auto-generated method stub
        return peopleDAO.findById(id);
    }

    public People findByTestId(Long id) {

        logger.info("fffffffffffffffffffffffff se realiza la consulta por id " + id);
        // TODO Auto-generated method stub
        return peopleDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        peopleDAO.deleteById(id);

    }

    @Override
    public People save(People entity) {

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

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

    @Override
    @Transactional(readOnly = true)
    public Page<People> findAllByPage(Pageable pageable) {

        return pagingPeopleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<People> findByNameOrLastNamePage(String name, String lastName, Pageable pageable) {
        return pagingPeopleRepository.findByNameOrLastName(name, lastName, pageable);
    }

    @Override

    @Transactional(readOnly = true)
    public Page<PeopleDTO> findPeopleByNameOrLastNamePage(String name, String lastName, Pageable pageable) {
        // return pagingPeopleRepository.findByNameOrLastName(name, lastName,
        // pageable);

        Page<People> peopleList = pagingPeopleRepository.findByNameOrLastName(name, lastName, pageable);

        return new PageImpl<>(transformTypedProductsInDTOs(peopleList), pageable, peopleList.getTotalElements());

    }

    @Override
    @Transactional(readOnly = true)
    public Page<PeopleDTO> findAllPeopleByPage(Pageable pageable) {

        Page<People> peopleList = pagingPeopleRepository.findAll(pageable);

        // return pagingPeopleRepository.findAll(pageable);

        return new PageImpl<>(transformTypedProductsInDTOs(peopleList), pageable, peopleList.getTotalElements());

    }

    private List<PeopleDTO> transformTypedProductsInDTOs(Iterable<People> peopleList) {
        List<PeopleDTO> results = new ArrayList<>();
        for (People people : peopleList) {
            results.add(PeopleDTO.build(people));
        }
        return results;
    }

    public People findByIdentificationAndIdentificationType(String identification, IdentificationType identificationType) {

        return pagingPeopleRepository.findByIdentificationAndIdentificationType(identification, identificationType);

    }

}
