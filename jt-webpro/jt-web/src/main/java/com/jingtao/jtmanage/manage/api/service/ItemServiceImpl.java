package com.jingtao.jtmanage.manage.api.service;

import com.jingtao.jtcommon.configurer.HttpClientHelper;
import com.jingtao.jtmanage.manage.api.module.Item;
import com.jingtao.jtmanage.manage.api.module.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpClientHelper httpClient;


    private static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${manage.url}")
    private String manUrl;

    @Override
    public Item findItemById(Long itemId) {
        //1.定义远程访问的uri
//		String uri = "http://localhost:8091/web/item/findItemById?itemId=1472298887";
        String uri = manUrl + "/web/item/findItemById?itemId=" + itemId;
        //2.发起请求获取远程服务端数据
        String jsonData = httpClient.doGet(uri);
        Item item = null;
        try {
            item = objectMapper.readValue(jsonData, Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        //1.定义远程访问的uri
        String uri = manUrl + "/web/findItemDescById?itemId=" + itemId;
        //2.发起请求获取远程服务端数据
        String jsonData = httpClient.doGet(uri);
        ItemDesc itemDesc = null;
        try {
            itemDesc = objectMapper.readValue(jsonData, ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }
}
