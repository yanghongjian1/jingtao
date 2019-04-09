package com.jingtao.jtmanage.manage.api.service;

import java.util.List;

import com.jingtao.jtmanage.manage.api.module.Cart;


public interface CartService {

    List<Cart> findCartByUserId(Long userId);

    void saveCart(Cart cart);

    void updateCartNum(Long userId, Long itemId, Integer num);


    void deleteCart(Long userId, Long itemId);
}
