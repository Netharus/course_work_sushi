package com.example.course_work.controllers;

import com.example.course_work.entity.CartItem;
import com.example.course_work.entity.Product;
import com.example.course_work.entity.User;
import com.example.course_work.service.CartItemService;
import com.example.course_work.service.CartService;
import com.example.course_work.service.ProductService;
import com.example.course_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/cart" )
public class CartRestController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add/{productId}/{quantity}")
    public String addItem(@PathVariable("productId") Long productId,
                          @PathVariable("quantity") Long quantity,@AuthenticationPrincipal UserDetails loggedUser){
        User user=userService.getByEmail(loggedUser.getUsername());
        Product product=productService.findById(productId).get();

        cartItemService.createItem(user.getId(),product.getId(),quantity,cartService.findByUserId(user.getId()));

        return "vse ok";
    }
    @PostMapping("/update/{productId}/{quantity}")
    public String updateProductQuantity(@PathVariable("productId") Long productId,
                                        @PathVariable("quantity") Long quantity,@AuthenticationPrincipal UserDetails loggedUser) {
            User user=userService.getByEmail(loggedUser.getUsername());
            float subtotal=cartItemService.updateQuantity(productId,quantity,user.getId());
            return String.valueOf(subtotal);
    }
    @DeleteMapping("/remove/{productId}")
    public String removeProduct(@PathVariable("productId") Long productId,@AuthenticationPrincipal UserDetails loggedUser ){
            User user=userService.getByEmail(loggedUser.getUsername());
            cartItemService.removeProduct(productId,user.getId());
            return "Продукт был удалён из вашей корзины";
    }
}
