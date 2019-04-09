package com.jingtao.jtmanage.manage.api.controller;

import java.util.List;

import com.jingtao.jtcommon.vo.SysResult;
import com.jingtao.jtmanage.constants.UserThreadLocal;
import com.jingtao.jtmanage.manage.api.module.Cart;
import com.jingtao.jtmanage.manage.api.service.CartService;
import com.jingtao.jtmanage.manage.api.utiles.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @RequestMapping("/show")
    public String findCartByUserId(Model model) {
//		Long userId = 7L; //暂时为7 后期维护
        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartByUserId(userId);
        model.addAttribute("cartList", cartList);
        return "cart";
    }


    //实现购车数据新增
    @RequestMapping("/add/{itemId}")
    public String saveCart(@PathVariable Long itemId, Cart cart) {
        //封装数据
        Long userId = UserThreadLocal.get().getId();
        cart.setItemId(itemId);
        cart.setUserId(userId);
        cartService.saveCart(cart);
        //重定向购物车展现页面
        return "redirect:/cart/show.html";
    }

    //修改购物车商品数量  /update/num/562379/9.html
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartNum(
            @PathVariable Long itemId,
            @PathVariable Integer num) {
        try {
            Long userId = UserThreadLocal.get().getId();
            cartService.updateCartNum(userId, itemId, num);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SysResult.build(201, "商品修改失败");
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId) {

        Long userId = UserThreadLocal.get().getId();

        cartService.deleteCart(userId, itemId);

        //跳转到购物车列表页面
        return "redirect:/cart/show.html";
    }


}
