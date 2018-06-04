package com.ziwow.scrmapp.qyh.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.service.*;
import com.ziwow.scrmapp.qyh.vo.QyhApiUser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.qyh.constants.QyhConstant;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhSuite;
import com.ziwow.scrmapp.qyh.vo.PermanentCodeVo;
import com.ziwow.scrmapp.qyh.vo.WxSaaSCallbackInfo;
import com.ziwow.scrmapp.qyh.weixin.decode.WXBizMsgCrypt;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;

/**
 * @author hogen
 * @ClassName: QyhOpenController
 * @date 2016-11-29 上午10:38:12
 */
@Controller
@RequestMapping("/wxQyhAuth")
public class QyhOpenController {

    Logger LOG = LoggerFactory.getLogger(QyhOpenController.class);

    @Autowired
    private QyhWxSaaSService qyhWxSaaSService;

    @Autowired
    private QyhSuiteService qyhSuiteService;

    @Autowired
    private QyhAuthCorpService qyhAuthCorpService;

    @Autowired
    private QyhAuthAgentService qyhAuthAgentService;

    @Autowired
    private QyhDepartmentService qyhDepartmentService;

    @Autowired
    private QyhUserService qyhUserService;

    @Value("${qyh.open.token}")
    private String sToken;
    @Value("${qyh.open.encodingaeskey}")
    private String sEncodingAESKey;

    @Value("${qyh.open.corpid}")
    private String corpid;


