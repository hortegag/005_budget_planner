package com.home.budgetplanner.controller.form;

import java.io.Serializable;
import java.util.List;

import com.home.budgetplanner.controller.dtos.PeopleDTO;
import com.home.budgetplanner.controller.dtos.TransactionTypeDTO;
import com.home.budgetplanner.entity.People;

import lombok.Data;

@Data
public class TransactionTypeDTOGrid implements Serializable {

    private int                      totalPages;
    private int                      currentPage;
    private long                     totalRecords;
    private List<TransactionTypeDTO> peopleData;

}
