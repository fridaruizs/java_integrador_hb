package main.java.com.daos;

import main.java.com.models.Card;
import main.java.com.models.User;

import java.util.List;

public interface CardDAO {

    List<Card> searchByUser(int userId); // getallbyuser
    Card searchByUserAccount(int accountId);
    int create(Card card);
    void update(Card card);
    void remove(int id);

}
