package main.java.com.views;

import main.java.com.controllers.TransactionController;
import main.java.com.controllers.UserAdminController;
import main.java.com.controllers.UserController;
import main.java.com.exceptions.UserNotFoundException;
import main.java.com.models.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportView extends JFrame {
    private JPanel mainPanel;
    private TransactionController trController;
    private UserAdminController userAdminController;
    private UserController userController;


    public ReportView(TransactionController trController, UserAdminController userAdminController, UserController userController, AdminView adminView){
        this.trController = trController;
        this.userAdminController = userAdminController;
        this.userController = userController;

        setTitle("Panel de reportes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scrollPane);

        JButton backButton = new JButton("Volver a panel de administrador");
        backButton.addActionListener(e -> {
            dispose();
            adminView.setVisible(true);
            // this.mainPanel.setVisible(false);
        });

        mainPanel.add(backButton);

        JLabel userLabel = new JLabel("Seleccione ID de usuario:");
        List<User> userList = userAdminController.getAllUsers();
        JComboBox<User> userDropdown = new JComboBox<>(userList.toArray(new User[0]));

        JLabel cardLabel = new JLabel("Seleccione ID de tarjeta");

        List<Card> cardList = userController.getAllUserCards((User) userDropdown.getSelectedItem());
        JComboBox<Card> cardDropdown = new JComboBox<>(cardList.toArray(new Card[0]));

        Card selectedCard = (Card) cardDropdown.getSelectedItem();
        userDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Card> updatedCardList = userController.getAllUserCards((User) userDropdown.getSelectedItem());
                cardDropdown.setModel(new DefaultComboBoxModel<>(updatedCardList.toArray(new Card[0])));
            }
        });

        JLabel monthLabel = new JLabel("Seleccione un mes:");

        DefaultComboBoxModel<Integer> monthComboBoxModel = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBoxModel.addElement(i);
        }
        JComboBox<Integer> monthComboBox = new JComboBox<>(monthComboBoxModel);
        Integer selectedMonthNumber = (Integer) monthComboBox.getSelectedItem();

        JPanel reportPanel = new JPanel();
        reportPanel.setBorder(BorderFactory.createTitledBorder("Reportes mensuales"));

        monthlyReportBox(reportPanel, selectedCard, selectedMonthNumber);

        JButton toggleReportButton = new JButton("Generar reportes mensuales");
        toggleReportButton.addActionListener(e -> {
            reportPanel.setVisible(!reportPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(userLabel);
        mainPanel.add(userDropdown);
        mainPanel.add(cardLabel);
        mainPanel.add(cardDropdown);
        mainPanel.add(monthLabel);
        mainPanel.add(monthComboBox);


        mainPanel.add(toggleReportButton);
        mainPanel.add(reportPanel);


        JLabel userAuditLabel = new JLabel("Seleccione ID de usuario:");
        List<User> userAuditList = userAdminController.getAllUsers();
        JComboBox<User> userAuditDropdown = new JComboBox<>(userAuditList.toArray(new User[0]));
        User selectedAuditUser = (User) userDropdown.getSelectedItem();

        mainPanel.add(userAuditLabel);
        mainPanel.add(userAuditDropdown);

        JPanel auditPanel = new JPanel();
        auditPanel.setBorder(BorderFactory.createTitledBorder("Reportes de auditoria"));

        auditReportBox(auditPanel, selectedAuditUser);

        JButton toggleAuditButton = new JButton("Generar auditorias");
        toggleAuditButton.addActionListener(e -> {
            auditPanel.setVisible(!auditPanel.isVisible());
            mainPanel.revalidate();
        });

        mainPanel.add(toggleAuditButton);
        mainPanel.add(auditPanel);

    }

    public void monthlyReportBox(JPanel box, Card card, int month){
        box.setVisible(false);
        box.setBorder(BorderFactory.createTitledBorder("Movimientos de tarjeta"));

        JLabel welcomeLabel = new JLabel("ID de tarjeta:" + card.getId());
        box.add(welcomeLabel);

        Report report = trController.getCardTransactionsByMonth(card, month);

        if (report == null || report.getTransactions().isEmpty()) {
            JLabel emptyLabel = new JLabel("No hay informacion de reporte disponible");
            box.add(emptyLabel);
        } else {
            DefaultTableModel reportModel = new DefaultTableModel();
            reportModel.addColumn("ID");
            reportModel.addColumn("Desde");
            reportModel.addColumn("Hasta");
            reportModel.addColumn("Id de transaccion");
            reportModel.addColumn("Tipo de transaccion");
            reportModel.addColumn("Cantidad");
            reportModel.addColumn("Descripcion");

            reportModel.addRow(new Object[]{
                    report.getId(),
                    report.getFrom(),
                    report.getTo(),
                    "",
                    "",
                    "",
            });

            for (Transaction transaction : report.getTransactions()) {
                reportModel.addRow(new Object[]{
                        "",
                        "",
                        "",
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getDescription()
                });
            }

            JTable reportTable = new JTable(reportModel);
            JScrollPane reportScrollPane = new JScrollPane(reportTable);

            TitledBorder reportBorder = BorderFactory.createTitledBorder("Reporte");
            reportScrollPane.setBorder(reportBorder);

            JButton  exportCSVButton = new JButton("Descargar reporte mensual");
            exportCSVButton.addActionListener(e -> exportReport(report));
            box.add(exportCSVButton);

            box.add(reportScrollPane);

        }

        mainPanel.add(box);
    }
    public void auditReportBox(JPanel box, User user){
        box.setVisible(false);
        box.setBorder(BorderFactory.createTitledBorder("Auditoria"));

        JLabel welcomeLabel = new JLabel("Nombre de usuario:" + user.getName());
        box.add(welcomeLabel);

        List<Transaction> allTransactionsEver = null;

        for (Account acc: user.getAccounts()
             ) {
            allTransactionsEver.addAll(trController.getAccountAudit(acc));
        }
        Report report = new Report(1, null, null, allTransactionsEver);

        if (report.getTransactions().isEmpty()) {
            JLabel emptyLabel = new JLabel("No hay informacion de reporte disponible");
            box.add(emptyLabel);
        } else {
            DefaultTableModel reportModel = new DefaultTableModel();
            reportModel.addColumn("ID");
            reportModel.addColumn("Desde");
            reportModel.addColumn("Hasta");
            reportModel.addColumn("Id de transaccion");
            reportModel.addColumn("Tipo de transaccion");
            reportModel.addColumn("Cantidad");
            reportModel.addColumn("Descripcion");

            reportModel.addRow(new Object[]{
                    report.getId(),
                    report.getFrom(),
                    report.getTo(),
                    "",
                    "",
                    "",
            });

            for (Transaction transaction : report.getTransactions()) {
                reportModel.addRow(new Object[]{
                        "",
                        "",
                        "",
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getDescription()
                });
            }

            JTable reportTable = new JTable(reportModel);
            JScrollPane reportScrollPane = new JScrollPane(reportTable);

            TitledBorder reportBorder = BorderFactory.createTitledBorder("Reporte");
            reportScrollPane.setBorder(reportBorder);

            JButton  exportCSVButton = new JButton("Descargar reporte de auditoria");
            exportCSVButton.addActionListener(e -> exportReport(report));
            box.add(exportCSVButton);

            box.add(reportScrollPane);

        }
    }
    public void exportReport(Report report){
        if (report == null || report.getTransactions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay informacion disponible para exportar.", "Exportar a archivo CSV", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "audioria_usuario.csv";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ID de reporte, Desde, Hasta, ID de transaccion, Tipo de transaccion, Cantidad, Descripcion\n");
            writer.write(String.format("%d,%s,%s,,,,\n", report.getId(), report.getFrom(), report.getTo()));

            for (Transaction transaction : report.getTransactions()) {
                writer.write(String.format(",,%d,%s,%s,%s,%s\n",
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getDescription()));
            }

            JOptionPane.showMessageDialog(this, "Resumen desgarcado correctamente en ." + filePath, "Exportar a archivo CSV", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generando resumen.", "Exportar a archivo CSV", JOptionPane.ERROR_MESSAGE);
        }

    }
}
