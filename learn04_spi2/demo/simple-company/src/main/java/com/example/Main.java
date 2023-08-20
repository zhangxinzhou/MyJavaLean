package com.example;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ServiceLoader<InternetService> loader = ServiceLoader.load(InternetService.class);
        System.out.println(loader.stream().count());
        for (InternetService provider : loader) {
            provider.connectInternet();
        }
    }
}