package main.java.com.controllers;

import main.java.com.daos.UserAdminDAO;
import main.java.com.daos.UserDAO;
import main.java.com.exceptions.UserNotFoundException;
import main.java.com.models.User;
import main.java.com.models.UserAdmin;

import java.util.Objects;

public class BaseController {

    private final UserDAO userDAO;
    private final UserAdminDAO userAdminDAO;
    public BaseController(UserDAO userDAO, UserAdminDAO userAdminDAO) {
        this.userDAO = userDAO;
        this.userAdminDAO = userAdminDAO;
    }


    public boolean login(String username, String password) throws UserNotFoundException {
        UserAdmin ua = userAdminDAO.searchByName(username);
        User u = userDAO.searchByName(username);
        if(u != null){
            return Objects.equals(u.getPassword(), password);
        } else if (ua != null) {
            return Objects.equals(ua.getPassword(), password);
        }
        throw new UserNotFoundException("Usuario incorrecto o inexistente");
    }
}
