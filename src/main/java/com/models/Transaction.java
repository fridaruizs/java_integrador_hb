package main.java.com.models;

import java.util.Date;

public class Transaction {
    private int id;
    private TransactionType type;
    private int originId;
    private int destinyId;
    private Date date;
    private String description;

    public Transaction(){};
    public Transaction(
            int id,
            TransactionType type,
            int originId,
            int destinyId,
            Date date,
            String description)
    {
        this.id = id;
        this.type = type;
        this.originId = originId;
        this.destinyId = destinyId;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }
    public String getTypeAsString() {
        return type.toString();
    }

    public int getOriginId() {
        return originId;
    }

    public int getDestinyId() {
        return destinyId;
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
    public void setType(String type) {
        this.type = fromString(type);
    }

    public void setOriginId(int origin) {
        this.originId = origin;
    }

    public void setDestinyId(int destiny) {
        this.destinyId = destiny;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Helper
    public static TransactionType fromString(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + value);
    }
}
