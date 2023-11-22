package main.java.com.views;

import main.java.com.controllers.BaseController;
import main.java.com.controllers.UserAdminController;
import main.java.com.controllers.UserController;
import main.java.com.exceptions.UserNotFoundException;
import main.java.com.models.User;
import main.java.com.models.UserAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginView extends JFrame {

    private BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView(UserAdminController userAdminController, UserController userController) {
        setTitle("FridaBank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        // JLabel welcomeLabel = new JLabel("Welcome to FridaBank. Please login.");
        // welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(3, 2));

        // mainPanel.add(welcomeLabel);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(new JLabel());
        mainPanel.add(loginButton);

        setContentPane(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    Object existingUser = baseController.login(username, password);
                    String type = baseController.checkUserType(username);
                    if(existingUser != null && type.equals("userAdmin")){
                        AdminView adminPanel = new AdminView((UserAdmin) existingUser, userAdminController, userController);
                        adminPanel.setVisible(true);
                        LoginView.this.dispose();
                    } else if (existingUser != null && type.equals("user")) {
                        UserView userPanel = new UserView((User)existingUser, userAdminController, userController);
                        userPanel.setVisible(true);
                        LoginView.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginView.this, "Contraseña incorrecta");
                    }

                } catch (UserNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }*/
}