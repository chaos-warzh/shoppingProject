package com.itheima.springbootinit.orders;

import com.itheima.springbootinit.Goods.Goods;
import com.itheima.springbootinit.Goods.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
  Order findByOrderName(String orderName);

  Order findByName(String name);
  List findByIsBought(boolean isBought);

  List findByStatus(Order.OrderStatus status);

  public void deleteByName(String orderName);
}
