package main.java.com.models;

import java.util.Date;

public class Transaction {
    private int id;
    private TransactionType type;
    private Account origin;
    private Account destiny;
    private Date date;
    private String description;

    public Transaction(
            int id,
            TransactionType type,
            Account origin,
            Account destiny,
            Date date,
            String description)
    {
        this.id = id;
        this.type = type;
        this.origin = origin;
        this.destiny = destiny;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Account getOrigin() {
        return origin;
    }

    public Account getDestiny() {
        return destiny;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public void setDestiny(Account destiny) {
        this.destiny = destiny;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
