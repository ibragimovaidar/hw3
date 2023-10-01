package com.github.raily01.shoprestservice.repository;

import com.github.raily01.shoprestservice.model.domain.Product;
import com.github.raily01.shoprestservice.model.request.ProductRequest;
import com.github.raily01.shoprestservice.model.response.ProductResponse;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> getById(String vendorCode);

    Product save(Product product);

    Product update(Product product);

    void delete(String vendorCode);
}
