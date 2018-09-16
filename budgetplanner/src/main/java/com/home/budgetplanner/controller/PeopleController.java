package com.home.budgetplanner.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.budgetplanner.BudgetplannerApplication;
import com.home.budgetplanner.controller.form.PeopleGrid;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.service.PeopleService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private static final Logger logger = LogManager.getLogger(BudgetplannerApplication.class);

    @Autowired
    private PeopleService       peopleService;

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
            // pageRequest = new PageRequest(page - 1, rows, sort);

            pageRequest = PageRequest.of(page - 1, rows, sort);
        } else {
            // pageRequest = new PageRequest(page - 1, rows);
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

}
