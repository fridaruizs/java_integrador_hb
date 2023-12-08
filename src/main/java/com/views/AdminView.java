package main.java.com.views;

import main.java.com.controllers.TransactionController;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AdminView extends JFrame {

    private UserAdminController userAdminController;
    private UserController userController;
    private TransactionController transactionController;
    private UserAdmin user;
    private JComboBox<User> userDropdown;
    private JPanel mainPanel;

    public AdminView(UserAdmin user, UserAdminController userAdminController, UserController userController, TransactionController transactionController,LoginView loginView) {
        this.userAdminController = userAdminController;
        this.userController = userController;
        this.transactionController = transactionController;
        this.user = user;

        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scrollPane);

        JLabel welcomeLabel = new JLabel("Bienvenido administrador:" + user.getUsername());

        mainPanel.add(welcomeLabel);

        JButton backButton = new JButton("Volver a login");
        backButton.addActionListener(e -> {
           dispose();
            loginView.setVisible(true);
            // this.mainPanel.setVisible(false);
        });

        mainPanel.add(backButton);

        // USER CREATION
        JPanel createUserPanel = new JPanel();
        createUserPanel.setBorder(BorderFactory.createTitledBorder("Crear usuarios"));

        createCreateUserBox(createUserPanel);

        JButton toggleCreateUserButton = new JButton("Crear nuevo usuario");
        toggleCreateUserButton.addActionListener(e -> {
            createUserPanel.setVisible(!createUserPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateUserButton);
        mainPanel.add(createUserPanel);

        // ACCOUNT CREATION
        JPanel createAccountPanel = new JPanel();
        createAccountPanel.setBorder(BorderFactory.createTitledBorder("Crear cuentas"));

        createCreateAccountBox(createAccountPanel);

        JButton toggleCreateAccountButton = new JButton("Crear nueva cuenta");
        toggleCreateAccountButton.addActionListener(e -> {
            createAccountPanel.setVisible(!createAccountPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateAccountButton);
        mainPanel.add(createAccountPanel);

        // INTEREST CREATION
        JPanel createInterestPanel = new JPanel();
        createInterestPanel.setBorder(BorderFactory.createTitledBorder("Crear intereses"));

        createCreateInterestBox(createInterestPanel);

        JButton toggleCreateInterestButton = new JButton("Agregar intreres");
        toggleCreateInterestButton.addActionListener(e -> {
            createInterestPanel.setVisible(!createInterestPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateInterestButton);
        mainPanel.add(createInterestPanel);

        // CARD CREATION
        JPanel createCardPanel = new JPanel();
        createCardPanel.setBorder(BorderFactory.createTitledBorder("Crear tarjetas"));

        createCreateCardBox(createCardPanel);

        JButton toggleCreateCardButton = new JButton("Agregar nueva tarjeta");
        toggleCreateCardButton.addActionListener(e -> {
            createCardPanel.setVisible(!createCardPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateCardButton);
        mainPanel.add(createCardPanel);

        // Transaction CREATION
        JPanel createTransactionPanel = new JPanel();
        createTransactionPanel.setBorder(BorderFactory.createTitledBorder("Crear transacciones"));

        createCreateTransactionBox(createTransactionPanel);

        JButton toggleCreateTransactionButton = new JButton("Generar nueva transaccion");
        toggleCreateTransactionButton.addActionListener(e -> {
            createTransactionPanel.setVisible(!createTransactionPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateTransactionButton);
        mainPanel.add(createTransactionPanel);

        // USER DELETE
        JPanel createDeleteUserPanel = new JPanel();
        createDeleteUserPanel.setBorder(BorderFactory.createTitledBorder("Eliminar usuarios"));

        createDeleteUserBox(createDeleteUserPanel);

        JButton toggleDeleteUserButton = new JButton("Eliminar usuario");
        toggleDeleteUserButton.addActionListener(e -> {
            createDeleteUserPanel.setVisible(!createDeleteUserPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleDeleteUserButton);
        mainPanel.add(createDeleteUserPanel);

        // ACCOUNT DELETE
        JPanel createDeleteAccountPanel = new JPanel();
        createDeleteAccountPanel.setBorder(BorderFactory.createTitledBorder("Eliminar cuentas"));

        createDeleteAccountBox(createDeleteAccountPanel);

        JButton toggleDeleteAccountButton = new JButton("Eliminar cuenta");
        toggleDeleteAccountButton.addActionListener(e -> {
            createDeleteAccountPanel.setVisible(!createDeleteAccountPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleDeleteAccountButton);
        mainPanel.add(createDeleteAccountPanel);

        // CARD DELETE
        JPanel createDeleteCardPanel = new JPanel();
        createDeleteCardPanel.setBorder(BorderFactory.createTitledBorder("Eliminar tarjetas"));

        createDeleteCardBox(createDeleteUserPanel);

        JButton toggleDeleteCardButton = new JButton("Eliminar tarjeta");
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
        JButton refreshButton = new JButton("Refrescar");
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

        createUserBox.setBorder(BorderFactory.createTitledBorder("Crear nuevo usuario"));

        createUserBox.setLayout(new GridLayout(0, 2));

        JLabel nameLabel = new JLabel("Nombre:");
        JLabel usernameLabel = new JLabel("Usuario:");
        JLabel passwordLabel = new JLabel("Contraseña:");

        JTextField nameField = new JTextField(15);
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JButton createUserButton = new JButton("Crear");

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
                    throw new FailedObjectCreationException("Error creando usuario");
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

        createAccountBox.setBorder(BorderFactory.createTitledBorder("Crear cuenta"));

        createAccountBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Seleccione ID de usuario:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel typeLabel = new JLabel("Tipo de cuenta:");
        JComboBox<AccountType> typeDropdown = new JComboBox<>(AccountType.values());

        JLabel cbuLabel = new JLabel("CBU:");
        JLabel aliasLabel = new JLabel("Alias:");
        JLabel interestLabel = new JLabel("Interes:");

        JTextField cbuField = new JTextField(15);
        JTextField aliasField = new JTextField(15);
        JTextField interestField = new JTextField(15);

        JButton createAccountButton = new JButton("Crear");

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

        createInterestBox.setBorder(BorderFactory.createTitledBorder("Generar intereses"));

        createInterestBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Seleccione ID de usuario:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel accountLabel = new JLabel("Seleccione ID de cuenta:");

        List<Account> accountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
        JComboBox<Account> accountDropdown = new JComboBox<>(accountList.toArray(new Account[0]));

        JLabel interestLabel = new JLabel("Interes:");
        JTextField interestField = new JTextField(15);

        Account selectedAccount = (Account) accountDropdown.getSelectedItem();

        JButton createInterestButton = new JButton("Modificar");

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

        createCardBox.setBorder(BorderFactory.createTitledBorder("Crear tarjetas"));

        createCardBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Seleccione ID de usuario:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel accountLabel = new JLabel("Seleccione ID de cuenta:");

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

        JButton createCardButton = new JButton("Crear");

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

        createDeleteUserBox.setBorder(BorderFactory.createTitledBorder("Eliminar usuario existente"));

        createDeleteUserBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Seleccione ID de usuario:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JButton deleteUserButton = new JButton("Eliminar");

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

        createDeleteAccountBox.setBorder(BorderFactory.createTitledBorder("Eliminar cuenta"));

        createDeleteAccountBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Seleccione ID de usuario:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel accountLabel = new JLabel("Seleccione ID de cuenta:");

        List<Account> accountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
        JComboBox<Account> accountDropdown = new JComboBox<>(accountList.toArray(new Account[0]));

        userDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Account> updatedAccountList = userController.getAllUserAccounts((User) userDropdown.getSelectedItem());
                accountDropdown.setModel(new DefaultComboBoxModel<>(updatedAccountList.toArray(new Account[0])));
            }
        });

        JButton deleteAccountButton = new JButton("Eliminar");

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

        createDeleteCardBox.setBorder(BorderFactory.createTitledBorder("Eliminar tarjeta"));

        createDeleteCardBox.setLayout(new GridLayout(0, 2));

        JLabel userLabel = new JLabel("Seleccione ID de usuario:");

        List<User> userList = userAdminController.getAllUsers();
        userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel cardLabel = new JLabel("Seleccione ID de tarjeta");

        List<Card> cardList = userController.getAllUserCards((User) userDropdown.getSelectedItem());
        JComboBox<Card> cardDropdown = new JComboBox<>(cardList.toArray(new Card[0]));

        userDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Card> updatedCardList = userController.getAllUserCards((User) userDropdown.getSelectedItem());
                cardDropdown.setModel(new DefaultComboBoxModel<>(updatedCardList.toArray(new Card[0])));
            }
        });

        JButton deleteCardButton = new JButton("Eliminar");

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

        createTransactionBox.setBorder(BorderFactory.createTitledBorder("Generar transaccion"));

        createTransactionBox.setLayout(new GridLayout(0, 2));

        JLabel originLabel = new JLabel("Seleccione ID de cuenta origen:");

        List<Account> originList = userAdminController.getAllAccounts();
        JComboBox<Account> originDropdown = new JComboBox<>(originList.toArray(new Account[0]));

        JLabel destinyLabel = new JLabel("Seleccione ID de cuenta destino:");

        List<Account> destinyList = userAdminController.getAllAccounts();
        JComboBox<Account> destinyDropdown = new JComboBox<>(destinyList.toArray(new Account[0]));

        JLabel typeLabel = new JLabel("Tipo de transaccion:");
        JComboBox<TransactionType> typeDropdown = new JComboBox<>(TransactionType.values());

        JLabel descLabel = new JLabel("Descripcion:");

        JTextField descField = new JTextField(15);

        JLabel amountLabel = new JLabel("Importe:");

        JTextField amountField = new JTextField(15);

        JButton createTransactionButton = new JButton("Generar");

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
                        JOptionPane.showMessageDialog(AdminView.this, "Para generar la trasaccion cada cuenta debe tener una tarjeta!");
                        return;
                    }

                    tr.setDestinyId(destiny.getId());
                    tr.setOriginId(origin.getId());

                    try {
                        int id = transactionController.generateTransaction(tr);
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
