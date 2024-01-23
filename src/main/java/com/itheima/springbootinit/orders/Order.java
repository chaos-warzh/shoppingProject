package com.itheima.springbootinit.orders;

import com.itheima.springbootinit.Goods.Goods;
import com.itheima.springbootinit.Goods.GoodsType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * 订单类
 */
@Entity
@Validated
@Table(name = "orders")
public class Order {
  // default: name + "Bought" / "Sold"
  @Id
  private String orderName;
  private String name;
  private String description;
  @Min(value = 0, message = "价格不能为负数")
  private int price;
  private boolean status;
  private String imagePath;
  private GoodsType type;
  private Integer restNum;
  private String passwordOfGoods;
  static final String defaultPasswordOfGoods = "cafebabe";
  private boolean selected;
  private boolean isBought; // true-购买的; false-卖出的

  // 订单状态, 枚举类 {已收货 & 未收货}
  public enum OrderStatus {
    RECEIVED, NOT_RECEIVED,
  }
  private OrderStatus orderStatus;

  public Order() {
  }

  @Deprecated
  public Order(String name, String description, int price, boolean status, String imagePath, GoodsType type,
               @Nullable Integer restNum, @Nullable String passwordOfGoods, boolean isBought) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.status = status;
    this.imagePath = imagePath;
    this.type = type;
    this.restNum = restNum == null ? 1 : restNum;
    // 默认密码为 "cafebabe"
    this.passwordOfGoods = passwordOfGoods == null ? defaultPasswordOfGoods : passwordOfGoods;
    this.selected = true;
    this.isBought = isBought;
    // 默认未收到
    this.orderStatus = OrderStatus.NOT_RECEIVED;

    this.orderName = name + (isBought ? "Bought" : "Sold");
  }

  public Order(Goods goods, boolean isBought) {
    this.name = goods.getName();
    this.description = goods.getDescription();
    this.price = goods.getPrice();
    this.status = goods.getStatus();
    this.imagePath = goods.getImagePath();
    this.type = goods.getType();
    this.restNum = goods.getRestNum();
    this.passwordOfGoods = goods.getPasswordOfGoods();
    this.selected = goods.isSelected();
    this.isBought = isBought;
    this.orderStatus = OrderStatus.NOT_RECEIVED;

    this.orderName = name + (isBought ? "Bought" : "Sold");
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getPrice() {
    return price;
  }

  public boolean getStatus() {
    return status;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String desc) {
    this.description = desc;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImagePath() {
    return imagePath;
  }

  public GoodsType getType() {
    return type;
  }

  public void setType(GoodsType type) {
    this.type = type;
  }

  public int getRestNum() {
    return restNum;
  }

  public void setRestNum(int restNum) {
    if (restNum < 0) {
      return;
    }
    this.restNum = restNum;
  }
  public void decreaseRestNum() {
    if (restNum > 0)
      restNum--;
  }

  public String getPasswordOfGoods() {
    return passwordOfGoods;
  }

  public void setPasswordOfGoods(String passwordOfGoods) {
    this.passwordOfGoods = passwordOfGoods;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public String getOrderName() {
    return orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  public boolean isBought() {
    return isBought;
  }

  public void setBought(boolean bought) {
    isBought = bought;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String toString() {
    return "name: " + name + " description: " + description + " price: " + price + " status: " + status + " imagePath: " + imagePath + " type: " + type;
  }
}
