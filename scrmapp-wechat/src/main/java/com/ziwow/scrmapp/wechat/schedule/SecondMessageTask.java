package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

@Component
@JobHandler("secondMessageTask")
public class SecondMessageTask extends AbstractGrantEwCard{

  private static final Logger logger = LoggerFactory.getLogger(SecondMessageTask.class);

  @Autowired
  private GrantEwCardRecordService grantEwCardRecordService;

  @Autowired
  private WechatUserService wechatUserService;

  @Autowired
  private MobileService mobileService;

  @Override
  public ReturnT<String> execute(String s) throws Exception {
    String str = "亲爱的沁粉，您还有一张价值{0}元的延保卡还未领取，号码为：{1}。该卡券两天后失效，请尽快使用。\n" +
            " \n" +
            "使用方式：关注沁园公众号-【我的沁园】-【个人中心】-【延保服务】-【领取卡券】，复制券码并绑定至您的机器，即可延长一年质保（延保开始时间为机器法定质保期届满次日起计算），绑定时请扫描机身条形码，即可识别机器！\n" +
            " \n" +
            "如对操作有疑问，可点击公众号左下角小键盘符号，回复【延保卡】，查看绑定教程。";

    //调整时间为五天前
    Date date = EwCardUtil.getNeedDate(new Date(), -5);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String format = sdf.format(date);

    //第五天前发送延保卡的所有记录
    LinkedList<GrantEwCardRecord> grantEwCardRecordList = grantEwCardRecordService.selectRecordByDate(format);

    //筛选出已发送未注册用户
    Iterator<GrantEwCardRecord> iterator = grantEwCardRecordList.iterator();
    while (iterator.hasNext()){
      WechatUser user = wechatUserService.getUserByMobilePhone(iterator.next().getPhone());
      if (user != null){
        iterator.remove();
      }
    }

    for (GrantEwCardRecord record : grantEwCardRecordList){
      sendMessage(record,str);
    }

    return ReturnT.SUCCESS;
  }

  /**
   *  发送短信方法
   */
  public boolean sendMessage(GrantEwCardRecord record, String msg){
    final String msgContent = MessageFormat.format(msg,record.getType().getPrice(), record.getMask());
    String phone = record.getPhone();
    try {
      //发短信
      mobileService.sendContentByEmay(phone,msgContent, Constant.MARKETING);

    } catch (Exception e) {
      logger.error("发送短信失败，手机号码为:{},错误信息为:{}",phone,e);
      XxlJobLogger.log("发送短信失败，手机号码为:{},错误信息为:{}",phone,e);
      e.printStackTrace();
      return false;
    }

    XxlJobLogger.log("发送短信成功，手机号码为:{}",phone);
    return true;
  }
}
