package com.example.course_work.controllers;

import com.example.course_work.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductRestController {
    @Autowired
    ProductService productService;

    @PostMapping("/products/check_unique")
    public ResponseEntity<String> checkDuplicate(@Param("id") Long id, @Param("name") String name){
        String response = productService.isProductUnique(id,name)?"OK":"Duplicated";
        return ResponseEntity.ok(response);
    }

}
