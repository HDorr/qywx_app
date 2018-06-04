package com.ziwow.scrmapp.qyh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.common.bean.vo.QyhUserVo;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.mapper.QyhUserMapper;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.qyh.constants.QyhConstant;
import com.ziwow.scrmapp.qyh.service.QyhUserService;
import com.ziwow.scrmapp.qyh.service.QyhUtilService;
import com.ziwow.scrmapp.qyh.service.QyhWxSaaSService;
import com.ziwow.scrmapp.qyh.vo.QyhApiUser;
import com.ziwow.scrmapp.qyh.vo.WxSaaSCallbackInfo;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author hogen
 * @ClassName: QyhUserServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016-12-7 下午3:15:05
 */
@Service
public class QyhUserServiceImpl extends GenericServiceImpl<QyhUser, Long> implements QyhUserService {
    Logger LOG = LoggerFactory.getLogger(QyhUserServiceImpl.class);
    public static final String COOKIE_PATH = "SCRMAPP_TOKEN_QYH_USERID";

    @Value("${qyh.open.corpid}")
    private String corpId;
    
    @Value("${qyh.mdgl.suitId}")
    private String suitId;
    
    @Autowired
    public QyhUserMapper qyhUserMapper;

    @Resource
    private QyhUtilService qyhUtilService;

    @Resource
    private QyhWxSaaSService qyhWxSaaSService;
    
    @Override
    public void batchInsertWeiXinUser(List<QyhUser> qyhUsers) {
        qyhUserMapper.batchInsertWeiXinUser(qyhUsers);
    }

    @Override
    public void batchUpdateWeiXinUser(List<QyhUser> qyhUsers) {
        qyhUserMapper.batchUpdateWeiXinUser(qyhUsers);
    }

    @Override
    public void batchDeleteWeiXinUser(List<String> userId, String corpId) {
        qyhUserMapper.batchDeleteWeiXinUser(userId, corpId);
    }


    private String getOAuthQyhUserInfo(String code) {
        try {
            String userId = "";
            String jsonStr = HttpKit.get(QyhConstant.GET_USERINFO_URL.replace("ACCESS_TOKEN", qyhWxSaaSService.getCorpAccessToken(suitId, corpId)).replace("CODE", code));
            if (StringUtils.isNotEmpty(jsonStr)) {
                JSONObject obj = JSONObject.parseObject(jsonStr);
                if (obj.getInteger("errcode") != 0 ) {
                    throw new Exception(obj.getString("errmsg"));
                }
                userId = obj.getString("UserId");
                if (StringUtil.isNotBlank(userId)) {
                    return userId;
                }
            }
        } catch (Exception e) {
            LOG.error("企业号员工授权失败:", e);
        }
        return null;
    }

    @Override
    public QyhUser getOAuthQyhUserInfo(String code, HttpServletRequest request, HttpServletResponse response) {
        //先获取客户端的cookie
        String userId = "";
        try {
            String cookie_token = CookieUtil.readCookie(request, response, COOKIE_PATH);
            if (StringUtil.isNotBlank(cookie_token)) {
                userId = new String(Base64.decode(cookie_token));
                LOG.info("解密userId[{}]", userId);
                return qyhUserMapper.getQyhUserByUserIdAndCorpId(userId, corpId);
            } else {
                //授权
                userId = this.getOAuthQyhUserInfo(code);
                LOG.info("获取员工的授权信息:[{}]", userId);
                if (StringUtil.isNotBlank(userId)) {
                    QyhUser qyhUser = qyhUserMapper.getQyhUserByUserIdAndCorpId(userId, corpId);
                    if (qyhUser != null) {
                        //写入cookie
                        CookieUtil.writeCookie(request, response, COOKIE_PATH, Base64.encode(userId.getBytes()), Integer.MAX_VALUE);
                        return qyhUser;
                    }

                }
            }
        } catch (Exception e) {
            LOG.info("获取授权用户信息失败:", e);
        }
        return null;
    }

    @Override
    public QyhUserVo getQyhUserVoByUserId(String userId) {
        return qyhUserMapper.getQyhUserVoByUserId(userId);
    }

    @Override
    public QyhUser getQyhUserByUserIdAndCorpId(String userId, String corpId) {
        return qyhUserMapper.getQyhUserByUserIdAndCorpId(userId, corpId);
    }

    @Override
    public int createUser(String jsonData) {
        int errCode = -1;
        String accessToken = qyhUtilService.getQyhAccessToken();
        // 创建成员url
        String createPersonUrl = QyhConstant.CREATE_USER_URL.replace("ACCESS_TOKEN", accessToken);
        try {
            String jsonStr = HttpKit.post(createPersonUrl, jsonData);
            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
            if (null != jsonObj) {
                errCode = jsonObj.getIntValue("errcode");
                String errMsg = jsonObj.getString("errmsg");
                LOG.info("创建成员操作返回码:[{}],返回信息:[{}]", errCode, errMsg);
            }
        } catch (Exception e) {
            LOG.error("创建成员信息异常:", e);
        }
        return errCode;
    }

