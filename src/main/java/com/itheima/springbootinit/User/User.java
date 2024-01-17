package com.itheima.springbootinit.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    public String toString() {
        return "id: " + id + ", name: " + name + ", password: " + password + ", age: " + age;
    }
}
