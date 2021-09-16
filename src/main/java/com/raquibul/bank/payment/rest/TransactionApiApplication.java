package com.raquibul.bank.payment.rest;

import com.raquibul.bank.payment.rest.entity.*;
import com.raquibul.bank.payment.rest.repository.AccountRepository;
import com.raquibul.bank.payment.rest.repository.TransactionRepository;
import com.raquibul.bank.payment.rest.service.RoleService;
import com.raquibul.bank.payment.rest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@SpringBootApplication
@Slf4j
public class TransactionApiApplication implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionApiApplication(UserService userService, RoleService roleService, TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransactionApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("preparing test data");
        roleService.saveOrUpdate(new Role(1L, "admin"));
        roleService.saveOrUpdate(new Role(2L, "user"));

        User user1 = new User();
        user1.setEmail("test@user.com");
        user1.setName("Test User");
        user1.setMobile("9787456545");
        user1.setRole(roleService.findById(2L).get());
        user1.setPassword(new BCryptPasswordEncoder().encode("testuser"));
        userService.saveOrUpdate(user1);


        User user3 = new User();
        user3.setEmail("no-reply@test.com");
        user3.setName("Dummy Name");
        user3.setMobile("9787456545");
        user3.setRole(roleService.findById(2L).get());
        user3.setPassword(new BCryptPasswordEncoder().encode("testuser"));
        userService.saveOrUpdate(user3);

        User user2 = new User();
        user2.setEmail("test@admin.com");
        user2.setName("Test Admin");
        user2.setMobile("9787456545");
        user2.setRole(roleService.findById(1L).get());
        user2.setPassword(new BCryptPasswordEncoder().encode("testadmin"));
        userService.saveOrUpdate(user2);
        log.info("done inserting test data");

        Account srcAccount = new Account();
        srcAccount.setAccountNumber("123");
//        srcAccount.setId(1l);
        srcAccount.setBalance(BigDecimal.TEN);
        srcAccount.setAccountType(AccountType.CURRENT);
        accountRepository.save(srcAccount);


        Account tgtAccount = new Account();
        tgtAccount.setAccountNumber("987");
//        tgtAccount.setId(2l);
        tgtAccount.setAccountType(AccountType.CURRENT);
        tgtAccount.setBalance(BigDecimal.ONE);
        accountRepository.save(tgtAccount);


        Transaction transaction = new Transaction();
//        transaction.setSourceAccount(accountRepository.findByAccountNumber("123"));
//        transaction.setBeneficiaryAccount(accountRepository.findByAccountNumber("987"));

        transaction.setSourceAccount(srcAccount);
        transaction.setBeneficiaryAccount(tgtAccount);
        transaction.setReference("tx12345");
        transaction.setAmount(BigDecimal.TEN);
        transaction.setDescription("Test Transaction");
        transaction.setTime(OffsetDateTime.now());

        transactionRepository.save(transaction);


    }

}
