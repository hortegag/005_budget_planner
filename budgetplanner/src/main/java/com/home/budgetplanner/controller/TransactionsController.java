package com.home.budgetplanner.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.webflow.execution.RequestContext;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.controller.dtos.DataDTO;
import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.controller.dtos.TransactionsDTO;
import com.home.budgetplanner.controller.form.DataDTOGrid;
import com.home.budgetplanner.controller.form.PeopleDTOGrid;
import com.home.budgetplanner.controller.form.PeopleGrid;
import com.home.budgetplanner.controller.form.SearchPeopleForm;
import com.home.budgetplanner.controller.form.SearchStatisticsOfTransactionsForm;
import com.home.budgetplanner.controller.form.SearchTransactionTypeForm;
import com.home.budgetplanner.controller.form.SearchTransactionsForm;
import com.home.budgetplanner.controller.form.TransactionTypeDTOGrid;
import com.home.budgetplanner.controller.form.TransactionsDTOGrid;
import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.entity.Transactions;
import com.home.budgetplanner.service.GroupService;
import com.home.budgetplanner.service.IdentificationTypeService;
import com.home.budgetplanner.service.PeopleService;
import com.home.budgetplanner.service.TransactionTypeService;
import com.home.budgetplanner.service.TransactionsService;
import com.home.budgetplanner.utils.PageWrapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/transactions")
public class TransactionsController {

    private static final Logger    logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    private PeopleService          peopleService;

