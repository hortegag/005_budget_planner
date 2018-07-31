package com.home.budgetplanner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Entity()
@Data()
@Table(name = "identification_type")
public class IdentificationType {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id_identification_type")
    private Long   id;

    private String name;
    private String mnemonic;

    private String description;

    public IdentificationType(String name, String mnemonic, String description) {
        super();
        this.name = name;
        this.mnemonic = mnemonic;
        this.description = description;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
