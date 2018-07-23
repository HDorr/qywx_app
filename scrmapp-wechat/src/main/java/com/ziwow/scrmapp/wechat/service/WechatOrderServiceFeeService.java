package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.FilterLevel;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrderServiceFee;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.vo.ProductVo;
import java.sql.SQLDataException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xiaohei on 2017/4/5.
 */
public interface WechatOrderServiceFeeService {

    int deleteByPrimaryKey(Long id);

    int insert(WechatOrderServiceFee wechatOrderServiceFee);

    WechatOrderServiceFee selectByPrimaryKey(Long id);

}
