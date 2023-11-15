package main.java.com.daos;

import main.java.com.models.Account;
import main.java.com.models.User;

import java.util.List;

public interface AccountDAO {
    List<Account> searchByUser(User user); //getall
    Account searchById(int id);
    void create(Account account);
    void update(Account account);
    void delete(int accountId);

}
