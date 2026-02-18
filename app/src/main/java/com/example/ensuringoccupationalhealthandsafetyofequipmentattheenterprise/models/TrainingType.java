package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models;

public class TrainingType {
    private int id;
    private String name;
    private String description;
    private String defaultDueDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDefaultDueDate() { return defaultDueDate; }
    public void setDefaultDueDate(String defaultDueDate) { this.defaultDueDate = defaultDueDate; }

    @Override
    public String toString() {
        return name + " (срок: " + defaultDueDate + ")";
    }
}