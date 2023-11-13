package main.java.com.models;

import java.util.Date;

public class Report {
    private int id;
    // make pdf generator
    private ReportType type;
    private Date from;
    private Date to;
    private String description;
    private Transaction[] transaction; // cada reporte es una lista de transacciones
    // note: reporte tiene lista de transacciones pero se tendrian
    // que generar a partir de las fechas
    private User user; // de quien son los movimientos
    // note: RESUMEN es un tipo de reporte con el type = cuenta?


    public Report(int id, ReportType type, Date from, Date to, String description, /*Transaction[] transaction,*/ User user) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.to = to;
        this.description = description;
        // this.transaction = transaction;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public ReportType getType() {
        return type;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public String getDescription() {
        return description;
    }

    public Transaction[] getTransaction() {
        return transaction;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransaction(Transaction[] transaction) {
        this.transaction = transaction;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
