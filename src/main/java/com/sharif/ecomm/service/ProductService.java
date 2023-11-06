package com.sharif.ecomm.service;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.Product;
import com.sharif.ecomm.utils.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest product);
    String deleteProduct(Long productId) throws ProductException;
    Product updateProduct(Long productId, Product product) throws  ProductException;
    Product findProductById(Long id) throws ProductException;

    List<Product> findProductByCategory(String category);

    public Page<Product> getAllProducts(String category,List<String> colors,
                                        List<String> sizes,
                                        Integer minPrice,
                                        Integer maxPrice,
                                        Integer minDiscount,
                                        String sort,
                                        String stock,
                                        Integer pageNumber,
                                        Integer pageSize
                                        );
}
