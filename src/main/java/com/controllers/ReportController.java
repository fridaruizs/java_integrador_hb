package main.java.com.controllers;

import main.java.com.daos.ReportDAO;
import main.java.com.models.Report;

import java.util.Date;
import java.util.List;

public class ReportController {
    private final ReportDAO reportDAO;

    public ReportController(ReportDAO reportDAO){
        this.reportDAO = reportDAO;
    }

    // Methods

    public Report createTransactionReport(Date from, Date to){
return null;
    }

    public List<Report> getReportsByUser(){
return null;
    }

    public Report createCardReport(){
return null;
    }

    public void printReport(){
    }
}
