package com.itheima.springbootinit.Goods;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Table(name = "goods")
@Entity
@Validated
public class Goods {
    @Id
    private String name;
    private String description;
    @Min(value = 0, message = "价格不能为负数")
    private int price;
    private boolean status;
    private String imagePath;
    private GoodsType type;

    public Goods() {
    }

    public Goods(String name, String description, int price, boolean status, String imagePath, GoodsType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.imagePath = imagePath;
        this.type = type;
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

    public String toString() {
        return "name: " + name + " description: " + description + " price: " + price + " status: " + status + " imagePath: " + imagePath + " type: " + type;
    }
}
