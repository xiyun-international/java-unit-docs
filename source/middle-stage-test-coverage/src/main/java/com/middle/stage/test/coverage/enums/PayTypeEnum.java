package com.middle.stage.test.coverage.enums;

/**
 * @author zyq
 */

public enum PayTypeEnum {
    /**
     *
     */
    PAY_TYPE_FUYOU(1, "富友"),
    /**
     *
     */
    PAY_TYPE_KOUBEI(2, "口碑");

    PayTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
