package main.java.com.daos;

import main.java.com.models.UserAdmin;

import java.util.List;

public interface UserAdminDAO {
    // not sure is usefull crud here?
    UserAdmin findById(int id);
    List<UserAdmin> findAll();
    void save(UserAdmin userAdmin);
    void update(UserAdmin userAdmin);
    void delete(int id);
}
