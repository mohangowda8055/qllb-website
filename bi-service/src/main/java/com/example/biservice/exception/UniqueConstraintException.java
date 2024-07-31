package com.example.biservice.exception;

public class UniqueConstraintException extends RuntimeException{

    public UniqueConstraintException(String message){
        super(message);
    }
}
