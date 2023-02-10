package io.uouo.wechatbot;

import com.alibaba.fastjson.JSONObject;
import io.uouo.wechatbot.ChatGPT.Api;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class WechatBotApplicationTests {
    @Autowired
    Api api;

    @Test
    void contextLoads() {


    }

}
