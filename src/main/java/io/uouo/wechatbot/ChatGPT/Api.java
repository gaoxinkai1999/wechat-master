package io.uouo.wechatbot.ChatGPT;

import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public interface Api {
    String token="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";
    @Post(url = "https://api.openai.com/v1/completions",
            contentType = "application/json",
            headers = {"Authorization:Bearer "+token},
            data = {"{\"model\":\"text-davinci-003\",\"prompt\":\"${0}\",\"max_tokens\":500,\"temperature\":0.9}"}
    )
    JSONObject chatGPT(String text);

    @Get("http://81.70.100.130/api/xiaoai.php?msg={0}&n=text")
    String xiaoai(String msg);
}
