package com.github.raily01.shoprestservice.service;

import com.github.raily01.shoprestservice.exception.ProductNotFoundException;
import com.github.raily01.shoprestservice.model.domain.Product;
import com.github.raily01.shoprestservice.model.request.ProductRequest;
import com.github.raily01.shoprestservice.model.request.ProductUpdateRequest;
import com.github.raily01.shoprestservice.model.response.ProductResponse;
import com.github.raily01.shoprestservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductResponse getProduct(String vendorCode) {
        return productRepository.getById(vendorCode)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(vendorCode));
    }

    public ProductResponse saveProduct(ProductRequest productRequest) {
        return productMapper.toResponse(
                productRepository.save(productMapper.toProduct(productRequest))
        );
    }

    public ProductResponse updateProduct(String vendorCode, ProductUpdateRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        product.setVendorCode(vendorCode);
        return productMapper.toResponse(productRepository.update(product));
    }

    public void deleteProduct(String vendorCode) {
        productRepository.delete(vendorCode);
    }
}
