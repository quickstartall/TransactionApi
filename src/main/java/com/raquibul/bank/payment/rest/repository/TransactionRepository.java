package com.raquibul.bank.payment.rest.repository;

import com.raquibul.bank.payment.rest.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByAccounts_AccountNumber(String accountId);
    List<Transaction> findBySourceAccount_AccountNumber(String accountNumber);
}