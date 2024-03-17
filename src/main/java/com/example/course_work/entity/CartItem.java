package com.example.course_work.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private Long quantity;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @Transient
    public float getSubtotal(){
        return product.getPrice() * quantity;
    }
}
