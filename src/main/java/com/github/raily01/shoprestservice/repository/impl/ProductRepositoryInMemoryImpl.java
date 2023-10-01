package com.github.raily01.shoprestservice.repository.impl;

import com.github.raily01.shoprestservice.exception.ProductAlreadyExistsException;
import com.github.raily01.shoprestservice.exception.ProductNotFoundException;
import com.github.raily01.shoprestservice.model.domain.Product;
import com.github.raily01.shoprestservice.repository.ProductRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ConditionalOnProperty(prefix = "app", name = "repository", havingValue = "in-memory")
@Repository
public class ProductRepositoryInMemoryImpl implements ProductRepository {

    private final ConcurrentHashMap<String, Product> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Product> getById(String vendorCode) {
        if (storage.containsKey(vendorCode)) {
            return Optional.of(storage.get(vendorCode));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Product save(Product product) {
        String key = product.getVendorCode();
        if (storage.containsKey(key)) {
            throw new ProductAlreadyExistsException(key);
        }
        storage.put(key, product);
        return product;
    }

    @Override
    public Product update(Product product) {
        String key = product.getVendorCode();
        if (!storage.containsKey(key)) {
            throw new ProductNotFoundException(key);
        }
        storage.put(key, product);
        return product;
    }

    @Override
    public void delete(String vendorCode) {
        if (!storage.containsKey(vendorCode)) {
            throw new ProductNotFoundException(vendorCode);
        }
        storage.remove(vendorCode);
    }
}
