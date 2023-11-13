package main.java.com.models;

public class UserAdmin {
    private int id;
    private String username;
    private String password;

    public UserAdmin(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
