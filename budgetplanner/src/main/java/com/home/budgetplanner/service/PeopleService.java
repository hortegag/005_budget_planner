package com.home.budgetplanner.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;

public interface PeopleService extends BaseService<People, Long> {

    public List<People> findPeopleByName(String name);

    public Page<People> findAllByPage(Pageable pageable);

    public Page<People> findByNameOrLastNamePage(String name, String lastName, Pageable pageable);

    public Page<PeopleDTO> findPeopleByNameOrLastNamePage(String name, String lastName, Pageable pageable);

    public Page<PeopleDTO> findAllPeopleByPage(Pageable pageable);
    
    public People findByIdentificationAndIdentificationType(String identification, IdentificationType identificationType);
    
    
    public People findByUsername(String username);
    
   
    public People findByIdentificationAndIdentificationTypeAndIdNot(String identification, IdentificationType identificationType, Long id);


    public People findByEmail(String email);

    public People findByEmailAndIdNot(String email, Long id);
        
    public People findByUsernameAndIdNot(String username, Long id);
    
}
