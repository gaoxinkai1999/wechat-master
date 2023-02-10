package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.client.WechatBotClient;
import io.uouo.wechatbot.common.WechatBotCommon;
import io.uouo.wechatbot.service.WechatBotService;
import io.uouo.wechatbot.service.domain.WechatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WechatBotServiceImpl implements WechatBotService, WechatBotCommon {


    /** 注入微信客户端 */
    @Autowired
    private WechatBotClient wechatBotClient;

    /**
     * 描述: 发送文字消息
     *
     * @param wechatMsg 微信消息体
     * @return void
     */
    @Override
    public void wechatCommon(WechatMsg wechatMsg) {
        // 消息类型
        wechatBotClient.sendMsgUtil(wechatMsg);
    }

    /**
     * 描述: 发送文字消息
     *
     * @param wechatMsg 微信消息体
     * @return void
     */
    @Override
    public void sendTextMsg(WechatMsg wechatMsg) {
        wechatMsg.setType(TXT_MSG);
        // 消息类型
        wechatBotClient.sendMsgUtil(wechatMsg);
    }

    /**
     * 描述: 发送图片消息
     *
     * @param wechatMsg 微信消息体
     * @return void
     */
    @Override
    public void sendImgMsg(WechatMsg wechatMsg) {
        wechatMsg.setType(PIC_MSG);
        wechatBotClient.sendMsgUtil(wechatMsg);
    }

    /**
     * 描述: 群组内发送@指定人消息
     *
     * @param wechatMsg
     * @return io.uouo.wechatbot.common.util.AjaxResult
     */
    @Override
    public void sendATMsg(WechatMsg wechatMsg) {
        wechatMsg.setType(AT_MSG);

        wechatBotClient.sendMsgUtil(wechatMsg);
    }


    /**
     * 描述: 发送附件
     *
     * @param wechatMsg
     * @return io.uouo.wechatbot.common.util.AjaxResult
     */
    @Override
    public void sendAnnex(WechatMsg wechatMsg) {
        wechatMsg.setType(ATTATCH_FILE);
        wechatBotClient.sendMsgUtil(wechatMsg);
    }


    /**
     * 描述: 获取微信群组,联系人列表
     *
     * @param
     * @return io.uouo.wechatbot.common.util.AjaxResult
     */
    @Override
    public void getWeChatUserList() {
        WechatMsg wechatMsg = new WechatMsg();
        wechatMsg.setType(USER_LIST);
        wechatMsg.setContent(CONTACT_LIST);
        wechatBotClient.sendMsgUtil(wechatMsg);
    }

    /**
     * 描述:获取指定联系人的详细信息
     *
     * @param wxid 被获取详细信息的人的 微信id
     * @return void
     */
    @Override
    public void getPersonalDetail(String wxid) {
        WechatMsg wechatMsg = new WechatMsg();
        wechatMsg.setType(PERSONAL_DETAIL);
        wechatBotClient.sendMsgUtil(wechatMsg);
    }

}
