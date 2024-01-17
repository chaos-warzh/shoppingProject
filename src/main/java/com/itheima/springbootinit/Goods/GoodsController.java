package com.itheima.springbootinit.Goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    GoodsDao goodsDao;

    @GetMapping("/getAllGoods")
    public List getAllGoods() {
        List all = goodsDao.findAll();
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }
}
