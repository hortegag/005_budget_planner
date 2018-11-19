package com.home.budgetplanner.controller;

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
import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.controller.dtos.TransactionsDTO;
import com.home.budgetplanner.controller.form.PeopleDTOGrid;
import com.home.budgetplanner.controller.form.PeopleGrid;
import com.home.budgetplanner.controller.form.SearchPeopleForm;
import com.home.budgetplanner.controller.form.SearchTransactionTypeForm;
import com.home.budgetplanner.controller.form.SearchTransactionsForm;
import com.home.budgetplanner.controller.form.TransactionTypeDTOGrid;
import com.home.budgetplanner.controller.form.TransactionsDTOGrid;
import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
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
    private TransactionsService transactionsService;

    @Autowired
    private GroupService           groupService;

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
            
            //contactPage = transactionsService.findAllTransactionsByPage(pageRequest);            
            contactPage = transactionsService.findAllTransactionsByPeople(people, pageRequest);
            
        } else {
            
            //contactPage = transactionsService.findTransactionsByDescriptionPage(searchForm.getDescription(), pageRequest);
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

    public TransactionTypeDTO initializeTransactionTypeDTO() {

        TransactionTypeDTO transactionTypeDTO = new TransactionTypeDTO();

        return transactionTypeDTO;
    }

    public List<Groups> initializeSelectableGroup() {

        return groupService.findAllOrderByIdAsc();
        // selectableGroup

    }

 //   public TransactionTypeDTO findById(Long id) {

 //       TransactionTypeDTO transactionTypeDTO = TransactionTypeDTO.build(transactionTypeService.findById(new Long(id)));

 //       return transactionTypeDTO;

 //   }

//    public void save(TransactionTypeDTO transactionTypeDTO) {

//        TransactionType transactionType = TransactionTypeDTO.dtoToEntity(transactionTypeDTO);

//        transactionTypeService.save(transactionType);

//    }

    public People save(People people) {

        return peopleService.save(people);
    }

}
