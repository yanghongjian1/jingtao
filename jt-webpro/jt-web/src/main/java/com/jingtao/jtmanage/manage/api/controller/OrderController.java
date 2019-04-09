package com.jingtao.jtmanage.manage.api.controller;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jingtao.jtcommon.vo.SysResult;
import com.jingtao.jtmanage.constants.UserThreadLocal;
import com.jingtao.jtmanage.manage.api.module.Cart;
import com.jingtao.jtmanage.manage.api.service.CartService;
import com.jingtao.jtorderapi.module.Order;
import com.jingtao.jtorderapi.orderapi.DubboOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	private CartService cartService;

	@Reference(version = "1.0.0")
	private DubboOrderService orderService;
	
	//跳转到订单确认页面
	@RequestMapping("/create")
	public String create(Model model){
		Long userId = UserThreadLocal.get().getId();
		//获取购物车数据
		List<Cart> carts = cartService.findCartByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
	}
	
	//http://www.jt.com/service/order/submit
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order){
		Long userId = UserThreadLocal.get().getId();
		order.setUserId(userId);
		try {
			String orderId = orderService.saveOrder(order);
			if(StringUtils.isEmpty(orderId)){	
				throw new RuntimeException();
			}
			return SysResult.oK(orderId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"订单提交失败");
		
	}
	
	
	//跳转到成功页面
	@RequestMapping("/success")
	public String success(String id,Model model){
		
		Order order = orderService.findOrderById(id);
		
		model.addAttribute("order", order);
		return "success";
	}
	
}
