package com.home.budgetplanner.controller.form;

import java.util.List;

import com.home.budgetplanner.controller.dtos.DataDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataDTOGrid {
    
    private String key;
 
    private long          totalRecords;
    private List<DataDTO> values;

    
    
}
