package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.wechat.persistence.entity.TempWechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;

public interface WechatFansService {
    public void saveWechatFans(WechatFans wechatFans);
    public void updateWechatFans(WechatFans wechatFans);
    public void updWechatFansById(WechatFans wechatFans);
    public WechatFans getWechatFans(String openId);
    public WechatFans getWechatFansByOpenId(String openId);
    public WechatFans getFans(String unionId);
    public WechatFans getWechatFansById(Long pkId);
    public UserInfo getUserInfoByOpenId(String openId) throws Exception;
    public WechatFans getWechatFansByUserId(String userId);
    public WechatFansVo getOAuthUserInfo(String code, HttpServletRequest request, HttpServletResponse response);
    public WechatFansVo getFansInfo(String code, HttpServletRequest request, HttpServletResponse response);
    public List<WechatFans> getWechatFansByPage(int page,int size);
    public Integer countWechatFans();
    public List<TempWechatFans> loadTempWechatFansBatch1();
    public List<TempWechatFans> loadTempWechatFansBatch2();
    public List<TempWechatFans> loadTempWechatFansBatch3();
}