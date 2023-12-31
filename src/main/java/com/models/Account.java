package main.java.com.models;

import java.text.DecimalFormat;

public class Account {
    private int id;
    private AccountType type;
    private int cbu;
    private String alias;
    private double interest;
    private int userId;
    private double total;

    //note: tarjeta no tiene user como atributo porque: asociasion unidireccional
    // es un camino simple de usuario tiene cuenta, cuenta tiene tarjeta
    // lo mismo pasa con transacciones/movimeintos
    // cuenta no tiene movimientos porque movimientos tiene cuenta
    // cuenta no tiene saldo porque es de la tarjeta

    public  Account(){};
    public Account(int id, AccountType type, int cbu, String alias, double interest, int userId, double total) {
        this.id = id;
        this.type = type;
        this.cbu = cbu;
        this.alias = alias;
        this.interest = interest;
        this.userId = userId;
        this.total = total;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public int getId() {
        return id;
    }

    public AccountType getType() {
        return type;
    }
    public String getTypeAsString() {
        return type.toString();
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
    public int getUserId(){return userId;};

    public double getTotal(){
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
    public void setType(String type) {
        this.type = fromString(type);
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotal(int available, int due) {
        if(interest != 0){
            this.total = (available - due) * interest;
        } else {
            this.total = (available - due);
        }
    }
    public void setTotal(double total) {
        this.total = total;
    }


    // Helper
    public static AccountType fromString(String value) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + value);
    }
}
