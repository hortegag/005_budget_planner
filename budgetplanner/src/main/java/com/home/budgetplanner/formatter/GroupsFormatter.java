package com.home.budgetplanner.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.service.GroupService;
import com.home.budgetplanner.service.impl.GroupServiceImpl;

public class GroupsFormatter implements Formatter<Groups> {
    
    private static final Logger    logger = LogManager.getLogger(GroupsFormatter.class);


    @Autowired
    private GroupService groupService;
    
    
    @Override
    public String print(Groups group, Locale locale) {

        return (group != null ? group.getId().toString() : "");

    }

    @Override
    public Groups parse(String text, Locale locale) throws ParseException {

        logger.info(">>>>>>>>>>>><< vloar del bean"+groupService);
        final Long groupId = new Long(text);
        return groupService.findById(groupId);
    }

}