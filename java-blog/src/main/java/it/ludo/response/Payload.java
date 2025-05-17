package it.ludo.response;

import org.springframework.http.HttpStatus;

public class Payload<T> {

    private T data;
    private String errorMessage;
    private int status; // codice di stato HTTP

//    costruttori

    public Payload() {
    }

    public Payload(T data, String errorMessage, HttpStatus status) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.status = status.value();
    }

    // getter e setter
    
    public T getdata() {
        return data;
    }

    public void setdata(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}