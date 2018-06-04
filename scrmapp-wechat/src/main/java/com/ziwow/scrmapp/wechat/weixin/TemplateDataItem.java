package com.ziwow.scrmapp.wechat.weixin;

import java.util.HashMap;
 public class TemplateDataItem extends HashMap<String, Item>{

        /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

		public Item getItemInstance(String value) {
            return new Item(value);
        }

        public Item getItemInstance(String value, String color) {
            return new Item(value, color);
        }

        public TemplateDataItem() {
        }

        public TemplateDataItem addItem(String key, String value) {
            this.put(key, new Item(value));
            return this;
        }

        public TemplateDataItem addItem(String key, String value, String color) {
            this.put(key, new Item(value, color));
            return this;
        }
    }