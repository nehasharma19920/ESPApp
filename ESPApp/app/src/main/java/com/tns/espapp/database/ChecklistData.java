package com.tns.espapp.database;

/**
 * Created by TNS on 23-Jun-17.
 */

public class ChecklistData {

    private String name;
    private String dob;
    private String reportinghead;
    private int age;

    public ChecklistData(String name, String dob, String reportinghead, int age) {
        this.name = name;
        this.dob = dob;
        this.reportinghead = reportinghead;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getReportinghead() {
        return reportinghead;
    }

    public void setReportinghead(String reportinghead) {
        this.reportinghead = reportinghead;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
