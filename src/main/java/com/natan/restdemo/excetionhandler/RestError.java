package com.natan.restdemo.excetionhandler;

import java.util.Objects;

record RestErro(String message, String causeMessage) {
    public static String UNKNOWN_MESSAGE = "sem mensagem";
    public static String UNKNOWN_CAUSE = "sem causa";

    public RestErro {
        Objects.requireNonNull(message);
        Objects.requireNonNull(causeMessage);
    }

    public RestErro(String message) {
        this(message, UNKNOWN_MESSAGE);
    }

    public static RestErro semCausa(String causeMessage) {

        return new RestErro(UNKNOWN_CAUSE, causeMessage);
    }
}