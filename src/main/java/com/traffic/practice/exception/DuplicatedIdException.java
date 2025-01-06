package com.traffic.practice.exception;

public class DuplicatedIdException extends RuntimeException{
    public DuplicatedIdException(String msg) {
        super(msg);
    }
}
