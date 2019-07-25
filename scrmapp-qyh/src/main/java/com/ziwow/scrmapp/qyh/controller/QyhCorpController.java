package com.ziwow.scrmapp.qyh.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ziwow.scrmapp.tools.utils.Base64;
import org.apache.commons.io.FileUtils;
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
import com.google.common.collect.Lists;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.qyh.enums.PositionType;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhDepartment;
import com.ziwow.scrmapp.qyh.service.QyhDepartmentService;
import com.ziwow.scrmapp.qyh.service.QyhUserService;
import com.ziwow.scrmapp.qyh.service.QyhUtilService;
import com.ziwow.scrmapp.qyh.vo.QyhApiUser;
import com.ziwow.scrmapp.tools.thirdParty.SignUtil;

/**
 * @author John
 * @ClassName: QyhCorpController
 * @date 2017-03-03 上午10:38:12
 */
@Controller
@RequestMapping("/qyhCorp")
public class QyhCorpController {
    Logger LOG = LoggerFactory.getLogger(QyhCorpController.class);
    @Autowired
    private QyhUtilService qyhUtilService;
    @Autowired
    private QyhUserService qyhUserService;
    @Autowired
    private QyhDepartmentService qyhDepartmentService;
    @Value("${qyh.open.corpid}")
    private String corpId;

    /**
     * 获取企业号accessToken
     *
     * @return
     */
    @RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
    @ResponseBody
    public Result getAccessToken() {
        Result result = new BaseResult();
        String accessToken = qyhUtilService.getQyhAccessToken();
        if (StringUtils.isNotEmpty(accessToken)) {
            result.setData(accessToken);
            result.setReturnCode(Constant.SUCCESS);
        } else {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("获取企业号票据AccessToken失败!");
        }
        return result;
    }

