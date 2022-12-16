package com.example.classifier.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaveConcertCategoryDto {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20)
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
