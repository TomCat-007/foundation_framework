package com.example.web.enums;

/**
 * 短信发送状态
 *
 * @author zhanghuiyuan
 * @description 短信发送状态
 * @date 2023/3/3 13:33
 */
public enum SmsSendStatusEnum {
    /**
     * 待发送
     **/
    PREPARE((byte) 1),
    /**
     * 发送中
     **/
    SENDING((byte) 2),
    /**
     * 发送成功
     **/
    SUCCESS((byte) 3),
    /**
     * 发送失败
     **/
    FAILED((byte) 4);

    private byte val;

    private SmsSendStatusEnum(byte awardStatus) {
        this.val = awardStatus;
    }

    public byte val() {
        return this.val;
    }
}
