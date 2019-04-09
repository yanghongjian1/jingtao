package com.jingtao.jtorder.dubbo.api.dao;

import com.jingtao.jtcommon.core.Mapper;
import com.jingtao.jtorderapi.module.Order;

import java.util.Date;



public interface OrderMapper extends Mapper<Order> {

	void updateStatus(Date agoDate);


}