package com.itheima.springbootinit.User;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@Entity
@Table(name = "user")
public class User {
    @Id
    private String name;
    private String password;
    @Min(value = 0, message = "年龄不能为负数")
    private int age;
    private int id;

    private String imagePath; // 用户头像

    static final String defaultPath = "E:\\projects\\shoppingProject\\src\\main\\resources\\static\\images\\_default_user_image.png";

    private Integer balance; // 账户余额

    public User() {
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

    public String toString() {
        return "id: " + id + ", name: " + name + ", password: " + password + ", age: " + age;
    }
}
