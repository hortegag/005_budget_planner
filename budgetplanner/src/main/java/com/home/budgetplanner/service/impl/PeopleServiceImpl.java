package com.home.budgetplanner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.repository.PagingPeopleRepository;
import com.home.budgetplanner.repository.PeopleDAO;
import com.home.budgetplanner.service.PeopleService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleDAO              peopleDAO;

    @Autowired
    private PagingPeopleRepository pagingPeopleRepository;

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

}
