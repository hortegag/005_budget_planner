package com.home.budgetplanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.repository.IdentificationTypeRepository;

@SpringBootApplication
public class BudgetplannerApplication implements CommandLineRunner {
	
	private IdentificationTypeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BudgetplannerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		IdentificationType identificationType = repository.findById(1L);
		
	}
	
}
