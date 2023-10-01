package com.github.raily01.shoprestservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ShopRestServiceException extends RuntimeException {

    private final HttpStatus status;

    private final String errorDescription;

    public ShopRestServiceException(HttpStatus status, String errorDescription) {
        this.status = status;
        this.errorDescription = errorDescription;
    }

    public ShopRestServiceException(String message, HttpStatus status, String errorDescription) {
        super(message);
        this.status = status;
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        return String.format("ShopRestServiceException(status=%s,errorDescription=%s,message=%s)",
                status, errorDescription, super.getMessage());
    }
}
