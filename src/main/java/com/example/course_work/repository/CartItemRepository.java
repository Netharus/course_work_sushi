package com.example.course_work.repository;

import com.example.course_work.entity.CartItem;
import com.example.course_work.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByProduct(Product product);
    Optional<CartItem> findByCartUserIdAndProductId(Long UserId,Long ProductId);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cart_item (product_id,quantity,cart_id ) VALUES (?1,?2,?3)" ,nativeQuery = true)
    void addProductAndQuantity(@Param("product_id") Long productId, @Param("quantity") Long quantity,@Param("cart_id") Long cartId);
}
