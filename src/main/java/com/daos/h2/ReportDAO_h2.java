package main.java.com.daos.h2;

import main.java.com.daos.ReportDAO;
import main.java.com.models.Report;
import main.java.com.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO_h2 implements ReportDAO {

    private final Connection connection;

    public ReportDAO_h2(Connection connection) {
        this.connection = connection;
        initializeTable();
    }

    private void initializeTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS reports (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "type VARCHAR(255) NOT NULL," +
                    "from_date DATETIME NOT NULL,"+
                    "to_date DATETIME NOT NULL,"+
                    "description VARCHAR(255) NOT NULL,"+
                    "user_id INTEGER NOT NULL,"+
                    "FOREIGN KEY (user_id) REFERENCES users(id))"
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Report> searchByUser(User user) {
        List<Report> reports = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reports WHERE user_id = ?")) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reports.add(mapResultSetToReport(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    @Override
    public void create(Report report) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reports (type, from_date, to_date, description, user_id) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, report.getTypeAsString());
            preparedStatement.setTimestamp(2, new Timestamp(report.getFromDate().getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(report.getToDate().getTime()));
            preparedStatement.setString(4, report.getDescription());
            preparedStatement.setInt(5, report.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper mapper
    private Report mapResultSetToReport(ResultSet resultSet) throws SQLException {
        Report report = new Report();
        report.setId(resultSet.getInt("id"));
        report.setType(resultSet.getString("type"));
        report.setFromDate(resultSet.getDate("from_date"));
        report.setToDate(resultSet.getDate("to_date"));
        report.setDescription(resultSet.getString("description"));
        report.setUserId(resultSet.getInt("user_id"));
        return report;
    }
}

