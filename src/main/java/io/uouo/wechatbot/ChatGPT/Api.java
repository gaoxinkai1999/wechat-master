package io.uouo.wechatbot.ChatGPT;

import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Post;
import org.springframework.stereotype.Component;

@Component
public interface Api {

    @Post(url = "https://api.openai.com/v1/completions",
            contentType = "application/json",
            headers = {"Authorization:Bearer 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"},
            data = {"{\"model\":\"text-davinci-003\",\"prompt\":\"${0}\",\"max_tokens\":70,\"temperature\":0}",}
    )
    JSONObject chatGPT(String text);

    @Get("http://81.70.100.130/api/xiaoai.php?msg={0}&n=text")
    String xiaoai(String msg);
}
