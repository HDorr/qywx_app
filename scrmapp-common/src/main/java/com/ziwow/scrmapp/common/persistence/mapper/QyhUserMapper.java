package com.ziwow.scrmapp.common.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.common.bean.vo.QyhUserVo;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;

public interface QyhUserMapper extends GenericMapper<QyhUser, Long> {

    int saveQyhUser(QyhUser qyhUser);

    int updateQyhUser(QyhUser qyhUser);

    int deleteQyhUser(@Param("userId") String userId, @Param("corpId") String corpId);

    public void batchInsertWeiXinUser(@Param("weixinUsers") List<QyhUser> weixinUsers);

    public void batchUpdateWeiXinUser(@Param("weixinUsers") List<QyhUser> weixinUsers);

    public void batchDeleteWeiXinUser(@Param("userId") List<String> userId, @Param("corpId") String corpId);

    public QyhUser getQyhUserByUserIdAndCorpId(@Param("userId") String userId, @Param("corpId") String corpId);

    QyhUserVo getQyhUserVoByUserId(String qyhUserId);

    List<QyhUser> getActiveUser();

}