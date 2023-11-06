package com.sharif.ecomm.service.impl;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.Category;
import com.sharif.ecomm.model.Product;
import com.sharif.ecomm.repository.CategoryRepository;
import com.sharif.ecomm.repository.ProductRepository;
import com.sharif.ecomm.service.ProductService;
import com.sharif.ecomm.service.UserService;
import com.sharif.ecomm.utils.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = this.categoryRepository.findByName(req.getTopLevelCategory());
        if (topLevel == null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = this.categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
        if (secondLevel == null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = this.categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
        if (thirdLevel == null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);
            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setDescription(req.getDescription());
        product.setColor(req.getColor());
        product.setPrice(req.getPrice());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setSizes(req.getSize());
        product.setBrand(req.getBrand());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());
        Product save = this.productRepository.save(product);
        return save;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        this.productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if (req.getQuantity() != 0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()){
            return product.get();
        }
        throw new ProductException("Product is not Found with id +"+id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

//    @Override
//    public Page<Product> getAllProducts(String category,
//                                        List<String> colors,
//                                        List<String> sizes,
//                                        Integer minPrice,
//                                        Integer maxPrice,
//                                        Integer minDiscount,
//                                        String sort,
//                                        String stock,
//                                        Integer pageNumber,
//                                        Integer pageSize) {
//
//        Pageable pageable = PageRequest.of(pageNumber,pageSize);
//        List<Product> products = this.productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
//
//        if (!colors.isEmpty()) {
//            products = products.stream().filter(a -> colors.stream()
//                    .anyMatch(c-> c.equalsIgnoreCase(a.getColor())))
//                    .collect(Collectors.toList());
//        }
//
//        if (stock != null) {
//            if (stock.equalsIgnoreCase("in_stock")) {
//                products= products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
//            } else if (stock.equals("out_of_stock")){
//                products = products.stream().filter(a-> a.getQuantity() < 1).collect(Collectors.toList());
//            }
//        }
//
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());
//        List<Product> pageContent = products.subList(startIndex,endIndex);
//        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());
//        return filteredProducts;
//    }

//    @Override
//    public Page<Product> getAllProducts(String category,
//                                        List<String> colors,
//                                        List<String> sizes,
//                                        Integer minPrice,
//                                        Integer maxPrice,
//                                        Integer minDiscount,
//                                        String sort,
//                                        String stock,
//                                        Integer pageNumber,
//                                        Integer pageSize) {
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        List<Product> products = this.productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
//
//        if (!colors.isEmpty()) {
//            products = products.stream()
//                    .filter(product -> colors.contains(product.getColor()))
//                    .collect(Collectors.toList());
//        }
//
//        if (stock != null) {
//            if (stock.equalsIgnoreCase("in_stock")) {
//                products = products.stream()
//                        .filter(product -> product.getQuantity() > 0)
//                        .collect(Collectors.toList());
//            } else if (stock.equals("out_of_stock")) {
//                products = products.stream()
//                        .filter(product -> product.getQuantity() < 1)
//                        .collect(Collectors.toList());
//            }
//        }
//
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
//        List<Product> pageContent = products.subList(startIndex, endIndex);
//        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
//        return filteredProducts;
//    }

    @Override
    public Page<Product> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        // Check and set default values for pageNumber and pageSize
        if (pageNumber < 0) {
            pageNumber = 0;
        }

        if (pageSize <= 0) {
            // Set a default page size or handle as needed
            pageSize = 10; // Example default page size
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = this.productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if (!colors.isEmpty()) {
            products = products.stream().filter(product -> colors.contains(product.getColor())).collect(Collectors.toList());
        }

        if ("in_stock".equalsIgnoreCase(stock)) {
            products = products.stream().filter(product -> product.getQuantity() > 0).collect(Collectors.toList());
        } else if ("out_of_stock".equalsIgnoreCase(stock)) {
            products = products.stream().filter(product -> product.getQuantity() <= 0).collect(Collectors.toList());
        }

        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, products.size());

        if (startIndex < products.size()) {
            List<Product> pageContent = products.subList(startIndex, endIndex);
            Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
            return filteredProducts;
        } else {
            // Handle the case where there are no more products to display.
            return Page.empty(); // Or handle as needed
        }
    }
}