    @Autowired
    private TransactionsService    transactionsService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @ResponseBody
    @RequestMapping(value = "/listGridByNameAndDescription", method = RequestMethod.GET, produces = "application/json")
    public TransactionsDTOGrid listGridByNameAndLastName(SearchTransactionsForm searchForm) {

        logger.info("Listing contacts for grid with page: {}, rows: {}", searchForm.getPage(), searchForm.getRows());
        logger.info("Listing contacts for grid with sort: {}, order: {}", searchForm.getSidx(), searchForm.getSord());

        // Process order by
        Sort sort = null;
        String orderBy = searchForm.getSidx();
        // if (orderBy != null && orderBy.equals("bornDateString"))
        // orderBy = "bornDate";

        if (orderBy != null && searchForm.getSord() != null) {
            if (searchForm.getSord().equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid
        // starts with 1
        PageRequest pageRequest = null;

        if (sort != null) {

            pageRequest = PageRequest.of(searchForm.getPage() - 1, searchForm.getRows(), sort);
        } else {
            pageRequest = PageRequest.of(searchForm.getPage() - 1, searchForm.getRows());

        }

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        People people = peopleService.findByUsername(userName);

        Page<TransactionsDTO> contactPage;
        // Page<People> contactPage = peopleService.findAllByPage(pageRequest);
        // if (StringUtils.isBlank(searchForm.getName()) &&
        // StringUtils.isBlank(searchForm.getDescription())) {
        if (StringUtils.isBlank(searchForm.getDescription())) {

            // contactPage =
            // transactionsService.findAllTransactionsByPage(pageRequest);
            contactPage = transactionsService.findAllTransactionsByPeople(people, pageRequest);

        } else {

            // contactPage =
            // transactionsService.findTransactionsByDescriptionPage(searchForm.getDescription(),
            // pageRequest);
            contactPage = transactionsService.findByDescriptionAndPeople(searchForm.getDescription(), people, pageRequest);

        }

        // Construct the grid data that will return as JSON data
        TransactionsDTOGrid contactGrid = new TransactionsDTOGrid();

        contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        contactGrid.setTotalPages(contactPage.getTotalPages());
        contactGrid.setTotalRecords(contactPage.getTotalElements());

        contactGrid.setTransactionsData(Lists.newArrayList(contactPage.iterator()));

        return contactGrid;
    }

    public Pageable initializePageable() {

        Pageable pageable = PageRequest.of(0, 5);
        return pageable;
    }

    public TransactionsDTO initializeTransactionDTO() {

        TransactionsDTO transactionsDTO = new TransactionsDTO();

        return transactionsDTO;
    }

    public List<TransactionType> initializeSelectableTransactionTypes() {

        return transactionTypeService.findAllByOrderByIdAsc();

    }

    // public TransactionTypeDTO findById(Long id) {

    // TransactionTypeDTO transactionTypeDTO =
    // TransactionTypeDTO.build(transactionTypeService.findById(new Long(id)));

    // return transactionTypeDTO;

    // }

    public void save(TransactionsDTO transactionsDTO) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        People people = peopleService.findByUsername(userName);
        
        TransactionType transactionType = transactionTypeService.findByName(transactionsDTO.getTransactionType());
     
        
       
        
       // if (transactionsDTO.getTransactionTypeEntryType().equals("DEBIT")) {
        if ( transactionType.getEntryType().equals("DEBIT")) {
            
            
            people.setExpense( people.getExpense().add(new BigDecimal(transactionsDTO.getValue())) );
            people.setCurrentBalance( people.getCurrentBalance().subtract(new BigDecimal(transactionsDTO.getValue())) );
            
            
        } else {
            
            people.setIncome(  people.getIncome().add(new BigDecimal(transactionsDTO.getValue()))  ); 
            
            people.setCurrentBalance( people.getCurrentBalance().add(new BigDecimal(transactionsDTO.getValue())) );
            
            

        }

        
        peopleService.save(people);

        
        Transactions transaction = TransactionsDTO.dtoToEntity(transactionsDTO, transactionType, people);

        transactionsService.save(transaction);

    }
    
    
   
    
    
    
    public People initializePeople(){
        
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        People people = peopleService.findByUsername(userName);
        
        return people;
        
    }

   // public People save(People people) {

        //return peopleService.save(people);
    //}
    
    
    
    public TransactionsDTO findById(Long id) {

        TransactionsDTO transactionsDTO = TransactionsDTO.build(transactionsService.findById(new Long(id)));

        return transactionsDTO;

    }
    
    
    public List<TransactionType> initializeSelectableTransactionTypesByEntry(String entryType) {

        return transactionTypeService.findAllByEntryTypeOrderByIdAsc(entryType);

    }
    
    
    
    
    
    public void update(TransactionsDTO transactionsDTO) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        People people = peopleService.findByUsername(userName);
        
        TransactionType transactionType = transactionTypeService.findByName(transactionsDTO.getTransactionType());
     
       // Transactions transaction = new Transactions();
        Transactions transaction = transactionsService.findById(transactionsDTO.getId());

        
        
        
        //transaction.getValue();
      
        BigDecimal valueBd = new BigDecimal( transactionsDTO.getValue());
    //    transaction.getValue().compareTo(valueBd  );
        
       if (  transaction.getValue().compareTo(valueBd  ) !=0 ) {
           
           if ( transactionType.getEntryType().equals("DEBIT")) {
               people.setExpense(people.getExpense().subtract(transaction.getValue()));
               people.setCurrentBalance( people.getCurrentBalance().add (transaction.getValue() )) ;

               
               people.setExpense( people.getExpense().add(new BigDecimal(transactionsDTO.getValue())) );
               people.setCurrentBalance( people.getCurrentBalance().subtract(new BigDecimal(transactionsDTO.getValue())) );
               
           } else {
               
               
               people.setIncome(  people.getIncome().subtract( transaction.getValue()  )  ); 
               
               people.setCurrentBalance( people.getCurrentBalance().subtract(   transaction.getValue()   ) );
         
               
               people.setIncome(  people.getIncome().add(new BigDecimal(transactionsDTO.getValue()))  ); 
               
               people.setCurrentBalance( people.getCurrentBalance().add(new BigDecimal(transactionsDTO.getValue())) );
            
               
               
           }
         
       }
        
        
        peopleService.save(people);

        
        Transactions transactionToStore = TransactionsDTO.dtoToEntity(transactionsDTO, transactionType, people);

        transactionsService.save(transactionToStore);

    }
    
    
    
    
    public void delete(TransactionsDTO transactionsDTO) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        People people = peopleService.findByUsername(userName);
        
        TransactionType transactionType = transactionTypeService.findByName(transactionsDTO.getTransactionType());
     
       // Transactions transaction = new Transactions();
        Transactions transaction = transactionsService.findById(transactionsDTO.getId());

        
        
        
        //transaction.getValue();
      
     //   BigDecimal valueBd = new BigDecimal( transactionsDTO.getValue());
    //    transaction.getValue().compareTo(valueBd  );
        
     //  if (  transaction.getValue().compareTo(valueBd  ) !=0 ) {
           
           if ( transactionType.getEntryType().equals("DEBIT")) {
               people.setExpense(people.getExpense().subtract(transaction.getValue()));
               people.setCurrentBalance( people.getCurrentBalance().add (transaction.getValue() )) ;

               
              // people.setExpense( people.getExpense().add(new BigDecimal(transactionsDTO.getValue())) );
              // people.setCurrentBalance( people.getCurrentBalance().subtract(new BigDecimal(transactionsDTO.getValue())) );
               
           } else {
               
               
               people.setIncome(  people.getIncome().subtract( transaction.getValue()  )  ); 
               
               people.setCurrentBalance( people.getCurrentBalance().subtract(   transaction.getValue()   ) );
         
               
               //people.setIncome(  people.getIncome().add(new BigDecimal(transactionsDTO.getValue()))  ); 
               
               //people.setCurrentBalance( people.getCurrentBalance().add(new BigDecimal(transactionsDTO.getValue())) );
            
               
               
           }
         
