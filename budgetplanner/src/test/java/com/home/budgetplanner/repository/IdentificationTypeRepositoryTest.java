package com.home.budgetplanner.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.entity.IdentificationType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetplannerApplication.class)
public class IdentificationTypeRepositoryTest {

    private static final Logger logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    IdentificationTypeDao       identificationTypeDao;

    @Test
    public void findById_basic() {

        logger.info("Testing is Running");

        IdentificationType identificationType = identificationTypeDao.findById(1001L);

        assertEquals("Cedula de Identidad de la persona", identificationType.getDescription());
    }

    @Test
    //Dirties context allow you to restablish the data after the test is excecuted. With this option the record is no deleted after
    //the test is executed
    @DirtiesContext
    public void deleteById_basic() {
        identificationTypeDao.deleteById(1002L);
        assertNull(identificationTypeDao.findById(1002L));
    }
    
    
    @Test
    @DirtiesContext
    public void save_basic() {


        IdentificationType identificationType = identificationTypeDao.findById(1001L);

        assertEquals("Cedula de Identidad de la persona", identificationType.getDescription());

        identificationType.setDescription("CI de la persona");
        
        identificationTypeDao.save(identificationType);
        
        assertEquals("CI de la persona", identificationType.getDescription());

    }
}