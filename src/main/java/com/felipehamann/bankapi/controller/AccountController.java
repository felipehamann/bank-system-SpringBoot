package com.felipehamann.bankapi.controller;

import com.felipehamann.bankapi.dto.*;
import com.felipehamann.bankapi.model.BankAccount;
import com.felipehamann.bankapi.model.Transaction;
import com.felipehamann.bankapi.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final BankAccountService bankAccountService;

    public AccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public BankAccount create(@Valid @RequestBody CreateAccountRequest request) {
        return bankAccountService.createAccount(request.customerId(), request.currency());
    }

    @GetMapping("/by-customer/{customerId}")
    public List<BankAccount> byCustomer(@PathVariable Long customerId) {
        return bankAccountService.findAccountsByCustomer(customerId);
    }

    @GetMapping("/{accountNumber}")
    public BankAccount getByNumber(@PathVariable String accountNumber) {
        return bankAccountService.getByAccountNumber(accountNumber);
    }

    @PostMapping("/deposit")
    public void deposit(@Valid @RequestBody DepositRequest request) {
        bankAccountService.deposit(request.accountNumber(), request.amount());
    }

    @PostMapping("/withdraw")
    public void withdraw(@Valid @RequestBody WithdrawRequest request) {
        bankAccountService.withdraw(request.accountNumber(), request.amount());
    }

    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody TransferRequest request) {
        bankAccountService.transfer(
                request.fromAccount(),
                request.toAccount(),
                request.amount()
        );
    }

    @GetMapping("/{accountNumber}/transactions")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return bankAccountService.getTransactions(accountNumber);
    }

    @PatchMapping("/{accountId}/status")
    public BankAccount changeStatus(@PathVariable Long accountId,
                                    @Valid @RequestBody ChangeAccountStatusRequest request) {
        return bankAccountService.changeStatus(accountId, request.status());
    }
}
