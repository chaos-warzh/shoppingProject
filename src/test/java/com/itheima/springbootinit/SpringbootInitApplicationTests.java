package com.itheima.springbootinit;

import com.itheima.springbootinit.User.User;
import com.itheima.springbootinit.User.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

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
    void clearDB() {
        String url = "http://localhost:" + port + "/deleteAll";
        restTemplate.getForObject(url, String.class);
    }

    @Test
    void TestSet() {
        // 先把数据库清空
        clearDB();
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

        // 最后再把数据库清空, 有始有终.
        clearDB();
    }


}
