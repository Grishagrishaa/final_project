package com.example.afisha.dto;

//FOR HTTP REQUEST TO CLASSIFIER
public class CountryTest {

    private String title;
    private String description;

    public CountryTest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public CountryTest() {
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

    @Override
    public String toString() {
        return "CountryTest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
