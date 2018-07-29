package com.home.budgetplanner.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.home.budgetplanner.entity.IdentificationType;

public class IdentificationTypeRepository {

	@Autowired
	EntityManager em;
	
	
	
	
	public IdentificationType findById(Long id){
		
		return em.find(IdentificationType.class, id);
		
	}

	public static void main(String[] args) {


		

	}

}
