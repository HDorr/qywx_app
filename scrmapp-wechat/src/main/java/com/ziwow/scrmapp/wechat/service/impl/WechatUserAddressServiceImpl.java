package com.ziwow.scrmapp.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatFansMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatUserAddressMapper;
import com.ziwow.scrmapp.wechat.service.WechatUserAddressService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohei on 2017/3/9.
 */
@Service
public class WechatUserAddressServiceImpl implements WechatUserAddressService {

    private static final Logger logger = LoggerFactory.getLogger(WechatUserAddressServiceImpl.class);

    @Autowired
    private WechatUserAddressMapper userAddressMapper;

    @Autowired
    private WechatFansMapper wechatFansMapper;

    @Value("${miniapp.address.sync}")
    private String syncAddressUrl;

    @Value("${miniapp.address.delete}")
    private String syncAddressDelUrl;

    @Value("${miniapp.address.default}")
    private String syncAddressDefaultUrl;

    @Override
    public List<WechatUserAddress> findUserAddresList(String userId) {
        return userAddressMapper.findListByUserId(userId);
    }

    @Override
    public WechatUserAddress findAddress(Long addressId) {
        return userAddressMapper.findAddress(addressId);
    }

    public Long findAddressByAid(Long aId) {
        return userAddressMapper.findAddressByAid(aId);
    }

    @Override
    public int saveAddress(WechatUserAddress wechatUserAddress) {
        List<WechatUserAddress> userAddresList = findUserAddresList(wechatUserAddress.getUserId());

        //如果用户之前没有地址,强制设一个默认地址
        if (userAddresList.size() == 0) {
            wechatUserAddress.setIsDefault(1);
        }
        //如果新增的地址为默认地址，先清空该用户原来的默认地址
        else if (1 == wechatUserAddress.getIsDefault()) {
            userAddressMapper.cancelDefault(wechatUserAddress.getUserId());
        } else {
            //防止有人恶意传1,2以外的数据
            wechatUserAddress.setIsDefault(2);
        }

        return userAddressMapper.saveAddress(wechatUserAddress);
    }

    @Override
    public Integer updateAddress(WechatUserAddress wechatUserAddress) {
        //如果将需要修改的地址设为默认地址，先清空该用户原来的默认地址
        if (null == wechatUserAddress.getIsDefault() ? false : 1 == wechatUserAddress.getIsDefault()) {
            userAddressMapper.cancelDefault(wechatUserAddress.getUserId());
        } else {
            //防止有人恶意传1,2以外的数据
            wechatUserAddress.setIsDefault(2);
        }
        return userAddressMapper.updateAddress(wechatUserAddress);
    }

    @Override
    public void updateAddressSelective(WechatUserAddress wechatUserAddress) {
        //如果将需要修改的地址设为默认地址，先清空该用户原来的默认地址
        if (null == wechatUserAddress.getIsDefault() ? false : 1 == wechatUserAddress.getIsDefault()) {
            userAddressMapper.cancelDefault(wechatUserAddress.getUserId());
        } else {
            //防止有人恶意传1,2以外的数据
            wechatUserAddress.setIsDefault(2);
        }
        userAddressMapper.updateAddressSelective(wechatUserAddress);
    }

    public Integer deleteAddress(String userId, Long addressId) {
        WechatUserAddress address = findAddress(addressId);

        int count = userAddressMapper.deleteAddress(userId, addressId);
        //如果删除的地址为默认地址，将该用户id最小的地址设为默认地址
        if (count > 0 && 1 == address.getIsDefault()) {
            List<WechatUserAddress> userAddresList = findUserAddresList(userId);
            if (userAddresList != null && userAddresList.size() > 0) {
                WechatUserAddress wechatUserAddress = userAddresList.get(0);
                wechatUserAddress.setIsDefault(1);
                userAddressMapper.setDefault(userId, wechatUserAddress.getId());
            }
        }

        return count;
    }

    @Override
    public void delAddressById(Long addressId) {
        userAddressMapper.delAddressById(addressId);
    }

