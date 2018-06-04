package com.ziwow.scrmapp.wechat.utils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaohei on 2017/6/14.
 */
public class BarCodeConvert {

    private static final Logger logger = LoggerFactory.getLogger(BarCodeConvert.class);


    /**
     * 将20位条码转换位15位产品条码
     *
     * @param barcode
     * @return
     */
    public static final String convert(String barcode) {
        String productBarCode = StringUtil.EMPTY_STRING;

        if (StringUtils.isEmpty(barcode)) {
            throw new NullPointerException("条码不能为空");
        }

        if (barcode.length() != 20) {
            throw new NumberFormatException("条码长度错误!");
        }

        logger.info("原二维码:" + barcode);

//        int[] subRegular = new int[]{1, 3, 6, 1, 2, 2, 5};//截取规则
//        String[] subStr = new String[subRegular.length];
//        for (int i = 0; i < subRegular.length; i++) {
//            subStr[i] = barcode.substring(0, subRegular[i]);
//            barcode = barcode.substring(subRegular[i]);
//        }
//
//        int[] sort = new int[]{0, 4, 5, 1, 3, 2, 6};
//        for (int i = 0; i < sort.length; i++) {
//            productBarCode += subStr[sort[i]];
//        }
        //logger.info("转换后:" + productBarCode);
        productBarCode = barcode.substring(5);
        logger.info("转换为条码:" + productBarCode);

        return productBarCode;
    }

    public static void main(String[] args) {
//        System.out.println("原二维码:00206608817031821647");
//        System.out.println("条码:" + convert("00206608817031821647"));
        JSONObject scene_id = new JSONObject();
        scene_id.put("scene_id", 10001);
        JSONObject scene = new JSONObject();
        scene.put("scene", scene_id);

        JSONObject object = new JSONObject();
        object.put("action_name", "QR_LIMIT_SCENE");
        object.put("action_info", scene);

        System.out.println(object.toJSONString());
    }
}
