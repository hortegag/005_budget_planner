package com.home.budgetplanner.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Entity()
@Data()
@Table(name = "transaction_type")
// @NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(exclude = { "transactions" })
// @ToString(excludes={"people"})
@ToString(exclude = { "transactions" })

public class TransactionType implements Serializable {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id_transaction_type")
    private Long               id;

    private String             name;

    private String             description;

    @Column(name = "entry_type")
    private String             entryType;

    @JsonIgnore
    @OneToMany(mappedBy = "transactionType", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH }, fetch = FetchType.LAZY)
    private List<Transactions> transactions;

    public TransactionType(Long id, String name, String description, String entryType) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.entryType = entryType;
    }

}
