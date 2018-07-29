package com.home.budgetplanner.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.home.budgetplanner.entity.IdentificationType;

@Repository
public class IdentificationTypeRepository {

	@Autowired
	EntityManager em;
	
	
	
	
	public IdentificationType findById(Long id){
		
		return em.find(IdentificationType.class, id);
		
	}

	public static void main(String[] args) {


		

	}

}
