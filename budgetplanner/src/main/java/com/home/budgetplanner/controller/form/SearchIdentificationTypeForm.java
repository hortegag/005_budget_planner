package com.home.budgetplanner.controller.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class SearchIdentificationTypeForm implements Serializable {
    
    private String name;
    private int startPosition =1;
    private int maxResult=1;
    

}