    @Override
    public int updateUser(String jsonData) {
        int errCode = -1;
        String accessToken = qyhUtilService.getQyhAccessToken();
        // 更新成员url
        String updPersonUrl = QyhConstant.UPDATA_USER_URL.replace("ACCESS_TOKEN", accessToken);
        try {
            String jsonStr = HttpKit.post(updPersonUrl, jsonData);
            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
            if (null != jsonObj) {
                errCode = jsonObj.getIntValue("errcode");
                String errMsg = jsonObj.getString("errmsg");
                LOG.info("更新企业号员工操作返回码:[{}],返回信息:[{}]", errCode, errMsg);
            }
        } catch (Exception e) {
            LOG.error("更新企业号员工信息异常:", e);
        }
        return errCode;
    }

    @Override
    public QyhApiUser getUser(String userId) {
        QyhApiUser qyhApiUser = null;
        String accessToken = qyhUtilService.getQyhAccessToken();
        // 读取成员url
        String getPersonUrl = QyhConstant.GET_USER_URL.replace("ACCESS_TOKEN", accessToken).replace("USERID", userId);
        try {
            String jsonStr = HttpKit.get(getPersonUrl);
            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
            if (null != jsonObj) {
                int errCode = jsonObj.getIntValue("errcode");
                String errMsg = jsonObj.getString("errmsg");
                LOG.info("获取企业号员工操作返回码:[{}],返回信息:[{}]", errCode, errMsg);
                String name = jsonObj.getString("name");
                String position = jsonObj.getString("position");
                String mobile = jsonObj.getString("mobile");
                Integer gender = jsonObj.getInteger("gender");
                String avatar = jsonObj.getString("avatar");
                Integer status = jsonObj.getInteger("status");
                qyhApiUser = new QyhApiUser();
                qyhApiUser.setUserid(userId);
                qyhApiUser.setName(name);
                qyhApiUser.setPosition(position);
                qyhApiUser.setMobile(mobile);
                qyhApiUser.setGender(gender);
                qyhApiUser.setAvatar(avatar);
                qyhApiUser.setStatus(status);
            }
        } catch (Exception e) {
            LOG.error("获取企业号员工信息异常:", e);
        }
        return qyhApiUser;
    }

    @Override
    public int deleteUser(String userId) {
        int errCode = -1;
        String accessToken = qyhUtilService.getQyhAccessToken();
        // 更新成员url
        String delPersonUrl = QyhConstant.DELETE_USER_URL.replace("ACCESS_TOKEN", accessToken).replace("USERID", userId);
        try {
            String jsonStr = HttpKit.get(delPersonUrl);
            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
            if (null != jsonObj) {
                errCode = jsonObj.getIntValue("errcode");
                String errMsg = jsonObj.getString("errmsg");
                LOG.info("删除企业号员工操作返回码:[{}],返回信息:[{}]", errCode, errMsg);
            }
        } catch (Exception e) {
            LOG.error("删除企业号员工信息异常:", e);
        }
        return errCode;
    }

    @Transactional
    @Override
    public int saveQyhUser(WxSaaSCallbackInfo wxSaaSCallbackInfo) {
        QyhUser qyhUser = new QyhUser();
        qyhUser.setCorpid(wxSaaSCallbackInfo.getAuthCorpId());
        qyhUser.setUserid(wxSaaSCallbackInfo.getUserID());
        qyhUser.setName(wxSaaSCallbackInfo.getName());

        if (null != wxSaaSCallbackInfo.getDepartment()) {
            qyhUser.setDepartments(wxSaaSCallbackInfo.getDepartment());
        }
        if (null != wxSaaSCallbackInfo.getMobile()) {
            qyhUser.setMobile(wxSaaSCallbackInfo.getMobile());
        }

        if (null != wxSaaSCallbackInfo.getPosition()) {
            qyhUser.setPosition(wxSaaSCallbackInfo.getPosition());
        }
        if (null != wxSaaSCallbackInfo.getGender()) {
            qyhUser.setGender(Integer.parseInt(wxSaaSCallbackInfo.getGender()));
        }
        if (null != wxSaaSCallbackInfo.getEmail()) {
            qyhUser.setEmail(wxSaaSCallbackInfo.getEmail());
        }
        if (null != wxSaaSCallbackInfo.getAvatar()) {
            qyhUser.setAvatar(wxSaaSCallbackInfo.getAvatar());
        }
        LOG.info("新增用户，用户信息:[{}]", JSONObject.toJSONString(qyhUser));
        return qyhUserMapper.saveQyhUser(qyhUser);
    }

