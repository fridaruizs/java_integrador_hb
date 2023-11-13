package main.java.com.models;

public class User {
    private int id;
    private String name;
    private Account[] accounts;
    private String username;
    private String password;

    public User(int id, String name, String username, String password, Account[] accounts) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.accounts = accounts;
        // this.cards = cards; for each account show card
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // no set for accounts because yes >:(

}
