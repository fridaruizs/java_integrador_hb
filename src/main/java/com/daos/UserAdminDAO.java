package main.java.com.daos;

import main.java.com.models.UserAdmin;

import java.util.List;

public interface UserAdminDAO {
    // not sure is usefull crud here?
    UserAdmin searchById(int id);
    UserAdmin searchByName(String username);
    List<UserAdmin> searchAll();
    int create(UserAdmin userAdmin);
    void update(UserAdmin userAdmin);
    void delete(int id);
}