    @Transactional(rollbackFor = SQLDataException.class)
    @Override
    public boolean updateDefault(String userId, Long addressId) throws SQLDataException {
        userAddressMapper.cancelDefault(userId);
        int count = userAddressMapper.setDefault(userId, addressId);
        if (count > 0) {
            return true;
        } else {
            throw new SQLDataException("数据异常!");
        }
    }

    @Override
    @Async
    public void syncSaveAddressToMiniApp(WechatUserAddress wechatUserAddress) {
        // 异步推送给小程序对接方
        String userId = wechatUserAddress.getUserId();
        WechatFans wechatFans = wechatFansMapper.getWechatFansByUserId(userId);
        if (null == wechatFans || StringUtils.isBlank(wechatFans.getUnionId())) {
            logger.info("保存地址信息同步到小程序失败,unionid不能为空!");
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        params.put("unionId", wechatFans.getUnionId());
        params.put("id", wechatUserAddress.getId());
        params.put("consignee", wechatUserAddress.getContacts());
        params.put("phone", wechatUserAddress.getContactsMobile());
        params.put("zoneId", wechatUserAddress.getAreaId());
        params.put("street", wechatUserAddress.getStreetName());
        params.put("isDefault", wechatUserAddress.getIsDefault() == 1);
        String result = HttpClientUtils.postJson(syncAddressUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                String fAid = o1.getString("data");
                logger.info("保存地址信息同步到小程序成功,aid:{}", fAid);
                WechatUserAddress userAddress = new WechatUserAddress();
                userAddress.setId(wechatUserAddress.getId());
                userAddress.setfAid(fAid);
                updateAddressSelective(userAddress);
            } else {
                logger.info("保存地址信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Override
    @Async
    public void syncUpdateAddressToMiniApp(WechatUserAddress wechatUserAddress) {
        // 异步推送给小程序对接方
        String userId = wechatUserAddress.getUserId();
        WechatFans wechatFans = wechatFansMapper.getWechatFansByUserId(userId);
        if (null == wechatFans || StringUtils.isBlank(wechatFans.getUnionId())) {
            logger.info("保存地址信息同步到小程序失败,unionid不能为空!");
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        params.put("unionId", wechatFans.getUnionId());
        params.put("aId", wechatUserAddress.getfAid());
        params.put("consignee", wechatUserAddress.getContacts());
        params.put("phone", wechatUserAddress.getContactsMobile());
        params.put("zoneId", wechatUserAddress.getAreaId());
        params.put("street", wechatUserAddress.getStreetName());
        params.put("isDefault", wechatUserAddress.getIsDefault() == 1);
        logger.info("修改地址同步到小程序请求参数:{}", JSON.toJSONString(params));
        String result = HttpClientUtils.postJson(syncAddressUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                String fAid = o1.getString("data");
                logger.info("修改地址信息同步到小程序成功,aid:{}", fAid);
                WechatUserAddress userAddress = new WechatUserAddress();
                userAddress.setId(wechatUserAddress.getId());
                userAddress.setfAid(fAid);
                updateAddressSelective(userAddress);
            } else {
                logger.info("修改地址信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Override
    @Async
    public void syncDelAddressToMiniApp(String aId) {
        // 异步推送给小程序对接方
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        params.put("aId", aId.trim());
        String result = HttpClientUtils.postJson(syncAddressDelUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                logger.info("删除地址信息同步到小程序成功,aid:{}", aId);
            } else {
                logger.info("删除地址信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Override
    @Async
    public void syncSetDefaultAddressToMiniApp(String aId) {
        // 异步推送给小程序对接方
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        params.put("aId", aId.trim());
        String result = HttpClientUtils.postJson(syncAddressDefaultUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                logger.info("设置默认地址信息同步到小程序成功,aid:{}", aId);
            } else {
                logger.info("设置默认地址信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Override
    public int insertAndGetId(WechatUserAddress wechantUserAddress) {
        return userAddressMapper.saveAddress(wechantUserAddress);
    }

}