package main.java.com.daos;

import main.java.com.models.User;
import main.java.com.models.UserAdmin;

import java.util.List;

public interface UserDAO {
    User searchById(int id);
    User searchByName(String name);
    List<User> searchAll();
    int create(User user);
    void update(User user);
    void delete(int id);
}
