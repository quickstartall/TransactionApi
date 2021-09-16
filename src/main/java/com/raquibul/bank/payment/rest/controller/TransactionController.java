package com.raquibul.bank.payment.rest.controller;

import com.raquibul.bank.payment.rest.controller.exception.*;
import com.raquibul.bank.payment.rest.model.TransactionDomain;
import com.raquibul.bank.payment.rest.model.TransferOrder;
import com.raquibul.bank.payment.rest.service.TransactionService;
import com.raquibul.bank.payment.rest.service.exception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * This is the Controller for the endpoints related Transaction API
 */
@Tag(name = "transaction", description = "The Transaction API")
@RequestMapping("/transactions")
@RestController
@Slf4j
public class TransactionController extends BaseController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * This method returns the transactions of an account based on Account ID
     *
     * @param accountId accountId
     * @return http response
     */
    @Operation(summary = "Get Transactions of an Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions by AccountId found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The provided Account not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization failed",
                    content = @Content)})
    @GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransactionsByAccountId(@PathVariable("accountId") String accountId) {
        try {
            log.info("getTransactionsByAccountId : method invoked");
            return buildResponse(transactionService.findTransactionsByAccount(accountId), HttpStatus.OK);
        } catch (TransactionAccountNotFoundException e) {
            log.error("The provided account could not be found in the System");
            throw new AccountNotFoundException();
        } catch (TransactionGetException e) {
            log.error("There was some error fetching transaction for the provided account");
            throw new UnknownAPIException();
        }
    }

    /**
     * This method returns the transaction details based on ATransaction ID
     *
     * @param accountId     accountId
     * @param transactionId transactionId
     * @return http response
     */
    @Operation(summary = "Get the Transaction details by Transaction Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction Details found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The provided Account not found",
                    content = @Content)})
    @GetMapping("/{accountId}/{transactionId}")
    public ResponseEntity<?> getTransactionDetailById(@PathVariable("accountId") String accountId, @PathVariable("transactionId") String transactionId) {
        return buildResponse(Arrays.asList("s1", "s2"), HttpStatus.OK);
    }


    /**
     * This method initiates the account transaction from Source account to the Target account
     *
     * @param transferOrder {@link TransferOrder} instance as payload
     * @return http response
     */
    @Operation(summary = "Get Transactions of an Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions by AccountId found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "The provided Account not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization failed",
                    content = @Content)})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> initiateTransaction(@Valid @RequestBody TransferOrder transferOrder) {
        try {
            return buildResponse(transactionService.transferMoney(transferOrder), HttpStatus.OK);
        } catch (TransactionValidationException e) {
            log.error("Request validation failed", e);
            throw new RequestValidationException();
        } catch (TransactionAccountNotFoundException e) {
            log.error("The provided account could not be found in the System", e);
            throw new AccountNotFoundException();
        } catch (BeneficiaryAccountNotFoundException e) {
            log.error("Beneficiary Account could not be found", e);
            throw new BeneficiaryNotFoundException();
        } catch (TransactionSaveException e) {
            log.error("There was some error while performing this transaction.", e);
            throw new TransactionFailedException();
        }
    }

}
