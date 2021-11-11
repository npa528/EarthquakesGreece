package com.example.earthquakesgreece.viewmodels;

import java.util.concurrent.Executor;

public class ServiceRepository {

    private final Executor executor;


    public ServiceRepository(Executor executor) {
        this.executor = executor;
    }
}
