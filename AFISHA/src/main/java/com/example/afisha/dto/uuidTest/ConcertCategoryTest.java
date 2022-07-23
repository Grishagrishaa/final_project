package com.example.afisha.dto.uuidTest;

//FOR HTTP REQUEST TO CLASSIFIER
public class ConcertCategoryTest {
    private String title;

    public ConcertCategoryTest(String title) {
        this.title = title;
    }

    public ConcertCategoryTest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
