package main.java.com.daos;

import main.java.com.models.UserAdmin;

import java.util.List;

public interface UserAdminDAO {
    UserAdmin searchByName(String username);
}
