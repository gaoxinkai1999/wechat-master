package io.uouo.wechatbot.ChatGPT;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatGpt {
    @Autowired
    Api api;
    public String send(String text){
        try {
            JSONObject jsonObject = api.chatGPT(text);
            String result = jsonObject.getJSONArray("choices").getJSONObject(0).getString("text");
            return result;
        }catch (Exception e){
            return null;
        }

    }
}
