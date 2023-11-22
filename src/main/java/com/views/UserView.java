package main.java.com.views;

import main.java.com.controllers.UserAdminController;
import main.java.com.controllers.UserController;
import main.java.com.models.Account;
import main.java.com.models.User;

import javax.swing.*;
import java.util.List;

public class UserView extends JFrame {

    private UserAdminController userAdminController;
    private UserController userController;
    private User user;
    private JComboBox<User> userDropdown;
    private JPanel mainPanel;

    public UserView(User user, UserAdminController userAdminController, UserController userController) {
        this.userAdminController = userAdminController;
        this.userController = userController;
        this.user = user;

        setTitle("User Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scrollPane);

        createProductBox();

        // createRefreshButton();

        setContentPane(mainPanel);
    }

    public void createProductBox(){
        JPanel createProductBox = new JPanel();
        createProductBox.setBorder(BorderFactory.createTitledBorder("Accounts"));

        List<Account> accountList = userController.getAllUserAccounts(user);

        DefaultListModel<Account> accountListModel = new DefaultListModel<>();
        accountList.forEach(accountListModel::addElement);

        JList<Account> accountListThing = new JList<>(accountListModel);

        createProductBox.add(accountListThing);

        mainPanel.add(createProductBox);

    }


}
