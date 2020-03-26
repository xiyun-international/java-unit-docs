package com.middle.stage.test.optimization.model.enums;

/**
 * 日志对象类型枚举
 * Created by cjw on 2017/12/21.
 *
 * @author cjw
 */
public enum ObjectTypeForLogEnum {
    /**
     * 用户
     */
    USER(1, "用户"),
    /**
     * 商家
     */
    MERCHANT(2, "商家"),
    /**
     * 门店
     */
    CANTEEN(3, "门店"),
    /**
     * 组织架构
     */
    ORG(4, "组织架构"),
    /**
     * 校园卡模板
     */
    CARD_TEMPLATE(5, "校园卡模板"),
    /**
     * 校园卡卡类型
     */
    CARD_TYPE(6, "校园卡卡类型"),
    /**
     * 商户用户
     */
    MEMBER(7, "商户用户"),
    /**
     * 商户用户分类
     */
    MEMBER_CATEGORY(8, "商户用户分类"),
    /**
     * 应用
     */
    MODULE_MERCHANT(9, "应用"),
    /**
     * 微信进件
     */
    WECHAT_ENTER(10, "微信进件"),

    /**
     * 餐别
     */
    DINNER(11, "餐别");

    private Integer objectTypeIndex;

    private String objectTypeName;

    ObjectTypeForLogEnum(Integer objectTypeIndex, String objectTypeName) {
        this.objectTypeIndex = objectTypeIndex;
        this.objectTypeName = objectTypeName;
    }

    public Integer getTypeId() {
        return objectTypeIndex;
    }

    @Override
    public String toString() {
        return objectTypeName;
    }
}
