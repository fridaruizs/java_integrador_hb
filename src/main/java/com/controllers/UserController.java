package main.java.com.controllers;

import main.java.com.daos.AccountDAO;
import main.java.com.daos.CardDAO;
import main.java.com.daos.TransactionDAO;
import main.java.com.daos.UserDAO;
import main.java.com.models.*;

import java.util.List;

public class UserController {
    private final UserDAO userDAO;
    private final CardDAO cardDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO trDAO;
    public UserController(UserDAO userDAO, CardDAO cardDAO, AccountDAO accountDAO, TransactionDAO trDAO){
        this.userDAO = userDAO;
        this.cardDAO = cardDAO;
        this.accountDAO = accountDAO;
        this.trDAO = trDAO;
    }

    public List<Card> getAllUserCards(User user){
        return cardDAO.searchByUser(user.getId());
    }

    public Card getAccountCard(Account account){
        return cardDAO.searchByUserAccount(account.getId());
    }

    public List<Account> getAllUserAccounts(User user){
        return accountDAO.searchByUser(user);
    }

    public void editProfile(User user){
        userDAO.update(user);
    }

    public List<Transaction> getUserTransactions(User user, TransactionType type){
        return trDAO.searchByTypeAndUser(type, user.getId());
    }

    public int generateTransaction(Transaction transaction){
        // updates saldos
        Card originAcc = cardDAO.searchByUserAccount(transaction.getOriginId());
        Card destinyAcc = cardDAO.searchByUserAccount(transaction.getDestinyId());

        if(transaction.getType() == TransactionType.debit){
            originAcc.setAvailable(originAcc.getAvailable() - transaction.getAmount());
            originAcc.setDue(originAcc.getDue() + transaction.getAmount());

            destinyAcc.setAvailable(destinyAcc.getAvailable() + transaction.getAmount());
        } else {
            originAcc.setAvailable(originAcc.getAvailable() + transaction.getAmount());

            destinyAcc.setAvailable(destinyAcc.getAvailable() - transaction.getAmount());
            destinyAcc.setDue(destinyAcc.getDue() + transaction.getAmount());
        }
        cardDAO.update(originAcc);
        cardDAO.update(destinyAcc);
        return trDAO.create(transaction);
    }

    // public Report getSummary(){
        //eggerar reportre?
    // }
}
