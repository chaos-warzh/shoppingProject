package com.itheima.springbootinit.Goods;

import com.itheima.springbootinit.User.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

//import javax.validation.Valid;
//import javax.validation.constraints.Min;


@Table(name = "goods")
@Entity
@Validated
public class Goods {
    @Id
    private String name;
    private String description;
//    @Min(value = 0, message = "价格不能为负数")
    private int price;
    @Deprecated
    private boolean status;
    private String imagePath;
    private GoodsType type;
    private Integer restNum;
    private String passwordOfGoods;
    static final String defaultPasswordOfGoods = "cafebabe";
    private boolean selected;

    public Goods() {
        this.restNum = restNum == null ? 1 : restNum;
        // 默认密码为 "cafebabe"
        this.passwordOfGoods = passwordOfGoods == null ? defaultPasswordOfGoods : passwordOfGoods;
        this.selected = true;
    }

    public Goods(String name, String description, int price, boolean status, String imagePath, GoodsType type,
                 @Nullable Integer restNum, @Nullable String passwordOfGoods) {
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

    public String toString() {
        return "name: " + name + " description: " + description + " price: " + price + " status: " + status + " imagePath: " + imagePath + " type: " + type;
    }
}