       //}
        
        
        peopleService.save(people);

        
        transactionsService.deleteById(transactionsDTO.getId());
        
        //Transactions transactionToStore = TransactionsDTO.dtoToEntity(transactionsDTO, transactionType, people);

        //transactionsService.save(transactionToStore);

    }
    
    
    
    @ResponseBody
    @RequestMapping(value = "/listGridD3", method = RequestMethod.GET, produces = "application/json")
    //public List<DataDTO> testD3(){
    //public DataDTOGrid testD3(){
    public List<DataDTOGrid> testD3(){
        
        List<DataDTO> lista = new ArrayList<>();
        
        
        DataDTO first = new DataDTO();
        
        first.setLabel("y");
        first.setValue(new BigDecimal("10.59"));
        lista.add(first);
        

        DataDTO second = new DataDTO();
        
        
        
        second.setLabel("z");
        second.setValue(new BigDecimal("03.20"));  
        lista.add(second);

       
        List<DataDTO> test   = transactionsService.findTransactionSum();
        logger.info("**************************************************************************");
        logger.info(test);
        
        
        DataDTOGrid dataDTOGrid = new DataDTOGrid();
        
        //dataDTOGrid.setValues(lista);
        
        dataDTOGrid.setValues(test);
        
        
        
        List<DataDTOGrid> listaValues = new ArrayList<>();
        
        listaValues.add(dataDTOGrid);
        return listaValues;
        
       // return lista;
       // return dataDTOGrid;
        
    }
    
    
    

    @ResponseBody
    @RequestMapping(value = "/listGridD3Pie", method = RequestMethod.GET, produces = "application/json")
    //public List<DataDTO> testD3(){
    //public DataDTOGrid testD3(){
    public List<DataDTO> testD3Pie(){
        
        
        
        List<DataDTO> test   = transactionsService.findTransactionSum();
        logger.info("**************************************************************************");
        logger.info(test);
        
        List<DataDTO> lista = new ArrayList<>();
        
        
        DataDTO first = new DataDTO();
        
        first.setLabel("y");
        first.setValue(new BigDecimal("20.59"));
        lista.add(first);
        

        DataDTO second = new DataDTO();
        
        
        
        second.setLabel("z");
        second.setValue(new BigDecimal("40.20"));  
        lista.add(second);

       
        
        
        
        DataDTOGrid dataDTOGrid = new DataDTOGrid();
        
        dataDTOGrid.setValues(lista);
        
        
        List<DataDTOGrid> listaValues = new ArrayList<>();
        
        listaValues.add(dataDTOGrid);
       // return listaValues;
        
        return test;
       // return dataDTOGrid;
        
    }
    
    
    
    
    @ResponseBody
    @RequestMapping(value = "/listTransactionsByEntryType", method = RequestMethod.GET, produces = "application/json")
    //public List<DataDTO> testD3(){
    //public DataDTOGrid testD3(){
    public List<DataDTOGrid> findTransactionSumByEntryType(SearchStatisticsOfTransactionsForm searchStatisticsOfTransactionsForm){
        
        List<DataDTO> lista = new ArrayList<>();
        
        
       
        List<DataDTO> test   = transactionsService.findTransactionSumByEntryType(searchStatisticsOfTransactionsForm.getEntryType());
       
        
        
        DataDTOGrid dataDTOGrid = new DataDTOGrid();
        
        //dataDTOGrid.setValues(lista);
        
        dataDTOGrid.setValues(test);
        
        
        
        List<DataDTOGrid> listaValues = new ArrayList<>();
        
        listaValues.add(dataDTOGrid);
        
        
        logger.info("////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        String date = "2018-11-01";

        //default, ISO_LOCAL_DATE 
        LocalDate startDate = LocalDate.parse(date);
        
        String endDate = "2018-11-30";

        //default, ISO_LOCAL_DATE 
        LocalDate endLocalDate = LocalDate.parse(endDate);
     
        List<Object[][]> listByDay   = transactionsService.findTransactionSumByEntryTypeAndDay(searchStatisticsOfTransactionsForm.getEntryType(),startDate, endLocalDate);

        
        logger.info("//////////prueba group//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        logger.info(listByDay.size());

        logger.info(listByDay);
        
        //for (Object[][] pruebaGroup : listByDay){
            
            for (Object[] maxAvgSalary : listByDay) {
                logger.info("max avg salary: " + maxAvgSalary[0]);
                System.out.println("min avg salary: " + maxAvgSalary[1]);
            }
            
        //}
        
       
       // logger.info(pruebaGroup);
        
        
        
        return listaValues;
        
       // return lista;
       // return dataDTOGrid;
        
    }
    
    
    

}
