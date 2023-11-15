package main.java.com.models;

public class Card {
    private int id;
    private int accountId;
    private int available;
    private int due;

    // user no es atributo por que el caminito es: user > acc > card
    public Card(){};
    public Card(int accountId){
        this.accountId = accountId;
        this.available = 0;
        this.due = 0;
    };
    public Card(int id, int accountId, int available, int due) {
        this.id = id;
        this.accountId = accountId;
        this.available = available;
        this.due = due;
    }

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getAvailable() {
        return available;
    }

    public int getDue() {
        return due;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setDue(int due) {
        this.due = due;
    }
}
