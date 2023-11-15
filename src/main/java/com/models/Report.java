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
    private int userId; // de quien son los movimientos
    // note: RESUMEN es un tipo de reporte con el type = cuenta?


    public Report(){};
    public Report(int id, ReportType type, Date from, Date to, String description, /*Transaction[] transaction,*/ int userId) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.to = to;
        this.description = description;
        // this.transaction = transaction;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public ReportType getType() {
        return type;
    }
    public String getTypeAsString() {
        return type.toString();
    }

    public Date getFromDate() {
        return from;
    }

    public Date getToDate() {
        return to;
    }

    public String getDescription() {
        return description;
    }

    public Transaction[] getTransaction() {
        return transaction;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = fromString(type);
    }

    public void setFromDate(Date from) {
        this.from = from;
    }

    public void setToDate(Date to) {
        this.to = to;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransaction(Transaction[] transaction) {
        this.transaction = transaction;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Helper
    public static ReportType fromString(String value) {
        for (ReportType type : ReportType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + value);
    }
}
