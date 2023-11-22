package main.java.com.daos.h2;

import main.java.com.daos.TransactionDAO;
import main.java.com.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDAO_h2 implements TransactionDAO {
    private final Connection connection;

    // Constructor to inject the database connection
    public TransactionDAO_h2(Connection connection) {
        this.connection = connection;
        initializeTable(); // Initialize the table if it doesn't exist
    }

    private void initializeTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "type VARCHAR(255) NOT NULL," +
                    "originId INTEGER NOT NULL, " +
                    "destinyId INTEGER NOT NULL," +
                    "date DATETIME NOT NULL," +
                    "description VARCHAR(255) NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> searchByDate(Date from, Date to) {
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE date BETWEEN ? AND ?")) {
            preparedStatement.setTimestamp(1, new Timestamp(from.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(to.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> searchByDateAndAccount(Date from, Date to, int accountId) {
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE (originId = ? OR destinyId = ?) AND date BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, accountId);
            preparedStatement.setTimestamp(3, new Timestamp(from.getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(to.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> searchByDateAndUser(Date from, Date to, int userId) {
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM transactions WHERE (originId IN (SELECT id FROM accounts WHERE userId = ?) " +
                        "OR destinyId IN (SELECT id FROM accounts WHERE userId = ?)) AND date BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setTimestamp(3, new Timestamp(from.getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(to.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }

    @Override
    public List<Transaction> searchByTypeAndDate(TransactionType type, Date from, Date to) {
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE type = ? AND date BETWEEN ? AND ?")) {
            preparedStatement.setString(1, type.name());
            preparedStatement.setTimestamp(2, new Timestamp(from.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(to.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> searchByUser(User user) {
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE originId = ? OR destinyId = ?")) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public Transaction searchById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToTransaction(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int create(Transaction transaction) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transactions (type, originId, destinyId, date, description) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, transaction.getTypeAsString());
            preparedStatement.setLong(2, transaction.getOriginId());
            preparedStatement.setLong(3, transaction.getDestinyId());
            preparedStatement.setTimestamp(4, new Timestamp(transaction.getDate().getTime()));
            preparedStatement.setString(5, transaction.getDescription());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
                return transaction.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Helper
    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getInt("id"));
        transaction.setType(resultSet.getString("type"));
        transaction.setOriginId(resultSet.getInt("originId"));
        transaction.setDestinyId(resultSet.getInt("destinyId"));
        transaction.setDate(resultSet.getTimestamp("date"));
        transaction.setDescription(resultSet.getString("description"));
        return transaction;
    }
}
