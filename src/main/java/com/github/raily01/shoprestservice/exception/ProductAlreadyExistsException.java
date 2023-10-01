package com.github.raily01.shoprestservice.exception;

import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends ShopRestServiceException {
    public ProductAlreadyExistsException(String vendorCode) {
        super(HttpStatus.BAD_REQUEST, String.format("Продукт с vendorCode '%s' уже существует", vendorCode));
    }
}
