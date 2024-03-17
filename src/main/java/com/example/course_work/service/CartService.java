package com.example.course_work.service;

import com.example.course_work.entity.Cart;
import com.example.course_work.entity.CartItem;
import com.example.course_work.entity.User;
import com.example.course_work.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;


    public Cart createNew(User user){
        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    public Cart findByUserId(Long userId){
        return cartRepository.findByUserId(userId);
    }

}
