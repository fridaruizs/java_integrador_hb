package main.java.com.models;

public class Account {
    private int id;
    private AccountType type;
    private int cbu;
    private String alias;
    private double interest;

    //note: tarjeta no tiene user como atributo porque: asociasion unidireccional
    // es un camino simple de usuario tiene cuenta, cuenta tiene tarjeta
    // lo mismo pasa con transacciones/movimeintos
    // cuenta no tiene movimientos porque movimientos tiene cuenta

    public Account(int id, AccountType type, int cbu, String alias, double interest) {
        this.id = id;
        this.type = type;
        this.cbu = cbu;
        this.alias = alias;
        this.interest = interest;
    }

    public int getId() {
        return id;
    }

    public AccountType getType() {
        return type;
    }

    public int getCbu() {
        return cbu;
    }

    public String getAlias() {
        return alias;
    }

    public double getInterest() {
        return interest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void setCbu(int cbu) {
        this.cbu = cbu;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}
