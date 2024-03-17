package com.example.course_work.repository;

import com.example.course_work.entity.Cart;
import com.example.course_work.entity.CartItem;
import com.example.course_work.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long id);

}
