package com.jingtao.jtmanage.manage.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jingtao.jtcommon.configurer.HttpClientHelper;
import com.jingtao.jtcommon.vo.SysResult;
import com.jingtao.jtmanage.manage.api.module.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private HttpClientHelper httpClient;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Cart> findCartByUserId(Long userId) {
        String uri = "http://192.168.159.144:8095/cart/query/" + userId;
//		String uri = "http://192.168.159.144/cart/query/"+userId;
        String jsonData = httpClient.doGet(uri);

        SysResult sysResult = null;
        try {
            sysResult = objectMapper.readValue(jsonData, SysResult.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Cart> cartList = (List<Cart>) sysResult.getData();

        return cartList;
    }

    @Override
    public void saveCart(Cart cart) {
        //定义uri
//		String uri = "http://192.168.159.144:8095/cart/save";
        String uri = "http://192.168.159.144:8095/cart/save";

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", cart.getUserId() + "");
        params.put("itemId", cart.getItemId() + "");
        params.put("itemTitle", cart.getItemTitle());
        params.put("itemImage", cart.getItemImage());
        params.put("itemPrice", cart.getItemPrice() + "");
        params.put("num", cart.getNum() + "");

        httpClient.doPost(uri, params);
    }

    @Override
    public void updateCartNum(Long userId, Long itemId, Integer num) {
//		String uri = "http://192.168.159.144/cart/update/num/"+userId+"/"+itemId +"/"+num;
        String uri = "http://192.168.159.144:8095/cart/update/num/" + userId + "/" + itemId + "/" + num;

        httpClient.doGet(uri);
    }


    @Override
    public void deleteCart(Long userId, Long itemId) {

        String uri = "http://192.168.159.144:8095/cart/delete/" + userId + "/" + itemId;

        httpClient.doGet(uri);
    }


}
