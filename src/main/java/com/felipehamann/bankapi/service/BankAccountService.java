package com.felipehamann.bankapi.service;

import com.felipehamann.bankapi.model.*;
import com.felipehamann.bankapi.repository.BankAccountRepository;
import com.felipehamann.bankapi.repository.CustomerRepository;
import com.felipehamann.bankapi.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository,
                              CustomerRepository customerRepository,
                              TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public BankAccount createAccount(Long customerId, String currency) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        BankAccount account = BankAccount.builder()
                .customer(customer)
                .accountNumber("ACC-" + System.currentTimeMillis())
                .balance(BigDecimal.ZERO)
                .currency(currency)
                .status(AccountStatus.ACTIVE)
                .build();

        return bankAccountRepository.save(account);
    }

    public List<BankAccount> findAccountsByCustomer(Long customerId) {
        return bankAccountRepository.findAll().stream()
                .filter(a -> a.getCustomer().getId().equals(customerId))
                .toList();
    }

    public BankAccount getByAccountNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    private void ensureActive(BankAccount account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account is not active: " + account.getStatus()
            );
        }
    }

    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        BankAccount account = getByAccountNumber(accountNumber);
        ensureActive(account);

        account.setBalance(account.getBalance().add(amount));
        bankAccountRepository.save(account);

        transactionRepository.save(Transaction.builder()
                .account(account)
                .amount(amount)
                .type("DEPOSIT")
                .description("Deposit")
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount) {
        BankAccount account = getByAccountNumber(accountNumber);
        ensureActive(account);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        bankAccountRepository.save(account);

        transactionRepository.save(Transaction.builder()
                .account(account)
                .amount(amount.negate())
                .type("WITHDRAW")
                .description("Withdraw")
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Transactional
    public void transfer(String fromAcc, String toAcc, BigDecimal amount) {
        BankAccount from = getByAccountNumber(fromAcc);
        BankAccount to = getByAccountNumber(toAcc);

        ensureActive(from);
        ensureActive(to);

        if (from.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        bankAccountRepository.save(from);
        bankAccountRepository.save(to);

        transactionRepository.save(Transaction.builder()
                .account(from)
                .amount(amount.negate())
                .type("TRANSFER")
                .description("Transfer to " + toAcc)
                .createdAt(LocalDateTime.now())
                .build());

        transactionRepository.save(Transaction.builder()
                .account(to)
                .amount(amount)
                .type("TRANSFER")
                .description("Transfer from " + fromAcc)
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<Transaction> getTransactions(String accountNumber) {
        BankAccount account = getByAccountNumber(accountNumber);
        return transactionRepository.findByAccount(account);
    }

    @Transactional
    public BankAccount changeStatus(Long accountId, AccountStatus status) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getStatus() == AccountStatus.CLOSED && status == AccountStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot reopen a closed account");
        }

        account.setStatus(status);
        return bankAccountRepository.save(account);
    }
}
