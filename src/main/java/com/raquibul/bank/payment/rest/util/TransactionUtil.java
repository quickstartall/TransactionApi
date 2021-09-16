package com.raquibul.bank.payment.rest.util;

import com.raquibul.bank.payment.rest.entity.Transaction;
import com.raquibul.bank.payment.rest.model.TransactionDomain;
import com.raquibul.bank.payment.rest.model.TransactionWrapper;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TransactionUtil {
    private TransactionUtil() {
        //restricts instantiation of util call
    }

    /**
     * This method copies properties from the Source object to the Target Object
     *
     * @param source - Source bean
     * @param target - Target bean
     * @param <S>    - Source Type
     * @param <T>    - Target Type
     * @return - Target bean with properties from Source copied to it.
     */
    public static final <S, T> T copyProperties(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     *
     * @return
     */
    public static String generateUUIDAsString() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param transactions
     * @return
     */
    public static TransactionWrapper transformToDto(List<Transaction> transactions) {
        List<TransactionDomain> transactionDomains = transactions.stream().
                sorted(Comparator.comparing(Transaction::getTime).reversed()).
                map(transaction -> {
                    TransactionDomain transactionDomain = new TransactionDomain();
                    transactionDomain.setTransactionAmount(transaction.getAmount());
                    transactionDomain.setTransactionReference(transaction.getReference());
                    transactionDomain.setTransactionTime(transaction.getTime());
                    transactionDomain.setBeneficiaryAccountNumber(transaction.getBeneficiaryAccount().getAccountNumber());
                    transactionDomain.setTransactionDescription(transaction.getDescription());
                    return transactionDomain;
                }).
                collect(Collectors.toList());

        TransactionWrapper transactionWrapper = new TransactionWrapper();
        transactionWrapper.setTransactionDomains(transactionDomains);
        return transactionWrapper;
    }

}
