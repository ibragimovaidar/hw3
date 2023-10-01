package com.github.raily01.shoprestservice.model.response;

import lombok.Data;

@Data
public class ProductResponse {

    private String vendorCode;

    private String name;

    private Long price;

    private Long quantity;
}
