package com.ziwow.scrmapp.wechat.enums;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-13 15:04
 */
public class SmsMarketingEmus {
    public enum SmsTypeEnum {
        TODAY(1, "当天短信"), TWODAY(2, "2天内短信"), FOURDAY(3, "4天内短信");
        private int code;
        private String name;

        SmsTypeEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum SmsSendEnum {
        ONE(1, "第一次发送短信"), TWO(2, "第二次发送短信"), THREE(3, "第三次发送短信");
        private int code;
        private String name;

        SmsSendEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}