    /**
     * @param @param  suiteId
     * @param @param  corpId
     * @param @return 设定文件
     * @return Object    返回类型
     * @Title: contactSync
     * @Description: 手动同步通讯录
     * @version 1.0
     * @author Hogen.hu
     */
    @RequestMapping(value = "/contactSync", method = RequestMethod.POST)
    @ResponseBody
    public Object contactSync(@RequestParam("suiteId") String suiteId, @RequestParam("corpId") String corpId) {
        LOG.info("手动同步通讯录,suiteId=[{}],corpId=[{}]", suiteId, corpId);
        Result result = new BaseResult();
        try {
            qyhDepartmentService.contactSync(suiteId, corpId);
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    /**
     * @param @param  suiteId
     * @param @param  corpId
     * @param @return 设定文件
     * @return Object    返回类型
     * @Title: getCorpAccessToken
     * @Description: 获取CorpAccessToken
     * @version 1.0
     * @author Hogen.hu
     */
    @RequestMapping(value = "/getCorpAccessToken", method = RequestMethod.GET)
    @ResponseBody
    public Object getCorpAccessToken(@RequestParam("suiteId") String suiteId, @RequestParam("corpId") String corpId) {
        LOG.info("获取CorpAccessToken，参数,suiteId=[{}],corpId=[{}]", suiteId, corpId);
        Result result = new BaseResult();
        String corpAccessToken = "";
        try {
            corpAccessToken = qyhWxSaaSService.getCorpAccessToken(suiteId, corpId);
            result.setData(corpAccessToken);
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("获取失败");
        }

        return result;
    }

    /**
     * @param @param  suiteid
     * @param @return 设定文件
     * @return Object    返回类型
     * @throws UnsupportedEncodingException
     * @Title: getQyhWXAuthUrl
     * @Description: 从应用提供商网站发起授权, 获取URL
     * @version 1.0
     * @author Hogen.hu
     */
    @RequestMapping(value = "/getQyhWXAuthUrl", method = RequestMethod.GET)
    @ResponseBody
    public Object getQyhWXAuthUrl(@RequestParam("suiteid") String suiteid, @RequestParam("appids") String appids) throws UnsupportedEncodingException {
        LOG.info("从应用提供商网站发起授权,参数suiteid=[{}],appids[{}]", suiteid, appids);
        Result result = new BaseResult();
        QyhSuite info = qyhSuiteService.selectByPrimaryKey(suiteid);
        String suiteSecret = "";
        if (info != null) {
            suiteSecret = info.getSuitesecret();
            if (StringUtil.isBlank(suiteSecret)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("套件信息不完整");
                return result;
            }
            //获取预授权码
            String pre_auth_code = qyhWxSaaSService.getPreAuthCode(suiteid);
            qyhWxSaaSService.setSessionInfo(suiteid, pre_auth_code, appids, 0);//0 正式授权， 1 测试授权， 默认值为0
            String url = QyhConstant.AUTH_URL.replace("$suite_id$", suiteid).replace("$pre_auth_code$", pre_auth_code);
            LOG.info("从应用提供商网站发起授权,url=[{}]", url);
            result.setData(url);
            result.setReturnCode(Constant.SUCCESS);
        } else {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("无此套件授权信息");
        }
        return result;
    }

    /**
     * @param @param  suiteId
     * @param @param  authCode
     * @param @return 设定文件
     * @return Object    返回类型
     * @Title: qyWXSaasAuthCallback
     * @Description: 企业号第三方应用授权回调
     * @version 1.0
     * @author Hogen.hu
     */
    @RequestMapping(value = "/qyWXSaasAuthCallback", method = RequestMethod.POST)
    @ResponseBody
    public Object qyWXSaasAuthCallback(@RequestParam("suiteid") String suiteId, @RequestParam("authCode") String authCode) {
        LOG.info(" 企业号第三方应用授权回调,suiteId=[{}],authCode=[{}]", suiteId, authCode);
        Result result = new BaseResult();
        PermanentCodeVo vo = qyhWxSaaSService.getPermanentCode(suiteId, authCode);
        LOG.info("企业号第三方应用授权回调返回结果:[{}]", JSON.toJSON(vo));
        if (vo != null) {
            try {
                qyhAuthCorpService.qyWXSaasAuthCallbackSave(vo, suiteId);
                result.setReturnCode(Constant.SUCCESS);
                result.setData(vo.getAuth_corp_info().getCorpid());
            } catch (Exception e) {
                result.setReturnCode(Constant.FAIL);
            }
        } else {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("授权回调失败");
        }
        return result;
    }


    /**
     * @param @param req
     * @param @param res    设定文件
     * @return void    返回类型
     * @Title: qyhWXSaaSCallback
     * @Description: 微信企业号第三方应用回调协议
     * @version 1.0
     * @author Hogen.hu
     */
    @RequestMapping(value = "/qyhWXSaaSCallback", method = {RequestMethod.GET, RequestMethod.POST})
    public void qyhWXSaaSCallback(HttpServletRequest req, ServletResponse res) {
        //获取参数
        String msgSignature = req.getParameter("msg_signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");    //创建套件时验证回调url时传入
        String result = "";
        LOG.info("qyhWXSaaSCallback get param msgSignature:[{}],timestamp:[{}],nonce:[{}],echostr:[{}]", msgSignature, timestamp,
                nonce, echostr);
        try {
            if (StringUtil.isNotBlank(echostr)) {    //=======验证回调url有效性=======
                WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(sToken, sEncodingAESKey, corpid);//注意是CORP_ID
                result = wxBizMsgCrypt.VerifyURL(msgSignature, timestamp, nonce, echostr);
                LOG.info("系统事件接收URL接入:[{}]", result);
                res.getWriter().write(result);    //对echostr参数解密并原样返回echostr明文(不能加引号，不能带bom头，不能带换行符)
            } else {
                InputStream inputStream = null;
                inputStream = req.getInputStream();
                String postData = IOUtils.toString(inputStream, "utf-8");
                //LOG.info("微信企业号第三方应用回调协议,返回数据:[{}]",postData);
                //需要取出XML的ToUserName，也就是suite_id
                WxSaaSCallbackInfo wcis = XmlUtils.xmlToObject(postData, WxSaaSCallbackInfo.class);
                String suiteId = wcis.getToUserName();
                //LOG.info("微信企业号第三方应用回调协议,返回数据suiteId:[{}]",suiteId);
                WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(sToken, sEncodingAESKey, suiteId);//注意是SUITE_ID
                result = wxBizMsgCrypt.DecryptMsg(msgSignature, timestamp, nonce, postData);
                LOG.info("微信企业号第三方应用回调协议,返回数据解密:[{}]", result);
                WxSaaSCallbackInfo wci = XmlUtils.xmlToObject(result, WxSaaSCallbackInfo.class);

                if ("suite_ticket".equals(wci.getInfoType())) {//推送suite_ticket协议
                    LOG.info("推送suite_ticket协议,suiteId:[{}],ticket:[{}]", suiteId, wci.getSuiteTicket());
                    QyhSuite record = new QyhSuite();
                    record.setSuiteid(suiteId);
                    record.setSuiteticket(wci.getSuiteTicket());
                    record.setCreatetime(new Date());
                    qyhSuiteService.insertOrUpdateSuite(record);
                } else if ("change_auth".equals(wci.getInfoType())) {//变更授权的通知
                    LOG.info("变更授权的通知,suiteId:[{}],change_auth:[{}]", suiteId, wci.getAuthCorpId());
                    qyhAuthAgentService.changeAuth(suiteId, wci.getAuthCorpId());
                } else if ("cancel_auth".equals(wci.getInfoType())) {//取消授权的通知
                    LOG.info("取消授权的通知,suiteId:[{}],cancel_auth:[{}]", suiteId, wci.getAuthCorpId());
                    qyhAuthCorpService.cancelAuth(suiteId, wci.getAuthCorpId());
                } else if ("create_auth".equals(wci.getInfoType())) {//授权成功推送auth_code事件
                    LOG.info("创建授权的通知,suiteId:[{}],create_auth:[{}]", suiteId, wci.getAuthCode());
                } else if ("contact_sync".equals(wci.getInfoType())) {//通讯录变更通知
                    LOG.info("通讯录变更通知,suiteId:[{}],contact_sync:[{}]", suiteId, wci.getSeq());
                    qyhDepartmentService.contactSync(suiteId, wci.getAuthCorpId());
                }
                // 关注
                if(null != wci.getEvent() && wci.getEvent().equals("change_contact") && wci.getChangeType().equals("update_user")) {
                    String avatar = wci.getAvatar();
                    String status = wci.getStatus();
                    if(StringUtils.isNotEmpty(avatar) || (StringUtils.isNotEmpty(status) && status.equals("1"))){
                        String userId = wci.getUserID();
                        LOG.info("用户[{}]关注企业号", userId);
                        QyhUser qyhUser = new QyhUser();
                        qyhUser.setUserid(userId);
                        qyhUser.setCorpid(corpid);
                        if(StringUtils.isNotEmpty(avatar)) {
                            qyhUser.setAvatar(avatar);
                        }
                        qyhUser.setStatus(1);
                        qyhUserService.updateQyhUser(qyhUser);
                    }
                }
                //  取消关注
                if(StringUtils.isNotEmpty(wci.getAgentID()) && wci.getAgentID().equals("1")) {
                    String userId = wci.getFromUserName();
                    if(wci.getEvent().equals("unsubscribe")) {
                        LOG.info("用户[{}]取消企业号关注", userId);
                        QyhUser qyhUser = new QyhUser();
                        qyhUser.setUserid(userId);
                        qyhUser.setCorpid(corpid);
                        qyhUser.setStatus(4);
                        qyhUserService.updateQyhUser(qyhUser);
                    }
                }
                res.getWriter().write("success");    //应用提供商在收到推送消息后需要返回字符串success,
            }
        } catch (Exception e) {
            LOG.error("企业号三方授权callback异常:", e);
        }
    }
}