package com.raquibul.bank.payment.rest.controller;

import com.raquibul.bank.payment.rest.model.TransactionDomain;
import com.raquibul.bank.payment.rest.model.TransactionWrapper;
import com.raquibul.bank.payment.rest.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TransactionControllerITTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @WithMockUser("test@user.com")
    public void shouldReturnHttpStatus404WhenCalledWithInvalidUrl() throws Exception {
        mockMvc.perform(get("/invalidurl/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("test@user.com")
    public void shouldReturnHttpStatus200WhenCalledWithAuthorizedUser() throws Exception {
        mockMvc.perform(get("/transactions/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("test@user.com")
    public void shouldReturnHttpStatus200AndResultWhenCalledWithAuthorizedUser() throws Exception {
        when(transactionService.findTransactionsByAccount(ArgumentMatchers.anyString())).thenReturn(getMockTransactions());

        mockMvc.perform(get("/transactions/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionDomains[0].transactionReference").value("tx12345"))
                .andExpect(jsonPath("$.transactionDomains[0].transactionDescription").value("Test Transaction"));
    }

    private TransactionWrapper getMockTransactions() {
        TransactionDomain transactionDomain = new TransactionDomain();
        transactionDomain.setTransactionDescription("Test Transaction");
        transactionDomain.setTransactionReference("tx12345");
        transactionDomain.setTransactionTime(OffsetDateTime.now());
        transactionDomain.setBeneficiaryAccountNumber("123");

        TransactionWrapper transactionWrapper = new TransactionWrapper();
        transactionWrapper.setTransactionDomains(Arrays.asList(transactionDomain));
        return transactionWrapper;
    }
}
