package com.ziwow.scrmapp.wechat.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ziwow.scrmapp.common.bean.pojo.ProductFilterGradeParam;
import com.ziwow.scrmapp.common.bean.pojo.ProductParam;
import com.ziwow.scrmapp.common.bean.vo.SecurityVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductFilterGrade;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductItem;
import com.ziwow.scrmapp.common.bean.vo.mall.MallOrderVo;
import com.ziwow.scrmapp.common.bean.vo.mall.OrderItem;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.persistence.entity.FilterChangeRemind;
import com.ziwow.scrmapp.common.persistence.entity.FilterLevel;
import com.ziwow.scrmapp.common.persistence.entity.LevelFilterRelations;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrderServiceFee;
import com.ziwow.scrmapp.common.persistence.mapper.FilterChangeRemindMapper;
import com.ziwow.scrmapp.common.persistence.mapper.FilterLevelMapper;
import com.ziwow.scrmapp.common.persistence.mapper.FilterMapper;
import com.ziwow.scrmapp.common.persistence.mapper.LevelFilterRelationsMapper;
import com.ziwow.scrmapp.common.persistence.mapper.ProductMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrderServiceFeeMapper;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.BeanUtils;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.IPUtil;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.BuyChannel;
import com.ziwow.scrmapp.wechat.enums.SaleType;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.persistence.mapper.ProductModelMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.ProductSeriesMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.ProductTypeMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatFansMapper;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatOrderServiceFeeService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.utils.BarCodeConvert;
import com.ziwow.scrmapp.wechat.vo.ProductVo;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xiaohei on 2017/4/5.
 */
@Service
public class WechatOrderServiceFeeServiceImpl implements WechatOrderServiceFeeService {

    private static final Logger LOG = LoggerFactory
        .getLogger(WechatOrderServiceFeeServiceImpl.class);

    @Autowired
    WechatOrderServiceFeeMapper wechatOrderServiceFeeMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return wechatOrderServiceFeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WechatOrderServiceFee wechatOrderServiceFee) {
        return wechatOrderServiceFeeMapper.insert(wechatOrderServiceFee);
    }

    @Override
    public WechatOrderServiceFee selectByPrimaryKey(Long id) {
        return wechatOrderServiceFeeMapper.selectByPrimaryKey(id);
    }
}