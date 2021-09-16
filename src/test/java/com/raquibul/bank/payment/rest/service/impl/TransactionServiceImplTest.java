package com.raquibul.bank.payment.rest.service.impl;

import com.raquibul.bank.payment.rest.entity.Account;
import com.raquibul.bank.payment.rest.entity.AccountType;
import com.raquibul.bank.payment.rest.entity.Transaction;
import com.raquibul.bank.payment.rest.model.AccountDomain;
import com.raquibul.bank.payment.rest.model.TransactionDomain;
import com.raquibul.bank.payment.rest.model.TransactionWrapper;
import com.raquibul.bank.payment.rest.model.TransferOrder;
import com.raquibul.bank.payment.rest.repository.AccountRepository;
import com.raquibul.bank.payment.rest.repository.TransactionRepository;
import com.raquibul.bank.payment.rest.service.exception.TransactionAccountNotFoundException;
import com.raquibul.bank.payment.rest.service.exception.TransactionGetException;
import com.raquibul.bank.payment.rest.service.exception.TransactionValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testFindTransactionsAndAccountNotFoundExceptionIsThrown() {
        //Given
        String accountNumber = "123";

        //And
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);

        //When Service is called, Then it throws Exception
        Assertions.assertThrows(TransactionAccountNotFoundException.class, () -> {
            transactionService.findTransactionsByAccount(accountNumber);
        });
    }

    @Test
    public void testFindTransactionsAndTransactionGetExceptionIsThrown() {
        //Given
        String accountNumber = "123";

        //And
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(getMockAccount());

        //And
        when(transactionRepository.findBySourceAccount_AccountNumber(anyString())).thenAnswer(invocationOnMock -> {
            throw new TransactionGetException();
        });

        //When Service is called, Then it throws proper Exception
        Assertions.assertThrows(TransactionGetException.class, () -> {
            transactionService.findTransactionsByAccount(accountNumber);
        });
    }

    @Test
    public void testFindTransactionsReturnsAllTheTransactionsAndOrdered() throws TransactionAccountNotFoundException, TransactionGetException {
        //Given
        String accountNumber = "123";

        //And
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(getMockAccount());

        //And
        when(transactionRepository.findBySourceAccount_AccountNumber(anyString())).thenReturn(getMockTransactions());

        //When Service is called
        TransactionWrapper result = transactionService.findTransactionsByAccount(accountNumber);

        //Then proper result is returned
        assertNotNull(result, "The result should not be null");
        assertEquals(result.getTransactionDomains().size(), getMockTransactions().size());

        TransactionDomain transactionDomain1 = result.getTransactionDomains().get(0);
        assertEquals("This is Transaction 2", transactionDomain1.getTransactionDescription());
    }

    @Test
    public void testFindTransactionsReturnsAndContentIsCorrect() throws TransactionAccountNotFoundException, TransactionGetException {
        //Given
        String accountNumber = "123";

        //And
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(getMockAccount());

        //And
        when(transactionRepository.findBySourceAccount_AccountNumber(anyString())).thenReturn(getMockTransactions());

        //When Service is called
        TransactionWrapper result = transactionService.findTransactionsByAccount(accountNumber);

        //Then proper result is returned
        TransactionDomain transactionDomain1 = result.getTransactionDomains().get(0);
        assertEquals(new BigDecimal(100), transactionDomain1.getTransactionAmount());
        assertEquals("This is Transaction 2", transactionDomain1.getTransactionDescription());
        assertEquals("Test Ref 2", transactionDomain1.getTransactionReference());

        TransactionDomain transactionDomain2 = result.getTransactionDomains().get(1);
        assertEquals(BigDecimal.TEN, transactionDomain2.getTransactionAmount());
        assertEquals("This is Transaction 1", transactionDomain2.getTransactionDescription());
        assertEquals("Test Ref 1", transactionDomain2.getTransactionReference());

    }

    @Test
    public void testTransferMoneyPayloadValidationThrowsExpectedException() {
        //Given
        TransferOrder transferOrder = new TransferOrder();

        //When Service is called, Then it throws Exception
        Assertions.assertThrows(TransactionValidationException.class, () -> {
            transactionService.transferMoney(transferOrder);
        });
    }

    @Test
    public void testTransferMoneyAndAccountNotFoundExceptionIsThrown() {
        //Given
        TransferOrder transferOrder = getMockTransferPayload();
        Account account = new Account();
        account.setAccountNumber("1234");

        //And
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);

        //When Service is called, Then it throws Exception
        Assertions.assertThrows(TransactionAccountNotFoundException.class, () -> {
            transactionService.transferMoney(transferOrder);
        });
    }


    private TransferOrder getMockTransferPayload() {
        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setTransferAmount(BigDecimal.TEN);
        transferOrder.setTransferDescription("Test");

        AccountDomain sourceAccountDomain = new AccountDomain();
        sourceAccountDomain.setAccountNumber("1234");
        transferOrder.setSourceAccountDomain(sourceAccountDomain);

        AccountDomain beneficiaryAccountDomain = new AccountDomain();
        beneficiaryAccountDomain.setAccountNumber("9876");
        transferOrder.setBeneficiaryAccountDomain(beneficiaryAccountDomain);

        return transferOrder;
    }

    private List<Transaction> getMockTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setTime(OffsetDateTime.now().minus(Duration.ofDays(1l)));
        transaction1.setId(1l);
        transaction1.setDescription("This is Transaction 1");
        transaction1.setAmount(BigDecimal.TEN);
        transaction1.setReference("Test Ref 1");

        Account benAccount = new Account();
        benAccount.setAccountType(AccountType.CURRENT);
        benAccount.setBalance(BigDecimal.TEN);
        benAccount.setAccountNumber("987");
        transaction1.setBeneficiaryAccount(benAccount);

        Account srcAccount = new Account();
        srcAccount.setAccountType(AccountType.CURRENT);
        srcAccount.setBalance(BigDecimal.TEN);
        srcAccount.setAccountNumber("123");
        transaction1.setSourceAccount(srcAccount);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2l);
        transaction2.setTime(OffsetDateTime.now()); //Transaction2 is latest
        transaction2.setDescription("This is Transaction 2");
        transaction2.setReference("Test Ref 2");
        transaction2.setAmount(new BigDecimal(100));
        transaction2.setSourceAccount(srcAccount);
        transaction2.setBeneficiaryAccount(benAccount);

        return Arrays.asList(transaction1, transaction2);
    }

    private static Account getMockAccount() {
        Account account = new Account();
        account.setAccountNumber("123");
        account.setAccountType(AccountType.CURRENT);
        account.setBalance(BigDecimal.TEN);
        account.setId(1l);
        return account;
    }
}

