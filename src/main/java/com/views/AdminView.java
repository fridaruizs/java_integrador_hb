package main.java.com.views;

import main.java.com.controllers.UserAdminController;
import main.java.com.controllers.UserController;
import main.java.com.exceptions.FailedObjectCreationException;
import main.java.com.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AdminView extends JFrame {

    private UserAdminController userAdminController;
    private UserController userController;
    private UserAdmin user;
    private JComboBox<User> userDropdown;
    private JPanel mainPanel;

    public AdminView(UserAdmin user, UserAdminController userAdminController, UserController userController,LoginView loginView) {
        this.userAdminController = userAdminController;
        this.userController = userController;
        this.user = user;

        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        // setPreferredSize(new Dimension(1000, 800));
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scrollPane);

        JButton backButton = new JButton("Go back to login");
        backButton.addActionListener(e -> {
           dispose();
            loginView.setVisible(true);
            // this.mainPanel.setVisible(false);
        });

        mainPanel.add(backButton);

        // USER CREATION
        JPanel createUserPanel = new JPanel();
        createUserPanel.setBorder(BorderFactory.createTitledBorder("Create User"));

        createCreateUserBox(createUserPanel);

        JButton toggleCreateUserButton = new JButton("Create User");
        toggleCreateUserButton.addActionListener(e -> {
            createUserPanel.setVisible(!createUserPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateUserButton);
        mainPanel.add(createUserPanel);

        // ACCOUNT CREATION
        JPanel createAccountPanel = new JPanel();
        createAccountPanel.setBorder(BorderFactory.createTitledBorder("Create Account"));

        createCreateAccountBox(createAccountPanel);

        JButton toggleCreateAccountButton = new JButton("Create Account");
        toggleCreateAccountButton.addActionListener(e -> {
            createAccountPanel.setVisible(!createAccountPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateAccountButton);
        mainPanel.add(createAccountPanel);

        // INTEREST CREATION
        JPanel createInterestPanel = new JPanel();
        createInterestPanel.setBorder(BorderFactory.createTitledBorder("Create Interest"));

        createCreateInterestBox(createInterestPanel);

        JButton toggleCreateInterestButton = new JButton("Create Interest");
        toggleCreateInterestButton.addActionListener(e -> {
            createInterestPanel.setVisible(!createInterestPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateInterestButton);
        mainPanel.add(createInterestPanel);

        // CARD CREATION
        JPanel createCardPanel = new JPanel();
        createCardPanel.setBorder(BorderFactory.createTitledBorder("Create Card"));

        createCreateCardBox(createCardPanel);

        JButton toggleCreateCardButton = new JButton("Create Card");
        toggleCreateCardButton.addActionListener(e -> {
            createCardPanel.setVisible(!createCardPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateCardButton);
        mainPanel.add(createCardPanel);

        // Transaction CREATION
        JPanel createTransactionPanel = new JPanel();
        createTransactionPanel.setBorder(BorderFactory.createTitledBorder("Create Transaction"));

        createCreateTransactionBox(createTransactionPanel);

        JButton toggleCreateTransactionButton = new JButton("Create Transaction");
        toggleCreateTransactionButton.addActionListener(e -> {
            createTransactionPanel.setVisible(!createTransactionPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateTransactionButton);
        mainPanel.add(createTransactionPanel);

        // USER DELETE
        JPanel createDeleteUserPanel = new JPanel();
        createDeleteUserPanel.setBorder(BorderFactory.createTitledBorder("Delete User"));

        createDeleteUserBox(createDeleteUserPanel);

        JButton toggleDeleteUserButton = new JButton("Delete User");
        toggleDeleteUserButton.addActionListener(e -> {
            createDeleteUserPanel.setVisible(!createDeleteUserPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleDeleteUserButton);
        mainPanel.add(createDeleteUserPanel);

        // ACCOUNT DELETE
        JPanel createDeleteAccountPanel = new JPanel();
        createDeleteAccountPanel.setBorder(BorderFactory.createTitledBorder("Delete Account"));

        createDeleteAccountBox(createDeleteAccountPanel);

        JButton toggleDeleteAccountButton = new JButton("Delete Account");
        toggleDeleteAccountButton.addActionListener(e -> {
            createDeleteAccountPanel.setVisible(!createDeleteAccountPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleDeleteAccountButton);
        mainPanel.add(createDeleteAccountPanel);

        // CARD DELETE
        JPanel createDeleteCardPanel = new JPanel();
        createDeleteCardPanel.setBorder(BorderFactory.createTitledBorder("Delete Card"));

        createDeleteCardBox(createDeleteUserPanel);

        JButton toggleDeleteCardButton = new JButton("Delete Card");
        toggleDeleteCardButton.addActionListener(e -> {
            createDeleteCardPanel.setVisible(!createDeleteCardPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleDeleteCardButton);
        mainPanel.add(createDeleteCardPanel);

        createRefreshButton();

        setContentPane(mainPanel);
    }

    private void createRefreshButton() {
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAll();
            }
        });

        mainPanel.add(refreshButton);
    }
    private void refreshAll() {

        List<User> updatedUserList = userAdminController.getAllUsers();
        userDropdown.setModel(new DefaultComboBoxModel<>(updatedUserList.toArray(new User[0])));

        mainPanel.repaint();
        mainPanel.revalidate();
    }
    private void createCreateUserBox(JPanel createUserBox) {

        createUserBox.setVisible(false);

        createUserBox.setBorder(BorderFactory.createTitledBorder("Create User"));

        createUserBox.setLayout(new GridLayout(0, 2));

        JLabel nameLabel = new JLabel("Name:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        JTextField nameField = new JTextField(15);
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JButton createUserButton = new JButton("Create User");

        /*
        createUserButton.setEnabled(false);
        if(
                (!nameField.getText().equals("") && nameField != null) &&
                (!usernameField.getText().equals("") && usernameField != null) &&
                (!passwordField.getText().equals("") && passwordField != null)
        ){
            createUserButton.setEnabled(true);
        }
        */

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User newUser = new User();
                newUser.setName(name);
                newUser.setUsername(username);
                newUser.setPassword(password);

                try{
                    int id = userAdminController.generateUser(newUser);
                    if (id > 0){
                        JOptionPane.showMessageDialog(AdminView.this, "Usuario creado con ID:" + id);
                        refreshAll();
                    }
                }
                catch (Exception ex) {
                    throw new FailedObjectCreationException("Error creado usuario");
                }
            }
        });

        createUserBox.add(nameLabel);
        createUserBox.add(nameField);
        createUserBox.add(usernameLabel);
        createUserBox.add(usernameField);
        createUserBox.add(passwordLabel);
        createUserBox.add(passwordField);
        createUserBox.add(createUserButton);

        mainPanel.add(createUserBox);
    }
    private void createCreateAccountBox(JPanel createAccountBox) {
        createAccountBox.setVisible(false);

        createAccountBox.setBorder(BorderFactory.createTitledBorder("Create Account"));

        createAccountBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Select User id:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        // List<Account> existingAcc = userAdminController.getAllUserAccounts((User) userDropdown.getSelectedItem());
        // List<AccountType> existingAccountTypes = existingAcc.stream()
                // .map(Account::getType)
                // .collect(Collectors.toList());
        // List<AccountType> allPossibleAccountTypes = Arrays.asList(AccountType.values());

        // List<AccountType> availableAccountTypes = allPossibleAccountTypes.stream()
               // .filter(accountType -> !existingAccountTypes.contains(accountType))
               // .collect(Collectors.toList());

        JLabel typeLabel = new JLabel("Account Type:");
        JComboBox<AccountType> typeDropdown = new JComboBox<>(AccountType.values());
        // JComboBox<AccountType> typeDropdown = new JComboBox<>(availableAccountTypes.toArray(new AccountType[0]));

        JLabel cbuLabel = new JLabel("CBU:");
        JLabel aliasLabel = new JLabel("Alias:");
        JLabel interestLabel = new JLabel("Interest:");

        JTextField cbuField = new JTextField(15);
        JTextField aliasField = new JTextField(15);
        JTextField interestField = new JTextField(15);

        JButton createAccountButton = new JButton("Create Account");

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cbu = Integer.parseInt(cbuField.getText());
                String alias = aliasField.getText();
                int interest = Integer.parseInt(interestField.getText());

                User selectedUser = (User) userDropdown.getSelectedItem();
                AccountType selectedType = (AccountType) typeDropdown.getSelectedItem();

                Account newAcc = new Account();
                newAcc.setCbu(cbu);
                newAcc.setInterest(interest);
                newAcc.setAlias(alias);
                newAcc.setType(selectedType);


                // VALIDATION
                // if account.type ya existe no deberia ni ser una op TODO dropdown
                List<Account> existingAcc = userAdminController.getAllUserAccounts((User) userDropdown.getSelectedItem());
                List<AccountType> existingAccountTypes = existingAcc.stream()
                .map(Account::getType)
                .collect(Collectors.toList());

                if(existingAccountTypes.contains(selectedType)){
                    JOptionPane.showMessageDialog(AdminView.this, "Una cuenta de ese tipo ya fue creada, seleccione un nuevo tipo (Solo se permite una cuenta por tipo!)");
                    return;
                }

                try {
                    int id = userAdminController.generateAccount(selectedUser, newAcc);
                    if (id > 0){
                        JOptionPane.showMessageDialog(AdminView.this, "Cuenta creada con ID:" + id);
                        refreshAll();
                    }
                }
                catch (Exception ex){
                    throw new FailedObjectCreationException("Error creando cuenta");
                }

            }
        });

        createAccountBox.add(userLabel);
        createAccountBox.add(userDropdown);
        createAccountBox.add(typeLabel);
        createAccountBox.add(typeDropdown);
        createAccountBox.add(cbuLabel);
        createAccountBox.add(cbuField);
        createAccountBox.add(aliasLabel);
        createAccountBox.add(aliasField);
        createAccountBox.add(interestLabel);
        createAccountBox.add(interestField);
        createAccountBox.add(createAccountButton);

        mainPanel.add(createAccountBox);
    }
    public void createCreateInterestBox(JPanel createInterestBox){
        createInterestBox.setVisible(false);

        createInterestBox.setBorder(BorderFactory.createTitledBorder("Generate interest"));

        createInterestBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Select User id:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel accountLabel = new JLabel("Select User account id:");

        List<Account> accountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
        JComboBox<Account> accountDropdown = new JComboBox<>(accountList.toArray(new Account[0]));

        JLabel interestLabel = new JLabel("Interest:");
        JTextField interestField = new JTextField(15);

        Account selectedAccount = (Account) accountDropdown.getSelectedItem();

        JButton createInterestButton = new JButton("Modify interest");

        createInterestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectedAccount.setInterest(Double.parseDouble(interestField.getText()));

                    userAdminController.generateInterest(selectedAccount);
                    JOptionPane.showMessageDialog(AdminView.this, "Interes modificado con exito");
                    refreshAll();
                }
                catch (Exception ex){
                    throw new FailedObjectCreationException("Error modificando interes");
                }

            }
        });

        createInterestBox.add(userLabel);
        createInterestBox.add(userDropdown);
        createInterestBox.add(accountLabel);
        createInterestBox.add(accountDropdown);
        createInterestBox.add(interestLabel);
        createInterestBox.add(interestField);

        createInterestBox.add(createInterestButton);

        mainPanel.add(createInterestBox);


    }
    public void createCreateCardBox(JPanel createCardBox){
        createCardBox.setVisible(false);

        createCardBox.setBorder(BorderFactory.createTitledBorder("Create Card"));

        createCardBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Select User id:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel accountLabel = new JLabel("Select User account id:");

        List<Account> accountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
        JComboBox<Account> accountDropdown = new JComboBox<>(accountList.toArray(new Account[0]));

        userDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Account> updatedAccountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
                accountDropdown.setModel(new DefaultComboBoxModel<>(updatedAccountList.toArray(new Account[0])));
            }
        });

        JLabel availableLabel = new JLabel("Disponible:");
        JLabel dueLabel = new JLabel("A pagar:");

        JTextField availableField = new JTextField(15);
        JTextField dueField = new JTextField(15);

        JButton createCardButton = new JButton("Create Card");

        createCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int available = Integer.parseInt(availableField.getText());
                int due = Integer.parseInt(dueField.getText());

                User selectedUser = (User) userDropdown.getSelectedItem();
                Account selectedAcc = (Account) accountDropdown.getSelectedItem();

                Card newCard = new Card();
                newCard.setAccountId(selectedAcc.getId());
                newCard.setAvailable(available);
                newCard.setDue(due);

                try {
                    int id = userAdminController.generateCard(newCard);
                    if (id > 0){
                        JOptionPane.showMessageDialog(AdminView.this, "Tarjeta creada con ID:" + id);
                        refreshAll();
                    }
                }
                catch (Exception ex){
                    throw new FailedObjectCreationException("Error creando tarjeta");
                }

            }
        });

        createCardBox.add(userLabel);
        createCardBox.add(userDropdown);
        createCardBox.add(accountLabel);
        createCardBox.add(accountDropdown);
        createCardBox.add(availableLabel);
        createCardBox.add(availableField);
        createCardBox.add(dueLabel);
        createCardBox.add(dueField);
        createCardBox.add(createCardButton);

        mainPanel.add(createCardBox);
    }
    public void createDeleteUserBox(JPanel createDeleteUserBox){
        createDeleteUserBox.setVisible(false);

        createDeleteUserBox.setBorder(BorderFactory.createTitledBorder("Delete User"));

        createDeleteUserBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Select User id:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JButton deleteUserButton = new JButton("Delete user");

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User selectedUser = (User) userDropdown.getSelectedItem();

                try {
                    userAdminController.deleteUser(selectedUser);
                    JOptionPane.showMessageDialog(AdminView.this, "Usuario eliminado con exito");
                    refreshAll();
                }
                catch (Exception ex){
                    throw new FailedObjectCreationException("Error eliminando usuario");
                }

            }
        });

        createDeleteUserBox.add(userLabel);
        createDeleteUserBox.add(userDropdown);

        createDeleteUserBox.add(deleteUserButton);

        mainPanel.add(createDeleteUserBox);

    }
    public void createDeleteAccountBox(JPanel createDeleteAccountBox){
        createDeleteAccountBox.setVisible(false);

        createDeleteAccountBox.setBorder(BorderFactory.createTitledBorder("Delete Account"));

        createDeleteAccountBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Select User id:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel accountLabel = new JLabel("Select User account id:");

        List<Account> accountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
        JComboBox<Account> accountDropdown = new JComboBox<>(accountList.toArray(new Account[0]));

        userDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Account> updatedAccountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
                accountDropdown.setModel(new DefaultComboBoxModel<>(updatedAccountList.toArray(new Account[0])));
            }
        });

        JButton deleteAccountButton = new JButton("Delete account");

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userAdminController.deleteAccount((Account) accountDropdown.getSelectedItem());
                    JOptionPane.showMessageDialog(AdminView.this, "Cuenta eliminada con exito");
                    refreshAll();
                }
                catch (Exception ex){
                    throw new FailedObjectCreationException("Error eliminando cuenta");
                }

            }
        });

        createDeleteAccountBox.add(userLabel);
        createDeleteAccountBox.add(userDropdown);
        createDeleteAccountBox.add(accountLabel);
        createDeleteAccountBox.add(accountDropdown);

        createDeleteAccountBox.add(deleteAccountButton);

        mainPanel.add(createDeleteAccountBox);
    }
    public void createDeleteCardBox(JPanel createDeleteCardBox){
        createDeleteCardBox.setVisible(false);

        createDeleteCardBox.setBorder(BorderFactory.createTitledBorder("Delete Card"));

        createDeleteCardBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Select User id:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel cardLabel = new JLabel("Select User card id:");

        List<Card> cardList = userController.getAllUserCards((User) userDropdown.getSelectedItem());
        JComboBox<Card> cardDropdown = new JComboBox<>(cardList.toArray(new Card[0]));

        userDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Card> updatedCardList = userController.getAllUserCards((User) userDropdown.getSelectedItem());
                cardDropdown.setModel(new DefaultComboBoxModel<>(updatedCardList.toArray(new Card[0])));
            }
        });

        JButton deleteCardButton = new JButton("Delete card");

        deleteCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userAdminController.deleteCard((Card) cardDropdown.getSelectedItem());
                    JOptionPane.showMessageDialog(AdminView.this, "Tarjeta eliminada con exito");
                    refreshAll();
                }
                catch (Exception ex){
                    throw new FailedObjectCreationException("Error eliminando tarjeta");
                }

            }
        });

        createDeleteCardBox.add(userLabel);
        createDeleteCardBox.add(userDropdown);
        createDeleteCardBox.add(cardLabel);
        createDeleteCardBox.add(cardDropdown);

        createDeleteCardBox.add(deleteCardButton);

        mainPanel.add(createDeleteCardBox);
    }
    public void createCreateTransactionBox(JPanel createTransactionBox){
        createTransactionBox.setVisible(false);

        createTransactionBox.setBorder(BorderFactory.createTitledBorder("Create Transaction"));

        createTransactionBox.setLayout(new GridLayout(0, 2));

        JLabel originLabel = new JLabel("Select Origin id:");

        List<Account> originList = userAdminController.getAllAccounts();
        JComboBox<Account> originDropdown = new JComboBox<>(originList.toArray(new Account[0]));

        JLabel destinyLabel = new JLabel("Select destiny id:");

        List<Account> destinyList = userAdminController.getAllAccounts();
        JComboBox<Account> destinyDropdown = new JComboBox<>(destinyList.toArray(new Account[0]));

        JLabel typeLabel = new JLabel("Transaction Type:");
        JComboBox<TransactionType> typeDropdown = new JComboBox<>(TransactionType.values());

        JLabel descLabel = new JLabel("Description:");

        JTextField descField = new JTextField(15);

        JLabel amountLabel = new JLabel("Amount:");

        JTextField amountField = new JTextField(15);

        JButton createTransactionButton = new JButton("Create Transaction");

        createTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String desc = descField.getText();
                TransactionType selectedType = (TransactionType) typeDropdown.getSelectedItem();
                LocalDateTime currentDateTime = LocalDateTime.now();
                Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());

                int amount = Integer.parseInt(amountField.getText());

                Transaction tr = new Transaction();
                tr.setDescription(desc);
                tr.setDate(currentDate);
                tr.setAmount(amount);
                tr.setType(selectedType);

                if(originDropdown.getSelectedItem().equals(destinyDropdown.getSelectedItem())){
                    JOptionPane.showMessageDialog(AdminView.this, "Las cuentas no pueden ser iguales!");
                } else {
                    Account origin = (Account)originDropdown.getSelectedItem();
                    Account destiny = (Account)destinyDropdown.getSelectedItem();

                    // VALIDATION
                    Card oCard = userAdminController.getAccountCard(origin);
                    Card dCard = userAdminController.getAccountCard(destiny);

                    if(oCard == null || dCard == null){
                        JOptionPane.showMessageDialog(AdminView.this, "You need to create a card for the selected account before making a transaction.");
                        return;
                    }

                    tr.setDestinyId(destiny.getId());
                    tr.setOriginId(origin.getId());

                    try {
                        int id = userAdminController.generateTransaction(tr);
                        if (id > 0){
                            JOptionPane.showMessageDialog(AdminView.this, "Transaccion creada con ID:" + id);
                            refreshAll();
                        }
                    }
                    catch (Exception ex){
                        throw new FailedObjectCreationException("Error creando transaccion");
                    }

                }

            }
        });

        createTransactionBox.add(originLabel);
        createTransactionBox.add(originDropdown);
        createTransactionBox.add(destinyLabel);
        createTransactionBox.add(destinyDropdown);
        createTransactionBox.add(typeLabel);
        createTransactionBox.add(typeDropdown);
        createTransactionBox.add(descLabel);
        createTransactionBox.add(descField);
        createTransactionBox.add(amountLabel);
        createTransactionBox.add(amountField);
        createTransactionBox.add(createTransactionButton);

        mainPanel.add(createTransactionBox);
    }
    public void createCreateRecordBox(JPanel panel){}
}
