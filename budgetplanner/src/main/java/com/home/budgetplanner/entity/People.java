package com.home.budgetplanner.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Data()
@Table(name = "people")
@NoArgsConstructor
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

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "id_identification_type")
    private IdentificationType identificationType;

}