    @Transactional
    @Override
    public int saveQyhUser(QyhApiUser qyhApiUser) {
        QyhUser qyhUser = new QyhUser();
        qyhUser.setUserid(qyhApiUser.getUserid());
        qyhUser.setCorpid(corpId);
        qyhUser.setName(qyhApiUser.getName());
        if (null != qyhApiUser.getDepartment()) {
            qyhUser.setDepartments(qyhApiUser.getDepartment().get(0) + "");
        }
        if (null != qyhApiUser.getMobile()) {
            qyhUser.setMobile(qyhApiUser.getMobile());
        }

        if (null != qyhApiUser.getPosition()) {
            qyhUser.setPosition(qyhApiUser.getPosition());
        }
        if (null != qyhApiUser.getGender()) {
            qyhUser.setGender(qyhApiUser.getGender());
        }
        if (null != qyhApiUser.getEmail()) {
            qyhUser.setEmail(qyhApiUser.getEmail());
        }
        if (null != qyhApiUser.getAvatar()) {
            qyhUser.setAvatar(qyhApiUser.getAvatar());
        }
        LOG.info("新增用户，用户信息:[{}]", JSONObject.toJSONString(qyhUser));
        return qyhUserMapper.saveQyhUser(qyhUser);
    }

    @Transactional
    @Override
    public int updateQyhUser(WxSaaSCallbackInfo wxSaaSCallbackInfo) {
        QyhUser qyhUser = new QyhUser();
        qyhUser.setCorpid(wxSaaSCallbackInfo.getAuthCorpId());
        qyhUser.setUserid(wxSaaSCallbackInfo.getUserID());
        if (null != wxSaaSCallbackInfo.getName()) {
            qyhUser.setName(wxSaaSCallbackInfo.getName());
        }

        if (null != wxSaaSCallbackInfo.getDepartment()) {
            qyhUser.setDepartments(wxSaaSCallbackInfo.getDepartment());
        }
        if (null != wxSaaSCallbackInfo.getMobile()) {
            qyhUser.setMobile(wxSaaSCallbackInfo.getMobile());
        }

        if (null != wxSaaSCallbackInfo.getPosition()) {
            qyhUser.setPosition(wxSaaSCallbackInfo.getPosition());
        }
        if (null != wxSaaSCallbackInfo.getGender()) {
            qyhUser.setGender(Integer.parseInt(wxSaaSCallbackInfo.getGender()));
        }
        if (null != wxSaaSCallbackInfo.getEmail()) {
            qyhUser.setEmail(wxSaaSCallbackInfo.getEmail());
        }
        if (null != wxSaaSCallbackInfo.getAvatar()) {
            qyhUser.setAvatar(wxSaaSCallbackInfo.getAvatar());
        }
        LOG.info("更新用户，用户信息:[{}]", JSONObject.toJSONString(qyhUser));
        return qyhUserMapper.updateQyhUser(qyhUser);
    }

    @Transactional
    @Override
    public int updateQyhUser(QyhApiUser qyhApiUser) {
        QyhUser qyhUser = new QyhUser();
        qyhUser.setUserid(qyhApiUser.getUserid());
        qyhUser.setCorpid(corpId);
        if (null != qyhApiUser.getName()) {
            qyhUser.setName(qyhApiUser.getName());
        }

        if (null != qyhApiUser.getDepartment()) {
            qyhUser.setDepartments(qyhApiUser.getDepartment().get(0) + "");
        }
        if (null != qyhApiUser.getMobile()) {
            qyhUser.setMobile(qyhApiUser.getMobile());
        }

        if (null != qyhApiUser.getPosition()) {
            qyhUser.setPosition(qyhApiUser.getPosition());
        }
        if (null != qyhApiUser.getGender()) {
            qyhUser.setGender(qyhApiUser.getGender());
        }
        if (null != qyhApiUser.getEmail()) {
            qyhUser.setEmail(qyhApiUser.getEmail());
        }
        if (null != qyhApiUser.getAvatar()) {
            qyhUser.setAvatar(qyhApiUser.getAvatar());
        }
        LOG.info("更新用户，用户信息:[{}]", JSONObject.toJSONString(qyhUser));
        return qyhUserMapper.updateQyhUser(qyhUser);
    }

    @Override
    public int updateQyhUser(QyhUser qyhUser) {
        return qyhUserMapper.updateQyhUser(qyhUser);
    }

    @Transactional
    @Override
    public int deleteQyhUser(String userId, String corpId) {
        LOG.info("删除用户，userId:[{}],corpId:[{}]", userId, corpId);
        return qyhUserMapper.deleteQyhUser(userId, corpId);
    }
}