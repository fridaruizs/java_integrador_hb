package main.java.com.daos;

import main.java.com.models.User;
import main.java.com.models.UserAdmin;

import java.util.List;

public interface UserDAO {
    User searchByName(String name);
    List<User> searchAll();
    int create(User user);
    void delete(int id);
}
