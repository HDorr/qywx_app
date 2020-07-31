package com.ziwow.scrmapp.wechat.utils.keyword;

import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.service.WeChatMessageProcessingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huangrui
 * @Description: 默认回复
 * @Date: Create in 下午4:15 20-7-31
 */
@Component
public class DefaultKeywordStrategy extends KeywordAbstract {
    @Value("${miniapp.appid}")
    private String miniappAppid;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WeChatMessageProcessingHandler handler;
    @Override
    public boolean getContent(InMessage inMessage, HttpServletResponse response) {
        boolean isInChat=handler.checkChatStatus(inMessage.getFromUserName());
        if (isInChat){
            redisService.set(RedisKeyConstants.getScrmappWechatCustomermsg()+inMessage.getFromUserName(),true,1200L);
            return false;
        }
        this.setMsgsb(new StringBuilder());
        StringBuilder msgsb = this.getMsgsb();
        msgsb.append("您好,小沁在此为您服务,沁园与你一起,健康每一天！\n")
                .append("\n")
                .append("商城购买：\n")
                .append("购买机器,请点击")
                .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/home?goto=classify'>【全部商品】</a>")
                .append("\n")
                .append("\n")
                .append("购买滤芯,请点击")
                .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
                .append("\n")
                .append("\n")
                .append("售后服务：\n")
                .append("机器安装,请点击")
                .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/selectProduct?appointmentType=install'>【预约安装】</a>")
                .append("\n")
                .append("\n")
                .append("机器清洗,请点击")
                .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/selectProduct?appointmentType=clean'>【预约清洗】</a>")
                .append("\n")
                .append("\n")
                .append("机器维修,请点击")
                .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/selectProduct?appointmentType=maintain'>【预约维修】</a>")
                .append("\n")
                .append("\n")
                .append("预约查询,请点击")
                .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/queryProgress'>【进度查询】</a>")
                .append("\n")
                .append("\n")
                .append("其他咨询,请输入文字\"人工客服\"\n");
        return false;
    }
}
