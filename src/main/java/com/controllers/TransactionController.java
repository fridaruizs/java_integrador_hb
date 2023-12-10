package main.java.com.controllers;

import main.java.com.daos.AccountDAO;
import main.java.com.daos.CardDAO;
import main.java.com.daos.TransactionDAO;
import main.java.com.models.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionController {
    private final TransactionDAO transactionDAO;
    private final CardDAO cardDAO;

    private final AccountDAO accountDAO;


    public TransactionController(TransactionDAO transactionDAO, CardDAO cardDAO, AccountDAO accountDAO){
        this.transactionDAO = transactionDAO;
        this.cardDAO = cardDAO;
        this.accountDAO = accountDAO;
    }

    //Methods
    public void createTransaction(Transaction transaction){
        transactionDAO.create(transaction);
    }

    public int generateTransaction(Transaction transaction){
        // updates saldos
        Card originCard = cardDAO.searchByUserAccount(transaction.getOriginId());
        Card destinyCard = cardDAO.searchByUserAccount(transaction.getDestinyId());

        if(transaction.getType() == TransactionType.debit){
            originCard.setAvailable(originCard.getAvailable() - transaction.getAmount());
            originCard.setDue(originCard.getDue() + transaction.getAmount());

            destinyCard.setAvailable(destinyCard.getAvailable() + transaction.getAmount());
            destinyCard.setDue(destinyCard.getDue() - transaction.getAmount());
        } else {
            originCard.setAvailable(originCard.getAvailable() + transaction.getAmount());
            originCard.setDue(originCard.getDue() - transaction.getAmount());

            destinyCard.setAvailable(destinyCard.getAvailable() - transaction.getAmount());
            destinyCard.setDue(destinyCard.getDue() + transaction.getAmount());
        }
        cardDAO.update(originCard);
        cardDAO.update(destinyCard);

        // UPDATES ACCOUNT
        Account originAcc = accountDAO.searchById(originCard.getAccountId());
        Account destinyAcc = accountDAO.searchById(destinyCard.getAccountId());

        originAcc.setTotal(originCard.getAvailable(), originCard.getDue());
        destinyAcc.setTotal(destinyCard.getAvailable(), destinyCard.getDue());

        accountDAO.update(originAcc);
        accountDAO.update(destinyAcc);

        return transactionDAO.create(transaction);
    }

    public List<Transaction> getAccountAudit(Account account){
        return transactionDAO.searchByAccount(account.getId());
    }

    public Report getCardTransactionsByMonth(Card card, int month){
        // GET DATE
        Date startDate = getFirstDayOfMonth(2023, month);
        Date endDate = getLastDayOfMonth(2023, month);

        List<Transaction> movements = transactionDAO.searchByAccountandDate(card.getAccountId(), startDate, endDate);

        // ID hardcodeado porque no se guarda en la db ups :)
        return new Report(1, startDate, endDate, movements);
    }

    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }
}
