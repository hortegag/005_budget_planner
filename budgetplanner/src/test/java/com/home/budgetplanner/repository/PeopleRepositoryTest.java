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
import com.home.budgetplanner.entity.People;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetplannerApplication.class)
public class PeopleRepositoryTest {

    private static final Logger logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    PeopleDAO peopleDao;

    @Test
    public void findById_basic() {

        logger.info("Testing is Running");

        People people = peopleDao.findById(2003L);

        assertEquals("Hector", people.getName());
    }

    @Test
    //Dirties context allow you to restablish the data after the test is excecuted. With this option the record is no deleted after
    //the test is executed
    @DirtiesContext
    public void deleteById_basic() {
        peopleDao.deleteById(2004L);
        assertNull(peopleDao.findById(2004L));
    }
    
    
    @Test
    @DirtiesContext
    public void save_basic() {


        People people = peopleDao.findById(2003L);
        
        logger.info("{}",people);
        assertEquals("Hector", people.getName());

        people.setName("Juanita");
        
        peopleDao.save(people);
        
        assertEquals("Juanita", people.getName());

    }
}