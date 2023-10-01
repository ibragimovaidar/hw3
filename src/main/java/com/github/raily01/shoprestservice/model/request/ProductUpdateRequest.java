package com.github.raily01.shoprestservice.model.request;

import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class ProductUpdateRequest extends ProductRequest {

    private String name;

    private Long price;

    private Long quantity;
}
