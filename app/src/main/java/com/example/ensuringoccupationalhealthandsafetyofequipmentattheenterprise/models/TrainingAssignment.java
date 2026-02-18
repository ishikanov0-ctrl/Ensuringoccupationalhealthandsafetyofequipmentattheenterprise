package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models;

public class TrainingAssignment {
    private int id;
    private int userId;
    private int trainingId;
    private String trainingName;
    private String description;
    private String status;
    private String dueDate;
    private String userName;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getTrainingId() { return trainingId; }
    public void setTrainingId(int trainingId) { this.trainingId = trainingId; }
    public String getTrainingName() { return trainingName; }
    public void setTrainingName(String trainingName) { this.trainingName = trainingName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}