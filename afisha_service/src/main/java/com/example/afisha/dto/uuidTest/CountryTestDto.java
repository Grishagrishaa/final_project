package com.example.afisha.dto.uuidTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//FOR HTTP REQUEST TO CLASSIFIER
@Data
@NoArgsConstructor @AllArgsConstructor
public class CountryTestDto {

    private String title;
    private String description;

}
