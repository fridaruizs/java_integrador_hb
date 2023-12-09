package main.java.com.daos;

import main.java.com.models.*;

import java.util.Date;
import java.util.List;

public interface TransactionDAO {
    List<Transaction> searchByTypeAndUser(TransactionType type, int userId);
    int create(Transaction transaction);
    // No se pueden eliminar ni modificar las transacciones!

    // resumen de cuenta, resumen de tarjeta
    List<Transaction> searchByAccount(int accountId);

    // Reporte mensual
    List<Transaction> searchByDate(Date from, Date to);

}
