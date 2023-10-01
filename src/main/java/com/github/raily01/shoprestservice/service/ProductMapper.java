package com.github.raily01.shoprestservice.service;

import com.github.raily01.shoprestservice.model.domain.Product;
import com.github.raily01.shoprestservice.model.request.ProductRequest;
import com.github.raily01.shoprestservice.model.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    Product toProduct(ProductRequest product);
}
