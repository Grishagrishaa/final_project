package com.example.afisha;


import com.example.userservice.dao.entity.enums.EStatus;

import java.io.*;

public class TestMain {
    public static void main(String[] args) throws IOException {

        EStatus status = EStatus.ACTIVATED;
        System.out.println(status == EStatus.ACTIVATED);

    }
}
