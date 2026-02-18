package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models;

public class LearningItem {
    private int id;
    private String title;
    private String description;
    private String date;
    private String type;
    private String content;
    private int trainingAssignmentId;

    public LearningItem(int id, String title, String content, String date, String type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.type = type;
        this.description = "Инструкция";
    }

    public LearningItem(int id, String title, String description, String date, String type, int trainingAssignmentId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type;
        this.trainingAssignmentId = trainingAssignmentId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getTrainingAssignmentId() { return trainingAssignmentId; }
    public void setTrainingAssignmentId(int trainingAssignmentId) { this.trainingAssignmentId = trainingAssignmentId; }
}