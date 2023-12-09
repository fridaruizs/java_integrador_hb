package main.java.com.views;

import main.java.com.controllers.TransactionController;
import main.java.com.controllers.UserAdminController;
import main.java.com.controllers.UserController;
import main.java.com.exceptions.FailedObjectCreationException;
import main.java.com.models.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UserView extends JFrame {

    private UserController userController;
    private TransactionController trController;
    private User user;
    private JComboBox<User> userDropdown;
    private JPanel mainPanel;

    private JTable debitTable;
    private JTable creditTable;

    private List<Transaction> debits;
    private List<Transaction> credits;

    public UserView(User user, UserController userController, TransactionController trController, LoginView loginView) {
        this.userController = userController;
        this.user = user;

        setTitle("Panel de usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scrollPane);

        JLabel welcomeLabel = new JLabel("Bienvenido usuario:" + user.getName());

        mainPanel.add(welcomeLabel);

        JButton backButton = new JButton("Volver al login");
        backButton.addActionListener(e -> {
            dispose();
            loginView.setVisible(true);
            // this.mainPanel.setVisible(false);
        });

        mainPanel.add(backButton);

        createProductBox();
        createCardBox();
        createTransactionBox();

        JPanel createTransactionPanel = new JPanel();
        createTransactionPanel.setBorder(BorderFactory.createTitledBorder("Crear transaccion"));

        createCreateTransactionBox(createTransactionPanel);

        JButton toggleCreateTrButton = new JButton("Transferir");
        toggleCreateTrButton.addActionListener(e -> {
            createTransactionPanel.setVisible(!createTransactionPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleCreateTrButton);
        mainPanel.add(createTransactionPanel);

        setContentPane(mainPanel);
    }

    public void createProductBox(){
        JPanel createProductBox = new JPanel();
        createProductBox.setBorder(BorderFactory.createTitledBorder("Cuentas"));

        List<Account> accountList = userController.getAllUserAccounts(user);

        if (accountList.isEmpty()){
            JLabel emptyLabel = new JLabel("El usuario no posee cuentas");
            createProductBox.add(emptyLabel);
        }

        DefaultListModel<Account> accountListModel = new DefaultListModel<>();
        accountList.forEach(accountListModel::addElement);

        JList<Account> accountListThing = new JList<>(accountListModel);
        accountListThing.setCellRenderer(new AccountListCellRenderer());

        createProductBox.add(accountListThing);

        mainPanel.add(createProductBox);

    }

    private class AccountListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Account) {
                Account account = (Account) value;
                // setText(account.getAlias());
                setText("ID: " + account.getId() +
                        ", Tipo: " + account.getTypeAsString() +
                        ", CBU: " + account.getCbu() +
                        ", Alias: " + account.getAlias() +
                        ", Interes: " + account.getInterest() +
                        ", Total: " + account.getTotal());
            }

            return this;
        }
    }

    public void createCardBox(){
        JPanel createCardBox = new JPanel();
        createCardBox.setBorder(BorderFactory.createTitledBorder("Tarjetas"));

        List<Card> cardList = userController.getAllUserCards(user);

        if (cardList.isEmpty()){
            JLabel emptyLabel = new JLabel("El usuario no posee tarjetas");
            createCardBox.add(emptyLabel);
        }

        DefaultListModel<Card> cardListModel = new DefaultListModel<>();
        cardList.forEach(cardListModel::addElement);

        JList<Card> cardListThing = new JList<>(cardListModel);
        cardListThing.setCellRenderer(new CardListCellRenderer());

        createCardBox.add(cardListThing);

        mainPanel.add(createCardBox);

    }

    public void createCreateTransactionBox(JPanel createTransactionPanel){
        createTransactionPanel.setVisible(false);

        createTransactionPanel.setLayout(new GridLayout(0, 2));

        JLabel originLabel = new JLabel("Seleccione ID de cuenta origen:");

        List<Account> originList = userController.getAllUserAccounts(user);
        JComboBox<Account> originDropdown = new JComboBox<>(originList.toArray(new Account[0]));

        JLabel destinyLabel = new JLabel("Seleccione ID de cuenta destino:");

        List<Account> destinyList = userController.getAllUserAccounts(user);
        JComboBox<Account> destinyDropdown = new JComboBox<>(destinyList.toArray(new Account[0]));

        destinyDropdown.setRenderer(new AccountComboBoxRenderer());

        JLabel typeLabel = new JLabel("Tipo de transaccion:");
        JComboBox<TransactionType> typeDropdown = new JComboBox<>(TransactionType.values());

        JLabel descLabel = new JLabel("Descripcion:");

        JTextField descField = new JTextField(15);

        JLabel amountLabel = new JLabel("Cantidad:");

        JTextField amountField = new JTextField(15);

        JButton createTransactionButton = new JButton("Crear");

        createTransactionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String desc = descField.getText();
                TransactionType selectedType = (TransactionType) typeDropdown.getSelectedItem();
                LocalDateTime currentDateTime = LocalDateTime.now();
                Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
                int amount = Integer.parseInt(amountField.getText());

                // VALIDATIONS
                if (amount == 0 || amount < 0 || amountField.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(UserView.this, "Inserte una cantidad numerica valida");
                    return;
                }

                if (desc.isEmpty()){
                    JOptionPane.showMessageDialog(UserView.this, "Inserte una descripcion valida");
                    return;
                }

                Transaction tr = new Transaction();
                tr.setDescription(desc);
                tr.setDate(currentDate);
                tr.setAmount(amount);
                tr.setType(selectedType);

                if(originDropdown.getSelectedItem().equals(destinyDropdown.getSelectedItem())){
                    JOptionPane.showMessageDialog(UserView.this, "Las cuentas no pueden ser iguales!");
                } else {
                    Account origin = (Account)originDropdown.getSelectedItem();
                    Account destiny = (Account)destinyDropdown.getSelectedItem();

                    // VALIDATION
                    Card oCard = userController.getAccountCard(origin);
                    Card dCard = userController.getAccountCard(destiny);

                    if(oCard == null || dCard == null){
                        JOptionPane.showMessageDialog(UserView.this, "Para generar la trasanccion cada cuenta debe tener una tarjeta!");
                        return;
                    }

                    tr.setDestinyId(destiny.getId());
                    tr.setOriginId(origin.getId());

                    try {
                        int id = trController.generateTransaction(tr);
                        if (id > 0){
                            JOptionPane.showMessageDialog(UserView.this, "Transaccion creada con ID:" + id);
                            // refresh
                            debits = userController.getUserTransactions(UserView.this.user, TransactionType.debit);
                            credits = userController.getUserTransactions(UserView.this.user, TransactionType.credit);
                            mainPanel.repaint();
                            mainPanel.revalidate();
                        }
                    }
                    catch (Exception ex){
                        throw new FailedObjectCreationException("Error creando transaccion");
                    }

                }

            }
        });

        createTransactionPanel.add(originLabel);
        createTransactionPanel.add(originDropdown);
        createTransactionPanel.add(destinyLabel);
        createTransactionPanel.add(destinyDropdown);
        createTransactionPanel.add(typeLabel);
        createTransactionPanel.add(typeDropdown);
        createTransactionPanel.add(descLabel);
        createTransactionPanel.add(descField);
        createTransactionPanel.add(amountLabel);
        createTransactionPanel.add(amountField);
        createTransactionPanel.add(createTransactionButton);

        mainPanel.add(createTransactionPanel);
    }

    private static class AccountComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Account) {
                Account account = (Account) value;
                // Customize the display in the dropdown
                setText("CBU: " + account.getCbu() + " | Alias: " + account.getAlias() + " | ID: " + account.getId());
            }

            return this;
        }
    }

    private class CardListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Card) {
                Card c = (Card) value;
                // setText(String.valueOf(c.getId()));
                setText("ID: " + c.getId() +
                        ", Saldo a pagar: " + c.getDue() +
                        ", Disponible: " + c.getAvailable() +
                        ", ID de cuenta" + c.getAccountId()
                );

            }

            return this;
        }
    }

    public void createTransactionBox(){
        JPanel createTransactionBox = new JPanel();
        createTransactionBox.setBorder(BorderFactory.createTitledBorder("Transferencias"));

        debits = userController.getUserTransactions(this.user, TransactionType.debit);
        credits = userController.getUserTransactions(this.user, TransactionType.credit);

        if (debits.isEmpty() && credits.isEmpty()) {
            JLabel emptyLabel = new JLabel("El usuario no movimientos");
            createTransactionBox.add(emptyLabel);
        } else {
            // Create Debit Table
            DefaultTableModel debitModel = new DefaultTableModel();
            debitModel.addColumn("ID");
            debitModel.addColumn("ID de cuenta origen");
            debitModel.addColumn("ID de cuenta destino");
            debitModel.addColumn("Fecha");
            debitModel.addColumn("Cantidad");
            debitModel.addColumn("Descripcion");

            for (Transaction debit : debits) {
                debitModel.addRow(new Object[]{
                        debit.getId(),
                        debit.getOriginId(),
                        debit.getDestinyId(),
                        debit.getDate(),
                        debit.getAmount(),
                        debit.getDescription()
                });
            }

            debitTable = new JTable(debitModel);
            JScrollPane debitScrollPane = new JScrollPane(debitTable);

            TitledBorder debitBorder = BorderFactory.createTitledBorder("Debitos");
            debitScrollPane.setBorder(debitBorder);

            createTransactionBox.add(debitScrollPane);

            // Create Credit Table
            DefaultTableModel creditModel = new DefaultTableModel();
            creditModel.addColumn("ID");
            creditModel.addColumn("ID de cuenta origen");
            creditModel.addColumn("ID de cuenta destino");
            creditModel.addColumn("Fecha");
            creditModel.addColumn("Cantidad");
            creditModel.addColumn("Descripcion");

            for (Transaction credit : credits) {
                creditModel.addRow(new Object[]{
                        credit.getId(),
                        credit.getOriginId(),
                        credit.getDestinyId(),
                        credit.getDate(),
                        credit.getAmount(),
                        credit.getDescription()
                });
            }

            creditTable = new JTable(creditModel);
            JScrollPane creditScrollPane = new JScrollPane(creditTable);

            TitledBorder creditBorder = BorderFactory.createTitledBorder("Creditos");
            creditScrollPane.setBorder(creditBorder);

            createTransactionBox.add(creditScrollPane);
        }

        mainPanel.add(createTransactionBox);

    }

}
