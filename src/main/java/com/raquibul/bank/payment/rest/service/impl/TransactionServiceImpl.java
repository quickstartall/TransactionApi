package com.raquibul.bank.payment.rest.service.impl;

import com.raquibul.bank.payment.rest.entity.Account;
import com.raquibul.bank.payment.rest.entity.Transaction;
import com.raquibul.bank.payment.rest.model.TransactionWrapper;
import com.raquibul.bank.payment.rest.model.TransferOrder;
import com.raquibul.bank.payment.rest.repository.AccountRepository;
import com.raquibul.bank.payment.rest.repository.TransactionRepository;
import com.raquibul.bank.payment.rest.service.TransactionService;
import com.raquibul.bank.payment.rest.service.exception.*;
import com.raquibul.bank.payment.rest.util.TransactionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * @param transferOrder
     * @return
     * @throws TransactionAccountNotFoundException
     * @throws TransactionSaveException
     * @throws BeneficiaryAccountNotFoundException
     */
    @Transactional()
    public String transferMoney(TransferOrder transferOrder) throws TransactionAccountNotFoundException, TransactionSaveException, BeneficiaryAccountNotFoundException, TransactionValidationException {
        validateTransferPayload(transferOrder);
        Account sourceAccount = findAccountByNumber(transferOrder.getSourceAccountDomain().getAccountNumber()).
                orElseThrow(TransactionAccountNotFoundException::new);
        Account beneficiaryAccount = findAccountByNumber(transferOrder.getBeneficiaryAccountDomain().getAccountNumber()).
                orElseThrow(BeneficiaryAccountNotFoundException::new);
        try {
            BigDecimal sourceBalance = sourceAccount.getBalance();
            sourceAccount.setBalance(sourceBalance.subtract(transferOrder.getTransferAmount()));
            accountRepository.save(sourceAccount);

            BigDecimal beneficiaryBalance = beneficiaryAccount.getBalance();
            beneficiaryAccount.setBalance(beneficiaryBalance.add(transferOrder.getTransferAmount()));
            accountRepository.save(beneficiaryAccount);

            Transaction transaction = new Transaction();
            transaction.setSourceAccount(sourceAccount);
            transaction.setBeneficiaryAccount(beneficiaryAccount);
            transaction.setDescription(transferOrder.getTransferDescription());
            transaction.setReference(TransactionUtil.generateUUIDAsString());
            transactionRepository.save(transaction);

            return transaction.getReference();

        } catch (Exception e) {
            log.error("There was some error performing this transaction", e);
            throw new TransactionSaveException();
        }
    }

    /**
     * @param accountNumber
     * @return
     * @throws TransactionAccountNotFoundException
     * @throws TransactionGetException
     */
    public TransactionWrapper findTransactionsByAccount(String accountNumber) throws TransactionAccountNotFoundException, TransactionGetException {
        findAccountByNumber(accountNumber).orElseThrow(TransactionAccountNotFoundException::new);
        try {
            List<Transaction> transactions = transactionRepository.findBySourceAccount_AccountNumber(accountNumber);
            TransactionWrapper transactionWrapper = TransactionUtil.transformToDto(transactions);
            transactionWrapper.setAccountNumber(accountNumber);
            return transactionWrapper;
        } catch (Exception exception) {
            log.error("There was some error while fetching transactions for the provided account");
            throw new TransactionGetException();
        }
    }

    private Optional<Account> findAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        return Optional.ofNullable(account);
    }

    private void validateTransferPayload(TransferOrder transferOrder) throws TransactionValidationException {
        if (transferOrder == null) {
            throw new TransactionValidationException("payload is null");
        }
        if (transferOrder.getSourceAccountDomain() == null) {
            throw new TransactionValidationException("Source Account is null");
        }
        if (transferOrder.getBeneficiaryAccountDomain() == null) {
            throw new TransactionValidationException("Beneficiary Account in null");
        }
        if (StringUtils.isAllBlank(transferOrder.getTransferDescription())) {
            throw new TransactionValidationException("Description is blank");
        }
        if (transferOrder.getTransferAmount() == null) {
            throw new TransactionValidationException("Amount is null");
        }
    }
}
