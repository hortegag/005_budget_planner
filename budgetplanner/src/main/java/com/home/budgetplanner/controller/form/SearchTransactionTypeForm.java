package com.home.budgetplanner.controller.form;

import lombok.Data;

@Data
public class SearchTransactionTypeForm {
    Integer page;
    Integer rows;
    String  sidx;
    String  sord;
    String  name;
    String  description;

}