package com.example.nccnitjalandhar.notification;

import com.google.firebase.messaging.FirebaseMessagingService;

public class tokens {

private  String tokens;

    public tokens(String tokens) {
        this.tokens = tokens;
    }

    public tokens() {
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }
}
