package com.example.course_work.service;

import com.example.course_work.entity.Cart;
import com.example.course_work.entity.CartItem;
import com.example.course_work.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public void createItem(Long userId, Long productId, Long quantity, Cart cart){
        Optional<CartItem> cartItem=cartItemRepository.findByCartUserIdAndProductId(userId, productId);

        if(cartItem.isPresent()){
            cartItem.get().setQuantity(cartItem.get().getQuantity()+quantity);
            cartItemRepository.save(cartItem.get()).getId();
        }
        else{
            cartItemRepository.addProductAndQuantity(productId, quantity, cart.getId());
        }

    }

    public float updateQuantity(Long productId, Long quantity, Long userId) {
        Optional<CartItem> cartItem = cartItemRepository.findByCartUserIdAndProductId(userId,productId);
        cartItem.get().setQuantity(quantity);
        return  cartItemRepository.save(cartItem.get()).getProduct().getPrice()*quantity;
    }

    public void removeProduct(Long productId, Long userId) {
        Optional<CartItem> cartItem = cartItemRepository.findByCartUserIdAndProductId(userId,productId);
        cartItemRepository.deleteById(cartItem.get().getId());
    }
}
