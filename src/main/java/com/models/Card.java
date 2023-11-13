package main.java.com.models;

public class Card {
    private int id;
    private Account account;
    private int available;
    private int due;

    // user no es atributo por que el caminito es: user > acc > card
    public Card(int id, Account account, int available, int due) {
        this.id = id;
        this.account = account;
        this.available = available;
        this.due = due;
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
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

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setDue(int due) {
        this.due = due;
    }
}
