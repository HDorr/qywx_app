package com.ziwow.scrmapp.wechat.controller.oss;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.exception.BizException;
import com.ziwow.scrmapp.common.oss.ALiYunOssService;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.result.ResultHelper;
import com.ziwow.scrmapp.common.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/oss")
@RestController
public class ALiYunOssController {

  private final ALiYunOssService aLiYunOssService;

  @Autowired
  public ALiYunOssController(ALiYunOssService aLiYunOssService) {
    this.aLiYunOssService = aLiYunOssService;
  }

  /**
   * 文件上传
   *
   * @param file {@link MultipartFile}
   * @return {@link Result}
   */
  @RequestMapping("/upload")
  public Result uploadFile(
      @RequestParam MultipartFile file,
      @RequestParam String signature,
      @RequestParam String timeStamp) {
    boolean checkSignature = SignUtil.checkSignature(signature, timeStamp, Constant.AUTH_KEY);
    if (!checkSignature) {
      throw new BizException("当前用户没有权限访问");
    }
    try {
      String fileUrl =
          aLiYunOssService.uploadInputStream(
              file.getInputStream(), UUID.randomUUID().toString() + ".jpg");
      return ResultHelper.success(fileUrl);
    } catch (IOException e) {
      throw new BizException("文件上传失败");
    }
  }
}
