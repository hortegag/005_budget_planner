package com.home.budgetplanner.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;

@Entity()
@Data()
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class Groups implements Serializable {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id_group")
    private Long id;
    private String name;
    private String description;
    
    @ManyToMany
    @JoinTable( 
        name = "groups_roles", 
        joinColumns = @JoinColumn (
          name = "id_group"), 
        inverseJoinColumns = @JoinColumn(
          name = "id_rol")) 
    private Collection<Roles> roles;
    
    
    @ManyToMany
    @JoinTable( 
        name = "users_groups", 
        joinColumns = @JoinColumn (
          name = "id_group"), 
        inverseJoinColumns = @JoinColumn(
          name = "id_person")) 
    private Collection<People> people;
    

}
