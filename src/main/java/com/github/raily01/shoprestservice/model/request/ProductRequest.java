package com.github.raily01.shoprestservice.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductRequest {

    private String vendorCode;

    private String name;

    private Long price;

    private Long quantity;
}
