package com.bocman.InsuranceApp.exception;

public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 100606666465558687L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
