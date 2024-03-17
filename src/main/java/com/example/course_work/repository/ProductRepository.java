package com.example.course_work.repository;


import com.example.course_work.entity.Category;
import com.example.course_work.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, PagingAndSortingRepository<Product,Long> {
    List<Product> findByCategory(Category category);

    Product findByName(String name);
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
            + "OR p.shortDescription LIKE %?1%")
    public Page<Product> findAll(String keyword, Pageable pageable);

    Long countById(Long id);
}
