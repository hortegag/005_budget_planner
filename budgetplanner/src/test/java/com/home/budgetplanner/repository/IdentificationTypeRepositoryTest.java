package com.home.budgetplanner.repository;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.entity.IdentificationType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetplannerApplication.class)
public class IdentificationTypeRepositoryTest {

    private static final Logger logger = LogManager.getLogger(BudgetplannerApplication.class);
    
    @Autowired
    IdentificationTypeDao identificationTypeDao;

    @Test
    public void findById_basic() {
        
        logger.info("Testing is Running");
        
        IdentificationType identificationType = identificationTypeDao.findById(1001L);
        
        assertEquals("Cedula de Identidad de la persona", identificationType.getDescription());
    }

}
