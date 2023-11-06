package com.sharif.ecomm.utils;

import com.sharif.ecomm.model.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int discountPercent;

    private int quantity;

    private String color;

    private String brand;

    private Set<Size> size = new HashSet<>();

    private String imageUrl;

    private String topLevelCategory;

    private String secondLevelCategory;

    private String thirdLevelCategory;

}
