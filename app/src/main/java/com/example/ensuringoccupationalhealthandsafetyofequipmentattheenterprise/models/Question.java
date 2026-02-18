package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models;

import java.util.List;

public class Question {
    private int id;
    private int testId;
    private String questionText;
    private int correctOptionId;
    private List<Option> options;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public int getCorrectOptionId() { return correctOptionId; }
    public void setCorrectOptionId(int correctOptionId) { this.correctOptionId = correctOptionId; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
}