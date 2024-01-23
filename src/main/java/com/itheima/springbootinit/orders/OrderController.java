package com.itheima.springbootinit.orders;

import com.itheima.springbootinit.Goods.Goods;
import com.itheima.springbootinit.Goods.GoodsDao;
import com.itheima.springbootinit.Goods.GoodsType;
import com.itheima.springbootinit.User.User;
import com.itheima.springbootinit.User.UserDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class OrderController {
  @Autowired
  UserDao userDao;

  @Autowired
  OrderDao orderDao;

  @Autowired
  GoodsDao goodsDao;

    // 添加订单
    @GetMapping("/addToOrders")
    @Transactional
    public String addToOrders(@RequestParam("userName") String userName, @RequestParam("orderName") String orderName) {
        User user = userDao.findByName(userName);
        Order order = orderDao.findByOrderName(orderName);
        return user.addToOrders(order);
    }

    // 删除订单
    @GetMapping("/deleteFromOrders")
    public String deleteFromOrders(@RequestParam("userName") String userName, @RequestParam("orderName") String orderName) {
        User user = userDao.findByName(userName);
        Order order = orderDao.findByOrderName(orderName);
        return user.deleteFromOrders(order);
    }

    // 展示订单
    @GetMapping("/showOrders")
    @Transactional
    public List<Order> showOrders(@RequestParam("userName") String userName) {
        User user = userDao.findByName(userName);
        return user.showOrders();
    }

    // 清空订单
    @GetMapping("/deleteAllOrders")
    public void deleteAllOrders() {
        orderDao.deleteAll();
    }

    @GetMapping("/addOrder")
    public Order addOrder(@RequestParam("goodsName") String goodsName,
                          @RequestParam("isBought") boolean isBought) {
        Goods goods = goodsDao.findByName(goodsName);
        Order order = new Order(goods, isBought);
        Order save = orderDao.save(order);
        return save;
    }

}
