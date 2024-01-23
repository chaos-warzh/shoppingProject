package com.itheima.springbootinit.User;

import com.itheima.springbootinit.Goods.Goods;
import com.itheima.springbootinit.orders.Order;
import jakarta.persistence.*;
import org.hibernate.mapping.Collection;
import org.springframework.validation.annotation.Validated;

//import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Validated
@Entity
@Table(name = "user")
public class User {
    @Id
    private String name;
    private String password;
//    @Min(value = 0, message = "年龄不能为负数")
    private int age;
    private int id;

    private String imagePath; // 用户头像

    static final String defaultPath = "E:\\projects\\shoppingProject\\src\\main\\resources\\static\\images\\_default_user_image.png";

    private Integer balance; // 账户余额

//    @Embedded
//    @Transient
    @OneToMany(targetEntity = Goods.class)
    private List<Goods> shoppingCart;

    @OneToMany(targetEntity = Order.class)
    private List<Order> orders;

    public User() {
        orders = new ArrayList<>();
        shoppingCart = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        if (this.balance >= 500_000_000) {
            this.balance = 500_000_000;
        }
    }

    public int changeBalance(int changeNum, boolean isPositive) {
        if (isPositive) {
            if ((long) (this.balance) + (long) changeNum >= 500_000_000L) {
                this.balance = 500_000_000;
            } else {
                this.balance += changeNum;
            }
        }
        else {
            if (this.balance >= -5_000) {
                this.balance -= changeNum;
            }
        }
        return this.balance;
    }

    public List<Goods> showCart() {
        return Collections.unmodifiableList(shoppingCart);
    }

    public String addToCart(Goods goods) {
        if (goods != null) {
            shoppingCart.add(goods);
            return "添加成功";
        }
        return "添加失败";
    }

    public String deleteFromCart(Goods goods) {
        if (goods != null) {
            shoppingCart.remove(goods);
            return "删除成功";
        }
        return "删除失败";
    }

    public String clearCart() {
        shoppingCart.clear();
        return "全部删除成功";
    }

    public List<Order> showOrders() {
        return Collections.unmodifiableList(orders);
    }

    public String addToOrders(Order order) {
        if (order != null) {
            orders.add(order);
            return "添加成功";
        }
        return "添加失败";
    }

    public String deleteFromOrders(Order order) {
        if (order != null) {
            orders.remove(order);
            return "删除成功";
        }
        return "删除失败";
    }

    public String clearOrders() {
        orders.clear();
        return "全部删除成功";
    }


    public String toString() {
        return "id: " + id + ", name: " + name + ", password: " + password + ", age: " + age;
    }
}
