package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models;

public class TestResult {
    private int id;
    private int userId;
    private int testId;
    private int score;
    private String dateTaken;
    private String userName;
    private String testTitle;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getDateTaken() { return dateTaken; }
    public void setDateTaken(String dateTaken) { this.dateTaken = dateTaken; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getTestTitle() { return testTitle; }
    public void setTestTitle(String testTitle) { this.testTitle = testTitle; }
}