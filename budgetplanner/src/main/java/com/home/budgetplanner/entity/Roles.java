package com.home.budgetplanner.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;

@Entity()
@Data()
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id_rol")
    private String id;
    private String name;
    private String description;
    
    
    @ManyToMany
    @JoinTable( 
        name = "groups_roles", 
        joinColumns = @JoinColumn (
          name = "id_rol"), 
        inverseJoinColumns = @JoinColumn(
          name = "id_group")) 
    private Collection<Groups> groups;


}
