package main.java.com.controllers;

import main.java.com.daos.*;
import main.java.com.models.Account;
import main.java.com.models.Card;
import main.java.com.models.Transaction;
import main.java.com.models.User;

public class UserAdminController {
    private final UserAdminDAO userAdminDAO;
    // private final ReportDAO reportDAO;
    private final AccountDAO accountDAO;
    private final CardDAO cardDAO;
    private final TransactionDAO transactionDAO;

    /*
      Como instanciar la implementacion:
      Connection connection = ConnectionUtil.getConnection();
      SudoUserDao sudoUserDao = new SudoUserDaoH2Impl(connection);
      UserAdminController userController = new UserController(sudoUserDao);
      SudoUser sudoUser = new SudoUser(constructor parameters);
      // Update the username through the controller
      userController.updateUserUsername(sudoUser, "newUsername");
    */

    public UserAdminController(UserAdminDAO userAdminDAO, AccountDAO accountDAO, CardDAO cardDAO, TransactionDAO transactionDAO)  {

        this.userAdminDAO = userAdminDAO;
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
    public void generateInterest(Account account){
        accountDAO.update(account);
    }
    public void generateAccount(User user, Account account){
        account.setUserId(user.getId());
        accountDAO.create(account);
    }
    public void generateCard(int accountId){
        cardDAO.create(new Card(accountId));
    }
    public void generateTransaction(Transaction transaction){
        transactionDAO.create(transaction);
    }
}
