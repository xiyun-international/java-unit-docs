package com.middle.stage.test.optimization.model.enums;

/**
 * 常用 是否 枚举 1是，0否
 *
 * @author cjw
 * @date 2019-07-30
 */
public enum CommonIsEnum {
    /**
     * 常用表示是、已开通状态等
     */
    TRUE((byte) 1, "是"),
    /**
     * 常用表示否、未开通状态等
     */
    FALSE((byte) 0, "否");
    private Byte val;

    private String name;

    CommonIsEnum(Byte val, String name) {
        this.val = val;
        this.name = name;
    }

    public Byte getVal() {
        return this.val;
    }

    public String getName() {
        return this.name;
    }

    public Boolean equals(Byte val) {
        return this.val.equals(val);
    }

    public Boolean equals(Integer val) {
        return this.val.equals(val.byteValue());
    }
}
