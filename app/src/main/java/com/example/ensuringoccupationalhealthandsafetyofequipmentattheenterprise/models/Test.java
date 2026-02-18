package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models;

public class Test {
    private int id;
    private String title;
    private int questionsCount;
    private Integer score;

    public Test(int id, String title, int questionsCount, Integer score) {
        this.id = id;
        this.title = title;
        this.questionsCount = questionsCount;
        this.score = score;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getQuestionsCount() { return questionsCount; }
    public void setQuestionsCount(int questionsCount) { this.questionsCount = questionsCount; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}