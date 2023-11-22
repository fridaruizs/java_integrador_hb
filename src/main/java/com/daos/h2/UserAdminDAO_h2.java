package main.java.com.daos.h2;

import main.java.com.daos.UserAdminDAO;
import main.java.com.models.User;
import main.java.com.models.UserAdmin;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserAdminDAO_h2 implements UserAdminDAO {
    private final Connection connection;

    // Constructor to inject the database connection
    public UserAdminDAO_h2(Connection connection) {
        this.connection = connection;
        initializeTable(); // Initialize the table if it doesn't exist
    }

    private void initializeTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS sudo_users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL)");
            // add first and only value
            // statement.execute("INSERT INTO sudo_users (username, password) VALUES ('admin', 'admin')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public UserAdmin searchById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sudo_users WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUserAdmin(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UserAdmin searchByName(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sudo_users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUserAdmin(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<UserAdmin> searchAll() {
        List<UserAdmin> userAdmins = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM sudo_users")) {

            while (resultSet.next()) {
                userAdmins.add(mapResultSetToUserAdmin(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userAdmins;
    }

    @Override
    public void create(UserAdmin userAdmin) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sudo_users (username, password) VALUES (?, ?)")) {
            preparedStatement.setString(1, userAdmin.getUsername());
            preparedStatement.setString(2, userAdmin.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserAdmin userAdmin) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE sudo_users SET username = ?, password = ? WHERE id = ?")) {
            preparedStatement.setString(1, userAdmin.getUsername());
            preparedStatement.setString(2, userAdmin.getPassword());
            preparedStatement.setInt(3, userAdmin.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM sudo_users WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Map resultset to UserAdmin
    private UserAdmin mapResultSetToUserAdmin(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        return new UserAdmin(id, username, password);
    }
}