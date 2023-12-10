package main.java.com.controllers;

import main.java.com.daos.*;
import main.java.com.models.*;

import java.util.List;

public class UserAdminController {
    private final UserDAO userDAO;
    // private final ReportDAO reportDAO;
    private final AccountDAO accountDAO;
    private final CardDAO cardDAO;

    public UserAdminController(UserDAO userDAO, AccountDAO accountDAO, CardDAO cardDAO)  {

        this.userDAO = userDAO;
        // this.reportDAO = reportDAO;
        this.accountDAO = accountDAO;
        this.cardDAO = cardDAO;
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
    public List<Account> getAllUserAccounts(User user){
        return accountDAO.searchByUser(user);
    }
    public void deleteAccount(Account acc){
        accountDAO.delete(acc.getId());
        // update user's account list
    }

    public Card getAccountCard(Account account){
        return cardDAO.searchByUserAccount(account.getId());
    }
    public int generateCard(Card card){
        return cardDAO.create(card);
    }
    public int generateCard(Card card, Account acc){
        accountDAO.update(acc);
        return cardDAO.create(card);
    }
    public void deleteCard(Card card){
        // cuando se elimina una tarjeta todos los saldos se resetean ! (concepto utopico de fridabank)
        Account updated = accountDAO.searchById(card.getAccountId());
        updated.setTotal(0);
        accountDAO.update(updated);
        cardDAO.remove(card.getId());
    }
}
