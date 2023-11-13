package main.java.com.daos;

import java.util.Date;

public interface TransactionDAO {
    void searchByDate(Date from, Date to);
}
