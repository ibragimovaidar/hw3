package com.github.raily01.shoprestservice.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {

    private String vendorCode;

    private String name;

    private Long price;

    private Long quantity;
}
