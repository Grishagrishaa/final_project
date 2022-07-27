package com.example.userservice;

import java.util.Scanner;

public class TestMain1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int countPeople = scan.nextInt();

        int countCuts = 0;
        int a = 1;

        while (a < countPeople){
            countCuts+=1;
            a*=2;
        }

        System.out.println(countCuts);
    }
}
