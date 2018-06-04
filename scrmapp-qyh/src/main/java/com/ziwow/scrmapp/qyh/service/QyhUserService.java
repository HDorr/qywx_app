package com.ziwow.scrmapp.qyh.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziwow.scrmapp.common.bean.vo.QyhUserVo;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.vo.QyhApiUser;
import com.ziwow.scrmapp.qyh.vo.WxSaaSCallbackInfo;

/**
 * @author hogen
 * @ClassName: QyhUserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016-12-7 下午3:14:43
 */
public interface QyhUserService {
    public void batchInsertWeiXinUser(List<QyhUser> qyhUsers);
    public void batchUpdateWeiXinUser(List<QyhUser> qyhUsers);
    public void batchDeleteWeiXinUser(List<String> userId, String corpId);
    public QyhUser getOAuthQyhUserInfo(String code, HttpServletRequest request, HttpServletResponse response);
    public QyhUser getQyhUserByUserIdAndCorpId(String userId, String corpId);
    QyhUserVo getQyhUserVoByUserId(String userId);
    public int createUser(String jsonData);
    public int updateUser(String jsonData);
    public QyhApiUser getUser(String userId);
    int deleteUser(String userId);
    int saveQyhUser(WxSaaSCallbackInfo wxSaaSCallbackInfo);
    int saveQyhUser(QyhApiUser qyhApiUser);
    int updateQyhUser(WxSaaSCallbackInfo wxSaaSCallbackInfo);
    int updateQyhUser(QyhApiUser qyhApiUser);
    int updateQyhUser(QyhUser qyhUser);
    int deleteQyhUser(String userId, String corpId);
}