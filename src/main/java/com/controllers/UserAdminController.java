package main.java.com.controllers;

import main.java.com.daos.*;
import main.java.com.models.*;

import java.util.List;

public class UserAdminController {
    private final UserAdminDAO userAdminDAO;
    private final UserDAO userDAO;
    // private final ReportDAO reportDAO;
    private final AccountDAO accountDAO;
    private final CardDAO cardDAO;
    private final TransactionDAO transactionDAO;


    public UserAdminController(UserAdminDAO userAdminDAO, UserDAO userDAO, AccountDAO accountDAO, CardDAO cardDAO, TransactionDAO transactionDAO)  {

        this.userAdminDAO = userAdminDAO;
        this.userDAO = userDAO;
        // this.reportDAO = reportDAO;
        this.accountDAO = accountDAO;
        this.cardDAO = cardDAO;
        this.transactionDAO = transactionDAO;
    }

    // Methods
    public void generateReport(User user){
        /*
        * create new report (dao?)
        * reporcontroller search transactions based on date
        * report dao lo guarda en la db?
        */
    }

    public int generateUser(User user){ return userDAO.create(user);}

    public void deleteUser(User user){ userDAO.delete(user.getId());}
    public void generateInterest(Account account){
        accountDAO.update(account);
    }

    public List<User> getAllUsers(){
        return userDAO.searchAll();
    }

    public int generateAccount(User user, Account account){
        account.setUserId(user.getId());
        return accountDAO.create(account);
    }

    public List<Account> getAllAccounts(){
        return accountDAO.searchAll();
    }
    public void deleteAccount(Account acc){
        accountDAO.delete(acc.getId());
        // update user's account list
    }
    public int generateCard(Card card){
        return cardDAO.create(card);
    }
    public void deleteCard(Card card){ cardDAO.remove(card.getId());}
    public int generateTransaction(Transaction transaction){
        return transactionDAO.create(transaction);
    }

    // public void deleteTransaction(Transaction tr){ transactionDAO.}
}
