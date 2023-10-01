package com.github.raily01.shoprestservice.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ShopRestServiceException {

    public ProductNotFoundException(String vendorCode) {
        super(HttpStatus.NOT_FOUND, String.format("Продукт с vendorCode '%s' не найден", vendorCode));
    }
}
