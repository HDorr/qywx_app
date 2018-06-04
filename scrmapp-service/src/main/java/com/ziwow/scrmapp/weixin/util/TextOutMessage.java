package com.ziwow.scrmapp.weixin.util;

import java.io.Serializable;

public class TextOutMessage extends OutMessage implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XStreamCDATA
    private String MsgType = "text";
    // 文本消息
    @XStreamCDATA
    private String Content;

    public TextOutMessage() {
    }

    public TextOutMessage(String content) {
        Content = content;
    }

    public String getMsgType() {
        return MsgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
