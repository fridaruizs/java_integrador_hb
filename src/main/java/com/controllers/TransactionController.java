package main.java.com.controllers;

import main.java.com.daos.TransactionDAO;
import main.java.com.models.Transaction;

public class TransactionController {
    private final TransactionDAO transactionDAO;

    public TransactionController(TransactionDAO transactionDAO){
        this.transactionDAO = transactionDAO;
    }

    //Methods
    public void createTransaction(Transaction transaction){
        transactionDAO.create(transaction);
    }
}
