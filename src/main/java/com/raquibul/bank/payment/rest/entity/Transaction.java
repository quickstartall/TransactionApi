package com.raquibul.bank.payment.rest.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne()
    @JoinColumn(name = "source_account", nullable = false)
    private Account sourceAccount;

    @OneToOne()
    @JoinColumn(name = "beneficiary_account", nullable = false)
    private Account beneficiaryAccount;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_time", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private OffsetDateTime time;

    @Version
    private long version;
    /*@ManyToMany(mappedBy = "transactions")
    private Set<Account> accounts;*/
}
