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
import com.home.budgetplanner.controller.form.PeopleDTOGrid;
import com.home.budgetplanner.controller.form.PeopleGrid;
import com.home.budgetplanner.controller.form.SearchPeopleForm;
import com.home.budgetplanner.controller.form.SearchTransactionTypeForm;
import com.home.budgetplanner.controller.form.TransactionTypeDTOGrid;
import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.service.GroupService;
import com.home.budgetplanner.service.IdentificationTypeService;
import com.home.budgetplanner.service.PeopleService;
import com.home.budgetplanner.service.TransactionTypeService;
import com.home.budgetplanner.utils.PageWrapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/transactionType")
public class TransactionTypeController {

    private static final Logger       logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    private PeopleService             peopleService;

    @Autowired
    private TransactionTypeService    transactionTypeService;


    @Autowired
    private GroupService              groupService;

    @ResponseBody
    @RequestMapping(value = "/listGridByNameAndDescription", method = RequestMethod.GET, produces = "application/json")
    public TransactionTypeDTOGrid listGridByNameAndLastName(SearchTransactionTypeForm searchForm) {

        logger.info("Listing contacts for grid with page: {}, rows: {}", searchForm.getPage(), searchForm.getRows());
        logger.info("Listing contacts for grid with sort: {}, order: {}", searchForm.getSidx(), searchForm.getSord());

        // Process order by
        Sort sort = null;
        String orderBy = searchForm.getSidx();
        //if (orderBy != null && orderBy.equals("bornDateString"))
        //    orderBy = "bornDate";

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

        Page<TransactionTypeDTO> contactPage;
        // Page<People> contactPage = peopleService.findAllByPage(pageRequest);
        if (StringUtils.isBlank(searchForm.getName()) && StringUtils.isBlank(searchForm.getDescription())) {
            contactPage = transactionTypeService.findAllTransactionsTypeByPage(pageRequest);
        } else {
            contactPage = transactionTypeService.findTransactionsTypeByNameOrDescriptionPage(searchForm.getName(), searchForm.getDescription(), pageRequest);

        }

        // Construct the grid data that will return as JSON data
        TransactionTypeDTOGrid contactGrid = new TransactionTypeDTOGrid();

        contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        contactGrid.setTotalPages(contactPage.getTotalPages());
        contactGrid.setTotalRecords(contactPage.getTotalElements());

        contactGrid.setPeopleData(Lists.newArrayList(contactPage.iterator()));

        return contactGrid;
    }

    public Pageable initializePageable() {

        Pageable pageable = PageRequest.of(0, 5);
        return pageable;
    }

    @ResponseBody
    @RequestMapping(value = "/listgridTestWrap", method = RequestMethod.GET, produces = "application/json")
    public PageWrapper<People> listGridTestWrap(@PageableDefault Pageable pageable, RequestContext requestContext) {

        logger.info("Listing contacts for grid with page: {}", pageable);

        Page<People> contactPage = peopleService.findAllByPage(pageable);

        // Construct the grid data that will return as JSON data
        // PeopleGrid contactGrid = new PeopleGrid();

        // contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        // contactGrid.setTotalPages(contactPage.getTotalPages());
        // contactGrid.setTotalRecords(contactPage.getTotalElements());

        PageWrapper<People> page = new PageWrapper<People>(contactPage, "/listgridTestWrap");

        requestContext.getFlowScope().put("page", page);
        requestContext.getFlashScope().put("page", page);

        // contactGrid.setPeopleData(Lists.newArrayList(contactPage.iterator()));

        return page;
    }

    @ResponseBody
    @RequestMapping(value = "/listgridTestHate", method = RequestMethod.GET, produces = "application/json")
    public Page<People> listGridTestHate(Pageable pageable) {

        logger.info("Listing contacts for grid with page: {}", pageable);

        Page<People> contactPage = peopleService.findAllByPage(pageable);

        return contactPage;
    }

    public TransactionTypeDTO initializeTransactionTypeDTO() {

        TransactionTypeDTO transactionTypeDTO = new TransactionTypeDTO();

        return transactionTypeDTO;
    }



    public List<Groups> initializeSelectableGroup() {

        return groupService.findAllOrderByIdAsc();
        // selectableGroup

    }

    /*
     * @GetMapping("/testPeopleBootstrapTres")
     * 
     * //@RequestMapping(value = "/testPeopleBootstrapTres", method =
     * RequestMethod.GET, produces = "application/json") public Page<People>
     * listGridTestHate(Pageable pageable) {
     * 
     * logger.info("Listing contacts for grid with page: {}", pageable);
     * 
     * model.addAttribute("identificationsType", identificationsType);
     * 
     * return "identificationType/list-identificationsType";
     * 
     * Page<People> contactPage = peopleService.findAllByPage(pageable);
     * 
     * @GetMapping("/list")
     * 
     * return contactPage; }
     */

    public PeopleDTO findById(Long id) {

        PeopleDTO peopleDTO = PeopleDTO.build(peopleService.findById(new Long(id)));

        return peopleDTO;

    }
/*
    public void save(PeopleDTO peopleDTO) {



        List<Groups> groups = new ArrayList<>();

        for (String groupid : peopleDTO.getGroups()) {

            groups.add(groupService.findById(new Long(groupid)));

        }
        IdentificationType identificationType = identificationTypeService.findById(new Long(peopleDTO.getIdentificationTypeId()));

        People people = PeopleDTO.dtoToEntity(peopleDTO, groups, identificationType);

        peopleService.save(people);

    }*/

    public People save(People people) {


        return peopleService.save(people);
    }

}
