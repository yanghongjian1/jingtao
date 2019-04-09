package com.jingtao.jtorderapi.orderapi;


import com.jingtao.jtorderapi.module.Order;

public interface DubboOrderService {
	
	public String saveOrder(Order order);

	public Order findOrderById(String orderId);
}
