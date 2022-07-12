package com.example.classifier.dto;

public class SaveCountryDto {
    private String title;
    private String description;

    public SaveCountryDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public SaveCountryDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
