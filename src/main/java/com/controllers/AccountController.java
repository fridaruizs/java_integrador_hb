package main.java.com.controllers;

import main.java.com.daos.AccountDAO;
import main.java.com.models.Account;
import main.java.com.models.Transaction;

public class AccountController {
    private final AccountDAO accountDAO;
    private TransactionController transactionController;

    public AccountController(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // Methods

    public void transfer(Transaction transaction){
        transactionController.createTransaction(transaction);
    }
}
