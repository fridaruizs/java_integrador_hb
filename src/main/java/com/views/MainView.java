package main.java.com.views;

import main.Main;

import javax.swing.*;

public class MainView extends JFrame {
    private JLabel app_title;
    private JPanel MainPanel;
    private JPanel LoginPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;

    public MainView (){
        setContentPane(MainPanel);
        setTitle("Trabajo practico integrador: HomeBanking - Frida Ruiz S. Nov 2023 UP - Labo 1 POO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280,720);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        new MainView();
    }
}
