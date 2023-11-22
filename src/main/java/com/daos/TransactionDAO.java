package main.java.com.daos;

import main.java.com.models.*;

import java.util.Date;
import java.util.List;

public interface TransactionDAO {
    List<Transaction> searchByDate(Date from, Date to);
    List<Transaction> searchByDateAndAccount(Date from, Date to, int accountId);
    List<Transaction> searchByDateAndUser(Date from, Date to, int userId);
    // List<Transaction> searchByDateAndCard(Date from, Date to, int cardId);
    List<Transaction> searchByTypeAndDate(TransactionType type, Date from, Date to);
    List<Transaction> searchByUser(User user); // get all
    Transaction searchById(int id);
    int create(Transaction transaction);
    // No se pueden eliminar ni modificar las transacciones!
}
