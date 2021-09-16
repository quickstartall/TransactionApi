package com.raquibul.bank.payment.rest.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_number", length = 34, nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Version
    private long version;

   /* @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<Transaction> transactions;*/
}

