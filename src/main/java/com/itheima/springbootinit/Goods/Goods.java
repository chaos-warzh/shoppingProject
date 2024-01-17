package com.itheima.springbootinit.Goods;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "goods")
@Entity
public class Goods {
    @Id
    private String name;
    private int id;
    private int price;
    private boolean status;

    public Goods() {
    }

    public Goods(String name, int id, int price, boolean status) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
