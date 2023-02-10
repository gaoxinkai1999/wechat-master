package io.uouo.wechatbot.client;

import com.alibaba.fastjson.JSONObject;
import io.uouo.wechatbot.ChatGPT.Api;
import io.uouo.wechatbot.ChatGPT.ChatGpt;
import io.uouo.wechatbot.common.WechatBotCommon;
import io.uouo.wechatbot.service.domain.WechatMsg;
import io.uouo.wechatbot.service.impl.WechatBotServiceImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * websocket机器人客户端
 *
 */
public class WechatBotClient extends WebSocketClient implements WechatBotCommon {
    @Autowired
    Api api;
    @Autowired
    ChatGpt chatGpt;
    @Autowired
    WechatBotServiceImpl wechat;

    /**
     * 描述: 构造方法创建 WechatBotClient对象
     *
     * @param url WebSocket链接地址
     * @return
     */
    public WechatBotClient(String url) throws URISyntaxException {
        super(new URI(url));
    }

    /**
     * 描述: 在websocket连接开启时调用
     *
     * @param serverHandshake
     * @return void
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.err.println("已发送尝试连接到微信客户端请求");
    }

    /**
     * 描述: 方法在接收到消息时调用
     *
     * @param s
     * @return void
     */
    @Override
    public void onMessage(String s) {
        //过滤心跳包消息
        JSONObject message = JSONObject.parseObject(s);
        if (message.getIntValue("type") == HEART_BEAT) {
            return;
        } else if (message.getIntValue("type") == RECV_TXT_MSG) {
//                JSONObject content= api.chatGPT(message.getString("content"));
//                String string = content.getJSONArray("choices").getJSONObject(0).getString("text");
            WechatMsg wechatMsg = new WechatMsg();
//            被@的情况
            if (message.getString("content").startsWith("@abc") && message.getString("wxid").endsWith("@chatroom")) {
                System.out.println(message.getString("content").split("@abc")[1].trim());
//                String string = api.xiaoai(message.getString("content").split("@abc")[1].trim());
                String text=message.getString("content").split("@abc")[1].trim();
                String result = chatGpt.send(text);
                if (!result.isEmpty()) {
                    wechatMsg.setRoomid(message.getString("wxid"));
                    wechatMsg.setContent(result);
                    wechatMsg.setNickname("@");
                    wechat.sendATMsg(wechatMsg);
                }
            //私聊情况
            } else if (!message.getString("wxid").endsWith("@chatroom")) {
//                String string = api.xiaoai(message.getString("content"));
                String text=message.getString("content");
                String result = chatGpt.send(text);
                if (!result.isEmpty()) {
                    wechatMsg.setWxid(message.getString("wxid"));
                    wechatMsg.setContent(result);
                    wechat.sendTextMsg(wechatMsg);
                }

            }


        }
        System.out.println("微信中收到了消息:" + s);


    }

    /**
     * 描述: 方法在连接断开时调用
     *
     * @Date 2021-3-16
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("已断开连接... ");
    }

    /**
     * 描述: 方法在连接出错时调用
     *
     * @param e
     * @return void
     */
    @Override
    public void onError(Exception e) {
        System.err.println("通信连接出现异常:" + e.getMessage());

    }

    /**
     * 描述: 发送消息工具 (其实就是把几行常用代码提取出来 )
     *
     * @param wechatMsg 消息体
     * @return void
     */
    public void sendMsgUtil(WechatMsg wechatMsg) {
        if (!StringUtils.hasText(wechatMsg.getExt())) {
            wechatMsg.setExt(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getNickname())) {
            wechatMsg.setNickname(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getRoomid())) {
            wechatMsg.setRoomid(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getContent())) {
            wechatMsg.setContent(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getWxid())) {
            wechatMsg.setWxid(NULL_MSG);
        }


        // 消息Id
        wechatMsg.setId(String.valueOf(System.currentTimeMillis()));
        // 发送消息
        String string = JSONObject.toJSONString(wechatMsg);
//        System.err.println(":" + string);
        send(JSONObject.toJSONString(wechatMsg));
    }
}
