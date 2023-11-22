package main.java.com.daos.h2;

import main.java.com.daos.AccountDAO;
import main.java.com.models.Account;
import main.java.com.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO_h2 implements AccountDAO {

    private final Connection connection;

    // Constructor to inject the database connection
    public AccountDAO_h2(Connection connection) {
        this.connection = connection;
        initializeTable(); // Initialize the table if it doesn't exist
    }

    private void initializeTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "type VARCHAR(255) NOT NULL," +
                    "cbu INTEGER NOT NULL," +
                    "userId INTEGER NOT NULL," +
                    "alias VARCHAR(255) NOT NULL," +
                    "interest DOUBLE NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES users(id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> searchByUser(User user) {
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE userId = ?")) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accounts.add(mapResultSetToAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }
    @Override
    public List<Account> searchAll() {
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accounts.add(mapResultSetToAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account searchById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int create(Account account) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO accounts (type, cbu, alias, interest, userId) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getTypeAsString());
            preparedStatement.setInt(2, account.getCbu());
            preparedStatement.setString(3, account.getAlias());
            preparedStatement.setDouble(4, account.getInterest());
            preparedStatement.setInt(5, account.getUserId());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setId(generatedKeys.getInt(1));
                return account.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void update(Account account) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE accounts SET type = ?, cbu = ?, alias = ?, interest = ?, userId = ? WHERE id = ?")) {
            preparedStatement.setString(1, account.getTypeAsString());
            preparedStatement.setInt(2, account.getCbu());
            preparedStatement.setString(3, account.getAlias());
            preparedStatement.setDouble(4, account.getInterest());
            preparedStatement.setInt(4, account.getUserId());
            preparedStatement.setInt(5, account.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int accountId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE id = ?")) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Helper method to map a ResultSet to an Account object
    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setType(resultSet.getString("type"));
        account.setCbu(resultSet.getInt("cbu"));
        account.setAlias(resultSet.getString("alias"));
        account.setInterest(resultSet.getDouble("interest"));
        account.setUserId(resultSet.getInt("userId"));
        return account;
    }
}
