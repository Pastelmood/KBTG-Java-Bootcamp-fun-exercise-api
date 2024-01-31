package com.javabootcamp.fintechbank.accounts;

import com.javabootcamp.fintechbank.exceptions.InternalServerException;
import com.javabootcamp.fintechbank.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountResponse> getAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(acc -> new AccountResponse(acc.getNo(), acc.getType(), acc.getName(), acc.getBalance()))
                .toList();
    }

    public AccountResponse depositAccount(Integer accountNo, DepositRequest depositRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account account = optionalAccount.get();
        Double newBalance = account.getBalance() + depositRequest.amount();
        account.setBalance(newBalance);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new InternalServerException("Failed to deposit");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest theCreateAccountRequest) {

        Account tempAccount = new Account();
        tempAccount.setType(theCreateAccountRequest.getType());
        tempAccount.setName(theCreateAccountRequest.getName());
        tempAccount.setBalance(0d);

        Account newAccount = new Account();

        try {
            newAccount = accountRepository.save(tempAccount);
        } catch (Exception e) {
            throw new InternalServerException("Failed to create account");
        }

        return new AccountResponse(newAccount.getNo(), newAccount.getType(), newAccount.getName(), newAccount.getBalance());
    }


    @Transactional
    public AccountResponse withdrawAccount(Integer accountNo, WithdrawRequest theWithdrawRequest) {

        // find account by id.
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account account = optionalAccount.get();

        // check balance
        double newBalance;

        if (account.getBalance() >= theWithdrawRequest.amount()) {
            newBalance = account.getBalance() - theWithdrawRequest.amount();
        } else {
            throw new InternalServerException("Balance is not enough to withdraw.");
        }

        // Update balance
        account.setBalance(newBalance);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new InternalServerException("Failed to deposit");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }


    @Transactional
    public AccountResponse transferBalance(Integer accountNo, Integer targetAccountNo, TransferRequest transferRequest) {

        // find my account by id
        Optional<Account> optionalMyAccount = accountRepository.findById(accountNo);
        if (optionalMyAccount.isEmpty()) {
            throw new NotFoundException("My Account not found");
        }

        Account myAccount = optionalMyAccount.get();

        // find target account by id
        Optional<Account> optionalTargetAccount = accountRepository.findById(accountNo);
        if (optionalTargetAccount.isEmpty()) {
            throw new NotFoundException("Target Account not found");
        }

        Account targetAccount = optionalTargetAccount.get();

        // check my account balance
        if (transferRequest.amount() > myAccount.getBalance()) {
            throw new InternalServerException("Your balance is not enough to transfer.");
        }

        // withdraw my account
        AccountResponse myNewAccountBalance = withdrawAccount(accountNo, new WithdrawRequest(transferRequest.amount()));

        // deposit target account
        depositAccount(targetAccountNo, new DepositRequest(transferRequest.amount()));

        // return my account balance
        return myNewAccountBalance;
    }


    public AccountResponse getAccountById(Integer accountNo) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("My Account not found");
        }

        Account account = optionalAccount.get();

        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }

}
