//package com.itheima.springbootinit;
//
//import com.itheima.springbootinit.Goods.Goods;
//import com.itheima.springbootinit.Goods.GoodsType;
//import com.itheima.springbootinit.User.User;
//import com.itheima.springbootinit.User.UserDao;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//
//import java.awt.font.ShapeGraphicAttribute;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class SpringbootInitApplicationTests {
//
//    @Test
//    void contextLoads() {
//    }
//
//    @LocalServerPort
//    private int port;
//
//    public int setPort(int port) {
//        this.port = port;
//        return port;
//    }
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    // 辅助方法, 清空数据库
//    void clearSchema(String schema) {
//        String url = "http://localhost:" + port + "/deleteAll" + schema;
//        restTemplate.getForObject(url, String.class);
//    }
//
//    @Test
//    void TestSet() {
//        // 先把数据库清空
//        clearSchema("");
//        // 存入User数据
//        String url = "http://localhost:" + port + "/add?name=Wang&age=18&id=001&password=123456";
//        // 得到返回值
//        User response = restTemplate.getForObject(url, User.class);
//        // 数据存入正常
//        assertEquals("Wang", response.getName());
//        assertEquals(18 , response.getAge());
//        assertEquals(1, response.getId());
//        assertEquals("123456", response.getPassword());
//        // getAll请求
//        url = "http://localhost:" + port + "/getAll";
//        List<UserDao> response2 = restTemplate.getForObject(url, List.class);
//        assertEquals(1, response2.size());
//        // getOne请求
//        url = "http://localhost:" + port + "/getOne?name=Wang";
//        User response3 = restTemplate.getForObject(url, User.class);
//
//        // 可以正常查询到所需数据
//        assertEquals("Wang", response3.getName());
//        assertEquals(18 , response3.getAge());
//        assertEquals(1, response3.getId());
//        assertEquals("123456", response3.getPassword());
//
//        // 最后再次清空数据库, 有始有终.
//        clearSchema("");
//    }
//
//    @Test
//    void TestImage() throws IOException {
//        // 清除已有的商品表
//        clearSchema("Goods");
//
//        // 创建goods对象
//        String url = "http://localhost:" + port +
//            "/addGoods?name=微积分&description=不是数学分析&price=1&status=true" +
//            "&imagePath=C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones&type=学习与办公";
//        // 得到返回值
//        Goods response = restTemplate.getForObject(url, Goods.class);
//        assertEquals("微积分", response.getName());
//        assertEquals("不是数学分析", response.getDescription());
//        assertEquals(1, response.getPrice());
//        assert(response.getStatus());
//        assertEquals("C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones", response.getImagePath());
//        assertEquals(GoodsType.学习与办公, response.getType());
//
//        // 图片路径
//        String imagePath = "C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\PvZ_Logo.jpg";
//
//        // Load the image as a Resource
//        Resource imageResource = new ByteArrayResource(Files.readAllBytes(Paths.get(imagePath))) {
//            @Override
//            public String getFilename() {
//                return "PvZ_logo.jpg"; // Set the desired filename
//            }
//        };
//
//        // 准备数据
//        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        map.add("file", imageResource);
//        map.add("goodsName", "微积分");
//
//        // Set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        // Create the request entity with headers and form data
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
//
//        // Make the POST request
//        ResponseEntity<String> response2 = restTemplate.exchange(
//            "http://localhost:" + port + "/updateGoodsImage?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8",
//            HttpMethod.POST,
//            requestEntity,
//            String.class
//        );
//
//        // Assert the response as needed
//        assert response2.hasBody();
//        assert response2.getBody().contains("images/微积分.jpg");
//
//        // 最后清空数据库
////        clearSchema("Goods");
//    }
//
//    @Test
//    void TestImageDefault() throws IOException {
//        // 清空用户表
//        clearSchema("");
//
//        // 创建user对象
//        String url = "http://localhost:" + port +
//            "/add?name=ChihayaAnon&age=16&id=33&password=123456";
//
//        // 得到返回值
//        User response = restTemplate.getForObject(url, User.class);
//        assertEquals("ChihayaAnon", response.getName());
//        assertEquals(16, response.getAge());
//        assertEquals(33, response.getId());
//        assertEquals("E:\\projects\\shoppingProject\\src\\main\\resources\\static\\images\\_default_user_image.png",
//            response.getImagePath());
//    }
//
//    @Test
//    void TestImageUser() throws IOException {
//        // 清空用户表
//        clearSchema("");
//
//        // 创建user对象
//        String url = "http://localhost:" + port +
//            "/add?name=ChihayaAnon&age=16&id=33&password=123456"
//            + "&imagePath=C:\\Users\\DELL\\Desktop\\imageSet\\anon.png";
//
//        // 得到返回值
//        User response = restTemplate.getForObject(url, User.class);
//        assertEquals("ChihayaAnon", response.getName());
//        assertEquals(16, response.getAge());
//        assertEquals(33, response.getId());
//        assertEquals("C:\\Users\\DELL\\Desktop\\imageSet\\anon.png", response.getImagePath());
//
//        // 图片路径
//        String imagePath = "C:\\Users\\DELL\\Desktop\\imageSet\\anon.png";
//
//        // Load the image as a Resource
//        Resource imageResource = new ByteArrayResource(Files.readAllBytes(Paths.get(imagePath))) {
//            @Override
//            public String getFilename() {
//                return "anon.png"; // Set the desired filename
//            }
//        };
//
//        // 准备数据
//        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        map.add("file", imageResource);
//        map.add("userName", "ChihayaAnon");
//
//        // Set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        // Create the request entity with headers and form data
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
//
//        // Make the POST request
//        ResponseEntity<String> response2 = restTemplate.exchange(
//            "http://localhost:" + port + "/updateUserImage?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8",
//            HttpMethod.POST,
//            requestEntity,
//            String.class
//        );
//
//        // Assert the response as needed
//        assert response2.hasBody();
//        assert response2.getBody().contains("images/ChihayaAnon.png");
//
//        // 最后清空数据库
////        clearSchema("Goods");
//    }
//
//    @Test
//    void TestGoodsNum() {
//        // 清除已有的商品表
//        clearSchema("Goods");
//
//        // 创建goods对象
//        String url = "http://localhost:" + port +
//            "/addGoods?name=微积分&description=不是数学分析&price=1&status=true" +
//            "&imagePath=C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones&type=学习与办公";
//        // 得到返回值
//        Goods response = restTemplate.getForObject(url, Goods.class);
//        assertEquals("微积分", response.getName());
//        assertEquals("不是数学分析", response.getDescription());
//        assertEquals(1, response.getPrice());
//        assert(response.getStatus());
//        assertEquals("C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones", response.getImagePath());
//        assertEquals(GoodsType.学习与办公, response.getType());
//        assertEquals(1, response.getRestNum());
//
//         // 创建名字相同的goods对象, 数量变为100件
//        url = "http://localhost:" + port +
//            "/addGoods?name=微积分&description=不是数学分析&price=1&status=true" +
//            "&imagePath=C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones" +
//            "&type=学习与办公&restNum=100";
//        Goods response2 = restTemplate.getForObject(url, Goods.class);
//        assertEquals(100, response2.getRestNum());
//        url = "http://localhost:" + port + "/deleteOneGoods?name=微积分";
//        String response3 = restTemplate.getForObject(url, String.class);
//        assertEquals("已减少一件商品", response3);
//
//        url = "http://localhost:" + port + "/getByName?name=微积分";
//        Goods response4 = restTemplate.getForObject(url, Goods.class);
//        assertEquals(99, response4.getRestNum());
//
//        url = "http://localhost:" + port +
//            "/addGoods?name=微积分&description=不是数学分析&price=1&status=true" +
//            "&imagePath=C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones" +
//            "&type=学习与办公&restNum=100&passwordOfGoods=123456";
//        Goods response5 = restTemplate.getForObject(url, Goods.class);
//        assertNull(response5);
//
//    }
//    @Test
//    void TestUserBalance() {
//        // 先把数据库清空
//        clearSchema("");
//        // 存入User数据
//        String url = "http://localhost:" + port + "/add?name=Wang&age=18&id=001&password=123456&balance=1000000";
//        // 得到返回值
//        User response = restTemplate.getForObject(url, User.class);
//        // 数据存入正常
//        assertEquals("Wang", response.getName());
//        assertEquals(18 , response.getAge());
//        assertEquals(1, response.getId());
//        assertEquals("123456", response.getPassword());
//        assertEquals(1000_000, response.getBalance());
//
//        url = "http://localhost:" + port + "/changeBalance?name=Wang&changeNum=500000&isPositive=false";
//        String response2 = restTemplate.getForObject(url, String.class);
//        assert response2.contains("500000");
//
//        url = "http://localhost:" + port + "/getOne?name=Wang";
//        User response3 = restTemplate.getForObject(url, User.class);
//
//        // 可以正常查询到所需数据
//        assertEquals("Wang", response3.getName());
//        assertEquals(18 , response3.getAge());
//        assertEquals(1, response3.getId());
//        assertEquals("123456", response3.getPassword());
//        assertEquals(500000, response3.getBalance());
//
//        // 最后再次清空数据库, 有始有终.
//        clearSchema("");
//    }
//
//    @Test
//    void TestShoppingCart() {
//        // 先把数据库清空
//        clearSchema("");
//        clearSchema("Goods");
//
//        // 存入User数据
//        String url = "http://localhost:" + port + "/add?name=Wang&age=18&id=001&password=123456&balance=1000000";
//        // 得到返回值
//        User response = restTemplate.getForObject(url, User.class);
//        // 数据存入正常
//        assertEquals("Wang", response.getName());
//        assertEquals(18 , response.getAge());
//        assertEquals(1, response.getId());
//        assertEquals("123456", response.getPassword());
//        assertEquals(1000_000, response.getBalance());
//
//        // 创建goods对象
//        url = "http://localhost:" + port +
//            "/addGoods?name=微积分&description=不是数学分析&price=1&status=true" +
//            "&imagePath=C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones&type=学习与办公";
//        // 得到返回值
//        Goods response2 = restTemplate.getForObject(url, Goods.class);
//        assertEquals("微积分", response2.getName());
//        assertEquals("不是数学分析", response2.getDescription());
//        assertEquals(1, response2.getPrice());
//        assert(response2.getStatus());
//        assertEquals("C:\\Users\\DELL\\Downloads\\95版植物大战僵尸\\images\\Tombstones", response2.getImagePath());
//        assertEquals(GoodsType.学习与办公, response2.getType());
//        assertEquals(1, response2.getRestNum());
//
//
//        url = "http://localhost:" + port + "/addToCart?userName=Wang&goodsName=微积分";
//        String response3 = restTemplate.getForObject(url, String.class);
//        assertEquals("添加成功", response3);
//
//
//        url = "http://localhost:" + port + "/showCart?userName=Wang";
//
//        // 打印返回的内容
////        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
////        String responseBody = responseEntity.getBody();
////        System.out.println("Response Body: " + responseBody);
//
//        ParameterizedTypeReference<List<Goods>> responseType = new ParameterizedTypeReference<List<Goods>>() {};
//        ResponseEntity<List<Goods>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
//        List<Goods> response4 = responseEntity.getBody();
//
//        // 可以正常查询到所需数据
//        assert response4 != null;
//        assertEquals(1, response4.size());
//        assertEquals("微积分", response4.get(0).getName());
//
//        // 最后再次清空数据库, 有始有终.
//        clearSchema("");
//    }
//
//}
