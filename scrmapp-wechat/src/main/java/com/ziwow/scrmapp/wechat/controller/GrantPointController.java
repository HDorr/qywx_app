package com.ziwow.scrmapp.wechat.controller;

import com.aliyun.oss.model.Grant;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.service.GrantPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

/**
 * User: wangdong
 * Date: 19-6-11.
 * Time: 下午5:07
 * Description: ${DESCRIPTION}
 */
@Controller
@RequestMapping("/grant")
public class GrantPointController {

  private static final Logger logger = LoggerFactory.getLogger(GrantPointController.class);

  @Autowired
  private GrantPointService grantPointService;

  @RequestMapping(value = "/install",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
  @ResponseBody
  public Result install(@RequestBody PointForm form){
    try {
      grantPointService.grantOrderInstallPoint(form.getUserId(),form.getOrdersCode());
    }catch (Exception e){
      logger.error("积分发放失败 原因",e);
    }
    return BaseResult.Success(true);
  }
  @RequestMapping(value = "/filter",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
  @ResponseBody
  public Result filter(@RequestBody PointForm form){
    try {
      grantPointService.grantOrderFilterPoint(form.getUserId(),form.getOrdersCode());
    }catch (Exception e){
      logger.error("积分发放失败 原因",e);
    }
    return BaseResult.Success(true);
  }


}
