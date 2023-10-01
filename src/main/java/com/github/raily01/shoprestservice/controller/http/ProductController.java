package com.github.raily01.shoprestservice.controller.http;

import com.github.raily01.shoprestservice.model.request.ProductRequest;
import com.github.raily01.shoprestservice.model.request.ProductUpdateRequest;
import com.github.raily01.shoprestservice.model.response.ProductResponse;
import com.github.raily01.shoprestservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{vendorCode}")
    public ProductResponse getProduct(@PathVariable("vendorCode") String vendorCode) {
        return productService.getProduct(vendorCode);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createProduct(@RequestBody @Validated ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{vendorCode}")
    public ProductResponse updateProduct(@PathVariable("vendorCode") String vendorCode,
                                         @RequestBody @Validated ProductUpdateRequest productRequest) {
        return productService.updateProduct(vendorCode, productRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{vendorCode}")
    public void deleteProduct(@PathVariable String vendorCode) {
        productService.deleteProduct(vendorCode);
    }

}
