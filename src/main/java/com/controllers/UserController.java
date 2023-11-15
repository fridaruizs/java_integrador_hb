package main.java.com.controllers;

import main.java.com.daos.AccountDAO;
import main.java.com.daos.CardDAO;
import main.java.com.daos.UserDAO;
import main.java.com.models.*;

import java.util.List;

public class UserController {
    private final UserDAO userDAO;
    private final CardDAO cardDAO;
    private final AccountDAO accountDAO;
    public UserController(UserDAO userDAO, CardDAO cardDAO, AccountDAO accountDAO){
        this.userDAO = userDAO;
        this.cardDAO = cardDAO;
        this.accountDAO = accountDAO;
    }

    // Methods
    // public void getAllUserProducts(User user){
        // getallcards getallaccounts
    // }
    public List<Card> getAllUserCards(User user){
        return cardDAO.searchByUser(user.getId());
    }
    public List<Account> getAllUserAccounts(User user){
        return accountDAO.searchByUser(user);
    }

    public void editProfile(User user){
        userDAO.update(user);
    }

    // public Transaction transfer(User user){
        //save transaction > select account
    // }

    // public Report getSummary(){
        //eggerar reportre?
    // }
}
