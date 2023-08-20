package com.example;

public class ChinaMobile implements InternetService{
    @Override
    public void connectInternet() {
        System.out.println("connect internet by [Beijing China Mobile]");
    }
}
