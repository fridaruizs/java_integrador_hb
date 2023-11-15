package main.java.com.daos;

import main.java.com.models.Report;
import main.java.com.models.User;

import java.util.List;

public interface ReportDAO {
    List<Report> searchByUser(User user);
    void create(Report report);
}
