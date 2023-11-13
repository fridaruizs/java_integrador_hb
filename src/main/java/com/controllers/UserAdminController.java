package main.java.com.controllers;

import main.java.com.daos.UserAdminDAO;
import main.java.com.models.User;

public class UserAdminController {

    private final UserAdminDAO userAdminDao;

    /*
      Como instanciar la implementacion:
      Connection connection = ConnectionUtil.getConnection();
      SudoUserDao sudoUserDao = new SudoUserDaoH2Impl(connection);
      UserAdminController userController = new UserController(sudoUserDao);
      SudoUser sudoUser = new SudoUser(constructor parameters);
      // Update the username through the controller
      userController.updateUserUsername(sudoUser, "newUsername");
    */

    public UserAdminController(UserAdminDAO userAdminDao) {
        this.userAdminDao = userAdminDao;
    }

    // Methods
    public void generateReport(User user){
        /*
        * create new report (dao?)
        * reporcontroller search transactions based on date
        * report dao lo guarda en la db?
        */
    }
    public void generateInterest(User user){
        /*
        * usuario.account.interest
        * dao account update interest
        * account .setinterest
        */
    }
    public void generateAccount(User user){
        /*
        * dao account create
        * user.setaccounts agrego
        */
    }
    public void generateCard(User user){
        /*
        * dao card create
        * account .getcards add
        */
    }
    public void generateTransaction(User user){
        /*
        * transactioncontroller.createtrasnaction?
        */
    }
}
