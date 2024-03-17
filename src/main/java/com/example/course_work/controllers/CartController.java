package com.example.course_work.controllers;

import com.example.course_work.entity.Cart;
import com.example.course_work.entity.CartItem;
import com.example.course_work.service.CartItemService;
import com.example.course_work.service.CartService;
import com.example.course_work.service.ProductService;
import com.example.course_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/cart" )
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String viewCart(Model model, @AuthenticationPrincipal UserDetails loggedUser){
        Cart cart= cartService.findByUserId(userService.getByEmail(loggedUser.getUsername()).getId());
        List<CartItem> cartItems= cart.getCartItem().stream().toList();
        float estimatedTotal=0;
        for (CartItem item: cartItems){
            estimatedTotal+=item.getProduct().getPrice()*item.getQuantity();
        }
        model.addAttribute("cartItems",cartItems);
        model.addAttribute("estimatedTotal",estimatedTotal);
        return "user_content/cart";
    }
}
