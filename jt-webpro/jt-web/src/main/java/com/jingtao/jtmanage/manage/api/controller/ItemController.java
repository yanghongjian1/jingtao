package com.jingtao.jtmanage.manage.api.controller;


import com.jingtao.jtmanage.manage.api.module.Item;
import com.jingtao.jtmanage.manage.api.module.ItemDesc;
import com.jingtao.jtmanage.manage.api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //192.168.159.144:8093/items/562379.html
    @RequestMapping("/{itemId}")
    public String findItemById(@PathVariable Long itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        ItemDesc itemDesc = itemService.findItemDescById(itemId);

        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }
}
