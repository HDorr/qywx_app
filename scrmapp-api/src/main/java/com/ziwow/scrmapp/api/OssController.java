package com.ziwow.scrmapp.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.tools.oss.OSSUtil;

@RequestMapping("/scrmapp/picture")
@Controller
public class OssController extends BaseController {
	@RequestMapping(value = "/upload/{fileExten}", method = RequestMethod.POST)
	@ResponseBody
	public Result upload(@RequestBody byte[] fileBytes, @PathVariable String fileExten) {
		Result result = new BaseResult();
		if (fileBytes.length > 0) {
			InputStream input = new ByteArrayInputStream(fileBytes);
			String ossFileUrl = OSSUtil.uploadFile(input, fileExten);
			result.setData(ossFileUrl);
			result.setReturnCode(Constant.SUCCESS);
		}
		return  result;
	}
	
	@RequestMapping(value = "/delete/{fileUrl}", method = RequestMethod.POST)
	public void delete(@PathVariable String fileUrl) {
		OSSUtil.deleteFile(fileUrl);
	}
	
	@RequestMapping(value = "/update/{fileUrl}", method = RequestMethod.POST)
	public void update(@RequestBody byte[] fileBytes, @PathVariable String fileUrl) {
		if (fileBytes.length > 0) {
			InputStream input = new ByteArrayInputStream(fileBytes);			
			OSSUtil.updateFile(input, fileUrl);
		}
	}
}