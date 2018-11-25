package com.home.budgetplanner.controller.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class DataDTO {
    
    private String label;
    private BigDecimal value;
    
    
    private BigDecimal x;
    
    private BigDecimal y;
    
    
    public DataDTO(){
        
    }
    
    public DataDTO(String label, BigDecimal value) {
        super();
        this.label = label;
        this.value = value;
    }


    
    public DataDTO(BigDecimal x, BigDecimal y) {
        super();
        this.x = x;
        this.y = y;
    }
    
   
    

}
