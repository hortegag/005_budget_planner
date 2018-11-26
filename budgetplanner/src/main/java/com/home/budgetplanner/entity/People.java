package com.home.budgetplanner.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity()
@Data()
@Table(name = "people")
@AllArgsConstructor
public class People implements Serializable {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id_person")
    private Long               id;

    private String             name;

    @Column(name = "last_name")
    private String             lastName;

    private String             email;

    @Column(name = "born_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate          bornDate;

    private String             identification;
    
    private String username;
    private String password;
    private String enabled;
    
    
    
    @Column(name = "current_balance")
    private BigDecimal currentBalance;
    private BigDecimal income;
    private BigDecimal expense;
    
    
    public boolean isEnable(){
        
        if (this.getEnabled()!=null && this.getEnabled().equals("Y")){
            return true;
        } else {
            return false;
        }
    }

    @ToString.Exclude
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH },fetch=FetchType.EAGER)
    @JoinColumn(name = "id_identification_type")
    private IdentificationType identificationType;
    
    
    
    
    @ToString.Exclude
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable( 
        name = "users_groups", 
        joinColumns = @JoinColumn (
          name = "id_person"), 
        inverseJoinColumns = @JoinColumn(
          name = "id_group")) 
    private List<Groups> groups;
    
    
 public boolean isValidGroup(Groups group){
     boolean contain =false ;  
     if (group != null && this.groups != null){
            contain = this.groups.contains(group);
        }

        return contain;
    }
 
     @ToString.Exclude
     @JsonIgnore
     @OneToMany(mappedBy = "people", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH }, fetch=FetchType.LAZY)
     private List<Transactions> transactions;


    public People(Long id, String name, String lastName, String email, LocalDate bornDate, String identification, String username, String password,
            String enabled, IdentificationType identificationType, List<Groups> groups) {
        super();
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.bornDate = bornDate;
        this.identification = identification;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.identificationType = identificationType;
        this.groups = groups;
    }


    public People() {
        super();
    }
    
    
    

    
     

     
}
