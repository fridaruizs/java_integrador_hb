package main.java.com.models;

import java.util.Date;
import java.util.List;

public class Report {
    private int Id;
    private Date from;
    private Date to;
    private List<Transaction> transactions;
    public Report(int id, Date from, Date to, List<Transaction> transactions) {
        Id = id;
        this.from = from;
        this.to = to;
        this.transactions = transactions;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