    @RequestMapping(value = "/syncCsmMaster", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getMallMember(@RequestParam("timestamp") String timeStamp,
                                @RequestParam("signture") String signture,
                                @RequestParam("engineerId") String engineerId,
                                @RequestParam("deptName") String deptName,
                                @RequestParam("operate") String operate,
                                @RequestParam(value = "position", required = false) Integer position,
                                @RequestParam(value = "masterMobile", required = false) String masterMobile,
                                @RequestParam(value = "masterName", required = false) String masterName,
                                @RequestParam(value = "gender", required = false) Integer gender,
                                @RequestParam(value = "usable", required = false) Integer usable) {
        LOG.info(
                "同步服务工程师信息CSM系统推送的数据timestamp:[{}],signture:[{}],engineerId:[{}],deptName:[{}],operate:[{}],position:[{}],masterMobile:[{}],masterName:[{}],gender:[{}],usable:[{}]!",
                timeStamp, signture, engineerId, deptName, operate, position, masterMobile, masterName, gender, usable);
        Result result = new BaseResult();
        boolean isLegal = SignUtil.checkSignature(signture, timeStamp, Constant.AUTH_KEY);
        if (!isLegal) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(Constant.ILLEGAL_REQUEST);
            return result;
        }
        try {
            if (StringUtils.isEmpty(engineerId) || StringUtils.isEmpty(deptName) || StringUtils.isEmpty(operate)) {
                LOG.info("服务工程师[{}]基本信息不能为空!", engineerId);
                result.setReturnMsg("服务工程师基本信息不能为空!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            QyhDepartment qyhDepartment = qyhDepartmentService.getDepartmentByName(deptName, corpId);
            if (null == qyhDepartment) {
                result.setReturnMsg("提供的网点名称[" + deptName + "]找不到匹配的组织!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            QyhApiUser qyhApiUser = new QyhApiUser();
            qyhApiUser.setUserid(engineerId);
            if (StringUtils.isNotEmpty(masterMobile)) {
                qyhApiUser.setMobile(masterMobile);
            }
            if (null != gender) {
                qyhApiUser.setGender(gender);
            }
            if (StringUtils.isNotEmpty(masterName)) {
                qyhApiUser.setName(masterName);
            }
            if (null != position) {
                qyhApiUser.setPosition(PositionType.getNameByCode(position));
            }
            if(null != usable && usable == 3){
                qyhApiUser.setEnable(0);
            }
            qyhApiUser.setDepartment(Lists.newArrayList(Integer.valueOf(qyhDepartment.getId() + "")));
            qyhApiUser.setStatus(4);
            String jsonData = JSON.toJSONString(qyhApiUser);
            QyhUser qyhUser = qyhUserService.getQyhUserByUserIdAndCorpId(engineerId, corpId);
            int errorCode = -1 ;
            if (null != qyhUser) {
                if (null != usable && usable == 2) {
                    // 通过企业号接口修改员工
                    errorCode = qyhUserService.updateUser(jsonData);
                    if (0 == errorCode) {
                        qyhUserService.updateQyhUser(qyhApiUser);
                    }
                } else if (null != usable && usable == 1) {
                    //删除企业号员工
                    errorCode = qyhUserService.deleteUser(engineerId);
                    if (0 == errorCode) {
                        qyhUserService.deleteQyhUser(engineerId, corpId);
                    }
                } else if (null != usable && usable == 3) {
                //禁用企业号员工
                errorCode = qyhUserService.updateQyhUser(qyhApiUser);
                LOG.info("禁用企业号用户返回结果:{}",errorCode);
            }
            } else {
                if (null != usable && usable == 2) {
                    // 通过企业号接口创建员工
                    errorCode = qyhUserService.createUser(jsonData);
                    if (0 == errorCode) {
                        qyhUserService.saveQyhUser(qyhApiUser);
                    }
                }
            }
            /*if (operate.equalsIgnoreCase("insert")) {
                if(null != qyhUser) {
					result.setReturnMsg("服务工程师["+ qyhUser.getName() +"]已存在!");
					result.setReturnCode(Constant.FAIL);
					return result;
				}
				// 通过企业号接口创建员工
				qyhUserService.createUser(jsonData);
			} else if (operate.equalsIgnoreCase("update")) {
				// 通过企业号接口修改员工
				qyhUserService.updateUser(jsonData);
			}*/
            LOG.info("服务工程师[{}]同步结果:{}!", engineerId, errorCode);
            if(errorCode == 0) {
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("同步服务工程师信息成功!");
            } else {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("同步服务工程师信息失败，失败码为:" + errorCode);
            }
        } catch (Exception e) {
            LOG.error("同步服务工程师信息失败:", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("error");
        }
        return result;
    }

    @RequestMapping(value = "/syncHistoryMaster", method = RequestMethod.GET)
    @ResponseBody
    public void syncHistoryMaster(HttpServletRequest request) {
        String fileName = request.getSession().getServletContext().getRealPath("") + File.separator + "服务工程师.txt";
        File file = new File(fileName);
        try {
            List<String> list = FileUtils.readLines(file);
            for (String str : list) {
                String[] arry = str.split(",");
                if (arry.length == 6) {
                    String userId = arry[0].trim();
                    String userName = arry[1].trim();
                    String deptName = arry[2].trim();
                    String position = arry[3].trim();
                    String mobilePhone = arry[4].trim();
                    String gender = arry[5].trim();
                    QyhDepartment qyhDepartment = qyhDepartmentService.getDepartmentByName(deptName, corpId);
                    if (null == qyhDepartment) {
                        continue;
                    }
                    QyhApiUser qyhApiUser = new QyhApiUser();
                    qyhApiUser.setUserid(userId);
                    if (StringUtils.isNotEmpty(mobilePhone)) {
                        qyhApiUser.setMobile(mobilePhone);
                    }
                    if (null != gender) {
                        qyhApiUser.setGender(Integer.valueOf(gender));
                    }
                    if (StringUtils.isNotEmpty(userName)) {
                        qyhApiUser.setName(userName);
                    }
                    if (null != position) {
                        qyhApiUser.setPosition(position);
                    }
                    qyhApiUser.setDepartment(Lists.newArrayList(Integer.valueOf(qyhDepartment.getId() + "")));
                    qyhApiUser.setStatus(4);
                    String jsonData = JSON.toJSONString(qyhApiUser);
                    // 通过企业号接口创建员工
                    qyhUserService.createUser(jsonData);
                }
            }
        } catch (IOException e) {
            LOG.error("拉取历史服务工程师失败:", e);
        }
    }

}