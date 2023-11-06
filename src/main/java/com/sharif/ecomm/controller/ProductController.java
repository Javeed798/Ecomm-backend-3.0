package com.sharif.ecomm.controller;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.Product;
import com.sharif.ecomm.model.Size;
import com.sharif.ecomm.service.ProductService;
import com.sharif.ecomm.utils.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProducts(
            @RequestParam String category,
            @RequestParam List<String> color,
            @RequestParam List<String> size,
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice,
            @RequestParam Integer minDiscount,
            @RequestParam String sort,
            @RequestParam String stock,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
            ) {
        Page<Product> allProducts = productService.getAllProducts(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = this.productService.findProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/products/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
        Product createdProduct = productService.createProduct(req);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product req) throws ProductException {

        Product updatedProduct = productService.updateProduct(productId, req);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) throws ProductException {
        String message = productService.deleteProduct(productId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


}
