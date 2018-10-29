package com.home.budgetplanner.controller;

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
import com.home.budgetplanner.controller.form.PeopleDTOGrid;
import com.home.budgetplanner.controller.form.PeopleGrid;
import com.home.budgetplanner.controller.form.SearchPeopleForm;
import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.service.GroupService;
import com.home.budgetplanner.service.IdentificationTypeService;
import com.home.budgetplanner.service.PeopleService;
import com.home.budgetplanner.utils.PageWrapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpEntity;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private static final Logger       logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    private PeopleService             peopleService;

    @Autowired
    private IdentificationTypeService identificationTypeService;
    
    @Autowired
    private GroupService groupService;

    @ResponseBody
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    public PeopleGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "sidx", required = false) String sortBy,
            @RequestParam(value = "sord", required = false) String order) {

        logger.info("Listing contacts for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing contacts for grid with sort: {}, order: {}", sortBy, order);

        // Process order by
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("birthDateString"))
            orderBy = "bornDate";

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid
        // starts with 1
        PageRequest pageRequest = null;

        if (sort != null) {

            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);

        }

        Page<People> contactPage = peopleService.findAllByPage(pageRequest);

        // Construct the grid data that will return as JSON data
        PeopleGrid contactGrid = new PeopleGrid();

        contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        contactGrid.setTotalPages(contactPage.getTotalPages());
        contactGrid.setTotalRecords(contactPage.getTotalElements());

        contactGrid.setPeopleData(Lists.newArrayList(contactPage.iterator()));

        return contactGrid;
    }

    @ResponseBody
    @RequestMapping(value = "/listgridTest", method = RequestMethod.GET, produces = "application/json")
    public PeopleGrid listGridTest(@PageableDefault Pageable pageable) {

        logger.info("Listing contacts for grid with page: {}", pageable);

        Page<People> contactPage = peopleService.findAllByPage(pageable);

        // Construct the grid data that will return as JSON data
        PeopleGrid contactGrid = new PeopleGrid();

        contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        contactGrid.setTotalPages(contactPage.getTotalPages());
        contactGrid.setTotalRecords(contactPage.getTotalElements());

        contactGrid.setPeopleData(Lists.newArrayList(contactPage.iterator()));

        return contactGrid;
    }

    @ResponseBody
    @RequestMapping(value = "/listgridbyname", method = RequestMethod.GET, produces = "application/json")
    public PeopleGrid listGridByName(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "sidx", required = false) String sortBy,
            @RequestParam(value = "sord", required = false) String order, @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lastName", required = false) String lastName) {

        logger.info("Listing contacts for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing contacts for grid with sort: {}, order: {}", sortBy, order);

        // Process order by
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("birthDateString"))
            orderBy = "bornDate";

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid
        // starts with 1
        PageRequest pageRequest = null;

        if (sort != null) {

            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);

        }

        Page<People> contactPage;
        // Page<People> contactPage = peopleService.findAllByPage(pageRequest);
        if (StringUtils.isBlank(name) && StringUtils.isBlank(lastName)) {
            contactPage = peopleService.findAllByPage(pageRequest);

        } else {
            contactPage = peopleService.findByNameOrLastNamePage(name, lastName, pageRequest);

        }

        // Construct the grid data that will return as JSON data
        PeopleGrid contactGrid = new PeopleGrid();

        contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        contactGrid.setTotalPages(contactPage.getTotalPages());
        contactGrid.setTotalRecords(contactPage.getTotalElements());

        contactGrid.setPeopleData(Lists.newArrayList(contactPage.iterator()));

        return contactGrid;
    }

    @ResponseBody
    @RequestMapping(value = "/listGridBynameAndLastName", method = RequestMethod.GET, produces = "application/json")
    public PeopleDTOGrid listGridByNameAndLastName(SearchPeopleForm searchForm) {

        logger.info("Listing contacts for grid with page: {}, rows: {}", searchForm.getPage(), searchForm.getRows());
        logger.info("Listing contacts for grid with sort: {}, order: {}", searchForm.getSidx(), searchForm.getSord());

        // Process order by
        Sort sort = null;
        String orderBy = searchForm.getSidx();
        if (orderBy != null && orderBy.equals("bornDateString"))
            orderBy = "bornDate";

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

        Page<PeopleDTO> contactPage;
        // Page<People> contactPage = peopleService.findAllByPage(pageRequest);
        if (StringUtils.isBlank(searchForm.getName()) && StringUtils.isBlank(searchForm.getLastName())) {
            contactPage = peopleService.findAllPeopleByPage(pageRequest);
        } else {
            contactPage = peopleService.findPeopleByNameOrLastNamePage(searchForm.getName(), searchForm.getLastName(), pageRequest);

        }

        // Construct the grid data that will return as JSON data
        PeopleDTOGrid contactGrid = new PeopleDTOGrid();

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

    public People initializePeople() {

        People people = new People();

        return people;
    }

    public List<IdentificationType> initializeSelectableIdentificationTypes() {

        return identificationTypeService.findAll();

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
}
