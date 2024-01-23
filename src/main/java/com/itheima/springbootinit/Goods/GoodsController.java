package com.itheima.springbootinit.Goods;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    @GetMapping("/getByType")
    public List<Goods> getByType(@RequestParam("type") GoodsType type) {
        List<Goods> all = goodsDao.findByType(type);
        int len = all.size();
        for (Goods goods : all) {
            System.out.println(goods.toString());
        }
        return all;
    }

    @GetMapping("/addGoods")
    public Goods addGoods(@RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("price") int price,
                          @RequestParam("type") GoodsType type,
                          @Nullable @RequestParam("restNum") Integer restNum,
                          @Nullable @RequestParam("passwordOfGoods") String passwordOfGoods) {
        if (restNum == null) {
            restNum = 1;
        }
        if (passwordOfGoods == null) {
            passwordOfGoods = Goods.defaultPasswordOfGoods;
        }

        if (getByName(name) != null) {
            Goods goodsExisted = getByName(name);
            if (!goodsExisted.getPasswordOfGoods().equals(passwordOfGoods)) {
                // 认证失败
                return null;
            }
        }

        Goods goods = new Goods();
        goods.setName(name);
        goods.setDescription(description);
        goods.setPrice(price);
        goods.setType(type);
        goods.setRestNum(restNum);
        goods.setPasswordOfGoods(passwordOfGoods);
        Goods save = goodsDao.save(goods); // 持久化
        return save;
    }
    @GetMapping("changeStatus") // 商品状态开关
    public String changeStatus(@RequestParam("name") String name) {
        Goods goods = goodsDao.findByName(name);
        goods.setStatus(!goods.getStatus());
        goodsDao.save(goods);
        return "修改成功";
    }
    @GetMapping("getByName") // 根据名字查找商品
    public Goods getByName(@RequestParam("name") String name) {
        Goods goods = goodsDao.findByName(name);
        return goods;
    }
    @GetMapping("getByStatus") // 查看购物车里的商品(true) 或 未放入购物车的商品(false)
    public List getByStatus(@RequestParam("status") boolean status) {
        List all = goodsDao.findByStatus(status);
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }



    @Transactional
    @PostMapping("/updateGoodsImage") // 更新商品图片
    public String updateGoodsImage(MultipartFile file, String goodsName) {
        goodsName = URLDecoder.decode(goodsName, StandardCharsets.UTF_8);
        // file检验
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 重命名文件
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = goodsName + suffixName;
        // 文件上传路径
        String filePath = "E:\\projects\\shoppingProject\\src\\main\\resources\\static\\images\\" + fileName;
//        String filePath = "C:\\Users\\86139\\Desktop\\springboot-init\\src\\main\\resources\\static\\images\\" + fileName;
        try {
            // 保存文件
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 更新数据库
        Goods goods = goodsDao.findByName(goodsName);
//        System.out.println(goods.toString());
        String webPath = "http://localhost:8080/images/" + fileName;
        goods.setImagePath(webPath);

        System.out.println(fileName);
        return "images/" + fileName;
    }

    // 删除一个商品
    @GetMapping("/deleteOneGoods")
    @Transactional
    public String deleteOne(@RequestParam("name") String name) {
        Goods goods = getByName(name);
        if (goods != null) {
            goods.decreaseRestNum();
            if (goods.getRestNum() <= 0) {
                goodsDao.deleteByName(name);
                return "已删除此商品";
            }
            return "已减少一件商品";
        }
        return "无此商品";
    }

    // 删除所有商品
    @GetMapping("/deleteAllGoods")
    public String deleteAll() {
        goodsDao.deleteAll();
        return "全部商品删除成功";
    }

    // 改变购物车中是否已选中的状态
    @GetMapping("/changeSelected")
    public void changeSelected(@RequestParam("name") String name) {
        Goods goods = getByName(name);
        if (goods != null) {
            goods.setSelected(!goods.isSelected());
        }
    }

}
