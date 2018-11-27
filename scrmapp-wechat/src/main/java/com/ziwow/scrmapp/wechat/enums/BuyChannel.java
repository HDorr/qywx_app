package com.ziwow.scrmapp.wechat.enums;

import java.util.ArrayList;
import java.util.List;

import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.wechat.vo.EnumVo;

/**
 * Created by xiaohei on 2017/4/6.
 */
public enum BuyChannel {

    ONTAOBAO(SystemConstants.ONLINE, 1, "淘宝"),
    ONJD(SystemConstants.ONLINE, 2, "京东"),
    ONSUNING(SystemConstants.ONLINE, 3, "苏宁易购"),
    ONVIP(SystemConstants.ONLINE, 4, "唯品会"),
    ONGOME(SystemConstants.ONLINE, 5, "国美在线"),
    ONAMAZON(SystemConstants.ONLINE, 6, "亚马逊"),
    ONQINYUAN(SystemConstants.ONLINE, 7, "官方商城"),
    ONYHD(SystemConstants.ONLINE, 8, "一号店"),
    ONQYSC(SystemConstants.ONLINE, 16, "微信商城"),
    ONOTHER(SystemConstants.ONLINE, 9, "其他"),
    OFFGOME(SystemConstants.OFFLINE, 10, "国美电器"),
    OFFSUNING(SystemConstants.OFFLINE, 11, "苏宁"),
    OFFBAIHUO(SystemConstants.OFFLINE, 12, "百货商场"),
    OFFZHUANMAI(SystemConstants.OFFLINE, 13, "专卖店"),
    OFFZHANXIAO(SystemConstants.OFFLINE, 14, "展销会"),
    OFFOTHER(SystemConstants.OFFLINE, 15, "其他");


    // o2o 线上线下
    private int o2o, channelId;
    private String buyChannel;


    public int getO2o() {
        return o2o;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getBuyChannel() {
        return buyChannel;
    }

    BuyChannel(int o2o, int channelId, String buyChannel) {
        this.o2o = o2o;
        this.channelId = channelId;
        this.buyChannel = buyChannel;
    }


    public static List<EnumVo> getChannel(int o2o) {
        List<EnumVo> buyChannelList = new ArrayList<EnumVo>();
        BuyChannel[] buyChannels = BuyChannel.values();
        for (BuyChannel b : buyChannels) {
            if (b.getO2o() == o2o) {
                buyChannelList.add(new EnumVo(b.getChannelId() + "", b.getBuyChannel()));
            }
        }
        return buyChannelList;
    }

    public static String getBuyChannel(int o2o, int channelId) {
        BuyChannel[] buyChannels = BuyChannel.values();
        for (BuyChannel b : buyChannels) {
            if (b.getO2o() == o2o && b.getChannelId() == channelId) {
                return b.getBuyChannel();
            }
        }
        return null;
    }

    public static Integer getBuyChannelId(int o2o, String channelName) {
        BuyChannel[] buyChannels = BuyChannel.values();
        for (BuyChannel b : buyChannels) {
            if (b.getO2o() == o2o && b.getBuyChannel().equals(channelName)) {
                return b.getChannelId();
            }
        }
        return SystemConstants.ONLINE == o2o ? 9 : 15;
    }
}
