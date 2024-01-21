package com.itheima.springbootinit;

import com.itheima.springbootinit.Goods.Goods;
import com.itheima.springbootinit.Goods.GoodsType;
import com.itheima.springbootinit.User.User;
import com.itheima.springbootinit.User.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootInitApplicationTests {

    @Test
    void contextLoads() {
    }

    @LocalServerPort
    private int port;

    public int setPort(int port) {
        this.port = port;
        return port;
    }

    @Autowired
    private TestRestTemplate restTemplate;

    // 辅助方法, 清空数据库
    void clearSchema(String schema) {
        String url = "http://localhost:" + port + "/deleteAll" + schema;
        restTemplate.getForObject(url, String.class);
    }

    @Test
    void TestSet() {
        // 先把数据库清空
        clearSchema("");
        // 存入User数据
        String url = "http://localhost:" + port + "/add?name=Wang&age=18&id=001&password=123456";
        // 得到返回值
        User response = restTemplate.getForObject(url, User.class);
        // 数据存入正常
        assertEquals("Wang", response.getName());
        assertEquals(18 , response.getAge());
        assertEquals(1, response.getId());
        assertEquals("123456", response.getPassword());
        // getAll请求
        url = "http://localhost:" + port + "/getAll";
        List<UserDao> response2 = restTemplate.getForObject(url, List.class);
        assertEquals(1, response2.size());
        // getOne请求
        url = "http://localhost:" + port + "/getOne?name=Wang";
        User response3 = restTemplate.getForObject(url, User.class);

        // 可以正常查询到所需数据
        assertEquals("Wang", response3.getName());
        assertEquals(18 , response3.getAge());
        assertEquals(1, response3.getId());
        assertEquals("123456", response3.getPassword());

        // 最后再次清空数据库, 有始有终.
        clearSchema("");
    }

    @Test
    void TestImage() throws IOException {
        // 创建goods对象
        String url = "http://localhost:" + port +
            "/addGoods?name=微积分&description=不是数学分析&price=1&status=true" +
            "&imagePath=C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones&type=学习与办公";
        // 得到返回值
        Goods response = restTemplate.getForObject(url, Goods.class);
        assertEquals("微积分", response.getName());
        assertEquals("不是数学分析", response.getDescription());
        assertEquals(1, response.getPrice());
        assert(response.getStatus());
        assertEquals("C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones", response.getImagePath());
        assertEquals(GoodsType.学习与办公, response.getType());

        // 图片路径
        String imagePath = "C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\PvZ_Logo.jpg";

        // Load the image as a Resource
        Resource imageResource = new ByteArrayResource(Files.readAllBytes(Paths.get(imagePath))) {
            @Override
            public String getFilename() {
                return "PvZ_logo.jpg"; // Set the desired filename
            }
        };

        // 准备数据
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", imageResource);
        map.add("goodsName", "微积分");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create the request entity with headers and form data
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

        // Make the POST request
        ResponseEntity<String> response2 = restTemplate.exchange(
            "http://localhost:" + port + "/updateGoodsImage?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8",
            HttpMethod.POST,
            requestEntity,
            String.class
        );

        // Assert the response as needed
        assert response2.hasBody();
        assert response2.getBody().contains("images/微积分.jpg");

        // 最后清空数据库
//        clearSchema("Goods");
    }

}
