package main;

import main.java.com.controllers.*;
import main.java.com.daos.*;
import main.java.com.daos.h2.*;
import main.java.com.utils.ConnectionUtil;
import main.java.com.views.AdminView;
import main.java.com.views.LoginView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        // DB
        try{
            Connection con = ConnectionUtil.getConnection();
            // Initializations
            UserAdminDAO userAdminDAO = new UserAdminDAO_h2(con);
            AccountDAO accountDAO = new AccountDAO_h2(con);
            UserDAO userDAO = new UserDAO_h2(con);
            TransactionDAO transactionDAO = new TransactionDAO_h2(con);
            CardDAO cardDAO = new CardDAO_h2(con);

            // Controllers
            AccountController accountController = new AccountController(accountDAO);
            UserAdminController userAdminController = new UserAdminController(userAdminDAO, userDAO, accountDAO, cardDAO, transactionDAO);
            UserController userController = new UserController(userDAO, cardDAO, accountDAO, transactionDAO);
            TransactionController transactionController = new TransactionController(transactionDAO);
            BaseController baseController = new BaseController(userDAO, userAdminDAO);

            // View
            LoginView mainView = new LoginView(userAdminController, userController);
            mainView.setBaseController(baseController);
            mainView.setVisible(true);



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}