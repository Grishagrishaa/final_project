package com.example.afisha;


import com.example.afisha.dao.entity.enums.EventStatus;

import java.io.*;

public class TestMain {
    public static void main(String[] args) throws IOException {
        EventStatus draft = EventStatus.DRAFT;
        System.out.println(draft.getStatus());

    }
}
