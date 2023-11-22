package main.java.com.views;

import main.java.com.controllers.UserAdminController;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private UserAdminController userAdminController;

    public void setUserAdminController(UserAdminController userAdminController) {
        this.userAdminController = userAdminController;
    }
    private JLabel app_title;
    private JPanel MainPanel;
    private JPanel LoginPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton ingresarButton;

    public MainView(){
        MainPanel = new JPanel();
        ingresarButton = new JButton("Ingresar"); // You can set the button text accordingly
        LoginPanel = new JPanel();
        textField1 = new JTextField();
        passwordField1 = new JPasswordField();

        setContentPane(MainPanel);
        setTitle("Trabajo practico integrador: HomeBanking - Frida Ruiz S. Nov 2023 UP - Labo 1 POO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280,720);
        setLocationRelativeTo(null);
        setVisible(true);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // login(textField1.getText(), passwordField1.getText());
            }
        });
    }

}
