package com.jingtao.jtmanage.manage.api.service;


import com.jingtao.jtmanage.manage.api.module.Item;
import com.jingtao.jtmanage.manage.api.module.ItemDesc;

public interface ItemService {

    Item findItemById(Long itemId);

    ItemDesc findItemDescById(Long itemId);

}
