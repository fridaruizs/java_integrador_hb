package main.java.com.controllers;

import main.java.com.daos.CardDAO;
import main.java.com.daos.TransactionDAO;
import main.java.com.models.Card;
import main.java.com.models.Transaction;
import main.java.com.models.TransactionType;

public class TransactionController {
    private final TransactionDAO transactionDAO;
    private final CardDAO cardDAO;


    public TransactionController(TransactionDAO transactionDAO, CardDAO cardDAO){
        this.transactionDAO = transactionDAO;
        this.cardDAO = cardDAO;
    }

    //Methods
    public void createTransaction(Transaction transaction){
        transactionDAO.create(transaction);
    }

    public int generateTransaction(Transaction transaction){
        // updates saldos
        Card originAcc = cardDAO.searchByUserAccount(transaction.getOriginId());
        Card destinyAcc = cardDAO.searchByUserAccount(transaction.getDestinyId());

        if(transaction.getType() == TransactionType.debit){
            originAcc.setAvailable(originAcc.getAvailable() - transaction.getAmount());
            originAcc.setDue(originAcc.getDue() + transaction.getAmount());

            destinyAcc.setAvailable(destinyAcc.getAvailable() + transaction.getAmount());
            destinyAcc.setDue(destinyAcc.getDue() - transaction.getAmount());
        } else {
            originAcc.setAvailable(originAcc.getAvailable() + transaction.getAmount());
            originAcc.setDue(originAcc.getDue() - transaction.getAmount());

            destinyAcc.setAvailable(destinyAcc.getAvailable() - transaction.getAmount());
            destinyAcc.setDue(destinyAcc.getDue() + transaction.getAmount());
        }
        cardDAO.update(originAcc);
        cardDAO.update(destinyAcc);
        return transactionDAO.create(transaction);
    }
}
