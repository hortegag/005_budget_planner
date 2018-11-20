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
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity()
@Data()
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transactions implements Serializable {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id_transaction")
    private Long            id;

    @Column(name = "transaction_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate       transactionDate;

    private String          description;

    private BigDecimal      currentBalance;

    private BigDecimal      value;

    @ToString.Exclude
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_transaction_type")
    private TransactionType transactionType;

    @ToString.Exclude
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_person")
    private People          people;

}
