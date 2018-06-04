package com.ziwow.scrmapp.qyh.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;

/**
 * @ClassName: QyhMessageProcessingHandler
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-8-2 下午3:48:22
 *
 */
@Service("qyhMessageProcessingHandler")
public class QyhMessageProcessingHandler {

    private Logger log =Logger.getLogger(this.getClass().getName());


    /**
     * 业务转发组件
     * @Title: manageMessage
     * @param @param requestStr
     * @param @param request
     * @param @param response
     * @param @throws ServletException
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @version 1.0
     * @author Hogen.hu
     */
    public void  manageMessage(String requestStr,HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        String responseStr;
        JSONObject jsonObject = new JSONObject();
        try {
            log.info("企业号返回信息["+requestStr+"]");
            InMessage inMessage  = XmlUtils.xmlToObject(requestStr, InMessage.class);
            if(null !=inMessage){
                log.info("企业号返回信息解密["+JSON.toJSON(inMessage)+"]");
                if ("event".equals(inMessage.getMsgType())) {
                    if("subscribe".equals(inMessage.getEvent())) {
                        // 关注事件
                        log.info("关注openId=["+inMessage.getFromUserName()+"]");
                    }

                		 /*if("subscribe".equals(inMessage.getEvent())) {
                			 // 关注事件
                			 log.info("关注openId=["+inMessage.getFromUserName()+"]");
                		 } else if("unsubscribe".equals(inMessage.getEvent())){
                			 // 取消关注事件
                			 log.info("取消关注openId=["+inMessage.getFromUserName()+"]");
                		 } else if("CLICK".equals(inMessage.getEvent())) {
                			 // 菜单点击事件
                			 log.info("菜单点击事件=["+requestStr+"]");
                    		 String events[] = inMessage.getEventKey().split(":");
                    		 if("redpack".equals(events[0])){
                    			 TWxSubscription subscription = new TWxSubscription();
                    			 subscription.setCreateTime(new Date());
                    			 subscription.setSubToken(inMessage.getToUserName());
                    			 subscription.setSubOpenid(inMessage.getFromUserName());
                    			 tWxSubscriptionService.insertTWxSubscription(subscription);
                    			 //发送客服消息
                    			 tRedpackActivityService.sendCustomerMsg(events[1], inMessage.getFromUserName(),inMessage.getToUserName(),events[2],events[3]);
                    		 }
                		 } else if("user_get_card".equals(inMessage.getEvent())) {

                		 } else if("user_consume_card".equals(inMessage.getEvent())) {

                		 }*/
                }
            }
            //String event =jsonObject.getString("Event");
            // String msgtype =jsonObject.getString("MsgType");
                /* if("CLICK".equals(event) && "event".equals(msgtype)){ //菜单click事件
                     String eventkey =jsonObject.getString("EventKey");
                     if("hytd_001".equals(eventkey)){ // hytd_001 这是好友团队按钮的标志值
                         jsonObject.put("Content", "欢迎使用好友团队菜单click按钮.");
                     }

                 }  */

              /*   responseStr =WeiXinUtil.creatRevertText(jsonObject);//创建XML
                 log.info("responseStr:"+responseStr);
                 OutputStream os =response.getOutputStream();
                 os.write(responseStr.getBytes("UTF-8"));  */
        }   catch (Exception e) {
            System.out.println("requestStr=["+requestStr+"]");
            e.printStackTrace();
        }
    }
}
