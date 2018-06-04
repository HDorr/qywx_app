package com.ziwow.scrmapp.wechat.weixin;

import java.io.Serializable;

public class Item implements Serializable{
        /**
		 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
		 */
		private static final long serialVersionUID = 1L;
		private String value;
        private String color = "#000000";

        public Item(String value) {
            this.value = value;
        }

        public Item(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getColor() {
            return color;
        }
    }