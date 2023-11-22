package main.java.com.daos.h2;

import main.java.com.daos.CardDAO;
import main.java.com.models.Card;
import main.java.com.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAO_h2 implements CardDAO {
    private final Connection connection;

    // Constructor to inject the database connection
    public CardDAO_h2(Connection connection) {
        this.connection = connection;
        initializeTable(); // Initialize the table if it doesn't exist
    }

    private void initializeTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS cards (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "accountId INTEGER NOT NULL," +
                    "due INTEGER NOT NULL," +
                    "available INTEGER NOT NULL," +
                    "FOREIGN KEY (accountId) REFERENCES accounts(id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Card> searchByUser(int userId) {
        List<Card> cards = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cards WHERE accountId IN (SELECT id FROM accounts WHERE userId = ?)")) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cards.add(mapResultSetToCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    @Override
    public int create(Card card) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cards (accountId, due, available) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, card.getAccountId());
            preparedStatement.setInt(2, card.getDue());
            preparedStatement.setInt(3, card.getAvailable());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                card.setId(generatedKeys.getInt(1));
                return card.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void update(Card card) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cards SET accountId = ?, due = ?, available = ? WHERE id = ?")) {
            preparedStatement.setInt(1, card.getAccountId());
            preparedStatement.setInt(2, card.getDue());
            preparedStatement.setInt(3, card.getAvailable());
            preparedStatement.setInt(4, card.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cards WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper
    private Card mapResultSetToCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getInt("id"));
        card.setAccountId(resultSet.getInt("accountId"));
        card.setDue(resultSet.getInt("due"));
        card.setAvailable(resultSet.getInt("available"));
        return card;
    }
}
