package com.sharif.ecomm.repository;

import com.sharif.ecomm.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);

    @Query("SELECT c from Category c WHERE c.name=:name" +
            " AND c.parentCategory.name=:parentCategoryName")
    Category findByNameAndParent(@Param("name") String name,
                                 @Param("parentCategoryName") String parentCategoryName);
}
