package com.example.classifier.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaveCountryDto {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20)
    private String title;
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 150)
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
