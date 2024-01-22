package com.itheima.springbootinit.User;

import com.itheima.springbootinit.Goods.Goods;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserDao userDao;

    // 根据名字查找用户
    @GetMapping("/getOne")
    public User getOne(@RequestParam("name") String name) {
        if (userDao.findById(name).isPresent()) {
            User user = userDao.findById(name).get();
            return user;
        }
        // unavailable ID
        return null;
    }

    // 根据名字和密码查找用户 (用于用户登录)
    @GetMapping("/getOneSecurely")
    public User getOne(@RequestParam("name") String name, @RequestParam("password") String password) {
        if (userDao.findById(name).isPresent()) {
            User user = userDao.findById(name).get();
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                // wrong password
                return null;
            }
        }
        // unavailable ID
        return null;
    }

    // 得到数据库里的所有用户
    @GetMapping("/getAll")
    public List getAll() {
        List all = userDao.findAll();
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }


    // 添加一个用户 (用于用户注册)
    @GetMapping("/add")
    public User add (@RequestParam("name") String name,
                     @RequestParam("age") @Min(value = 0, message = "年龄不能为负数") int age,
                     @RequestParam("id") int id,
                     @RequestParam("password") String password,
                     @Nullable @RequestParam("imagePath") String imagePath) {
        // check
        if (getOne(name) != null) {
            // registered
            System.out.println("The name already existed!");
            return null;
        }

        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setId(id);
        user.setPassword(password);
        user.setImagePath(User.defaultPath);
        // 创建用户 (with 头像)
        if (imagePath != null) {
            user.setImagePath(imagePath);
        }
        User save = userDao.save(user);
        return save;
    }

    // 删除一个用户
    @GetMapping("/deleteOne")
    @Transactional
    public String deleteOne(@RequestParam("name") String name) {
        userDao.deleteByName(name);
        return "删除成功";
    }

    // 删除所有用户
    @GetMapping("/deleteAll")
    public String deleteAll() {
        userDao.deleteAll();
        return "全部删除成功";
    }
    @Transactional
    @PostMapping("/updateUserImage") // 更新用户头像
    public String updateGoodsImage(MultipartFile file, String userName) {
        userName = URLDecoder.decode(userName, StandardCharsets.UTF_8);
        // file检验
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 重命名文件
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = userName + suffixName;
        // 文件上传路径
        String filePath = "E:\\projects\\shoppingProject\\src\\main\\resources\\static\\images\\" + fileName;
        try {
            // 保存文件
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 更新数据库
        User user = userDao.findByName(userName);
        System.out.println(user.toString());
        user.setImagePath(fileName);

        System.out.println(fileName);
        return "images/" + fileName;
    }
}
