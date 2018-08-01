package com.home.budgetplanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.repository.IdentificationTypeDao;

@SpringBootApplication
public class BudgetplannerApplication implements CommandLineRunner {

    private static final Logger   logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    private IdentificationTypeDao repository;

    public static void main(String[] args) {
        SpringApplication.run(BudgetplannerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Commented Code because when the unit test is executed, this metodo
        // also is called and delete the records
        // IdentificationType identificationType = repository.findById(1002L);
        // logger.info("test log4j {}", identificationType);

        // repository.deleteById(1002L);
        // logger.info("test log4j {}", identificationType);
        // IdentificationType identificationType = new IdentificationType("My dummy IT", "MIT", "This is a dummmy Identification");
        // repository.save(identificationType);
    }

}
