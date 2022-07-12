package com.example.classifier.dto;

public class SaveConcertCategoryDto {
    private String title;

    public SaveConcertCategoryDto(String title) {
        this.title = title;
    }

    public SaveConcertCategoryDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
