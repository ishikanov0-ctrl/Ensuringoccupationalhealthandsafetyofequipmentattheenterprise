package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models;

public class Training {
    private int id;
    private String name;
    private String status;
    private String dueDate;

    public Training(int id, String name, String status, String dueDate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}