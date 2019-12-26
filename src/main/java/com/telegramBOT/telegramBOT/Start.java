package com.telegramBOT.telegramBOT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Start {

    public static void main(String[] args) {

        System.setProperty("com.google.inject.internal.cglib.$experimental_asm7", "true");
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
        System.getProperties().put( "socksProxyPort", "9150" );

        ApiContextInitializer.init();
        SpringApplication.run(Start.class, args);
    }

}


