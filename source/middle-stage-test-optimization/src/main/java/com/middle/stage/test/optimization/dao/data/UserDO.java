package com.middle.stage.test.optimization.dao.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 * Created by lvzhongzhou on 2017/10/28.
 *
 * @author lvzhongzhou
 */
@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = -1161117316650884349L;

    /**
     * 有效状态 无效
     */
    public static final int USER_STATUS_0 = 0;
    /**
     * 有效状态 有效
     */
    public static final int USER_STATUS_1 = 1;

    /**
     * 智云膳-删除状态
     */
    public static final int USER_STATUS_DELETE_ZYS = -1;

    /**
     * 调用满客宝接口使用 无效
     */
    public static final String USER_STATE_INACTIVE = "INACTIVE";
    /**
     * 调用满客宝接口使用 有效
     */
    public static final String USER_STATE_ACTIVE = "ACTIVE";

    /**
     * 锁定状态 正常
     */
    public static final int USER_LOCK_STATUS_N = 0;
    /**
     * 锁定状态 锁定
     */
    public static final int USER_LOCK_STATUS_W = 1;

    /**
     * 用户类型 超管
     */
    public static final int USER_TYPE_1 = 1;
    /**
     * 用户类型 普通用户
     */
    public static final int USER_TYPE_2 = 2;
    /**
     * 用户类型 收银员
     */
    public static final int USER_TYPE_3 = 3;
    /**
     * 用户类型 后台管理员
     */
    public static final int USER_TYPE_4 = 4;
    /**
     * 用户类型 若火用户
     */
    public static final int USER_TYPE_5 = 5;

    /**
     * 用户来源 商家中心
     */
    public static final int USER_SOURCE_MC = 1;
    /**
     * 用户来源 纵横客
     */
    public static final int USER_SOURCE_ZHK = 2;
    /**
     * 用户来源 满客宝
     */
    public static final int USER_SOURCE_MKB = 3;

    /**
     * 不允许退款
     */
    public static final int REFUNDABLE_0 = 0;
    /**
     * 允许退款
     */
    public static final int REFUNDABLE_1 = 1;

    /**
     * 收银终端是否允许查看统计,1：是；0：否
     */
    public static final int STATISTIC_STATUS_YES = 1;
    public static final int STATISTIC_STATUS_NO = 0;

    /**
     * 是门店管理员
     */
    public static final int IS_CONTRACTOR_YES = 1;
    /**
     * 不是门店管理员
     */
    public static final int IS_CONTRACTOR_NO = 0;

    /**
     * 验证业务类型 找回密码
     */
    public static final String USER_VER_TYPE_1 = "1";
    /**
     * 验证业务类型 修改支付宝配置
     */
    public static final String USER_VER_TYPE_2 = "2";
    /**
     * 验证业务类型 修改独立收款门店配置
     */
    public static final String USER_VER_TYPE_3 = "3";
    /**
     * 验证业务类型 修改登陆手机号
     */
    public static final String USER_VER_TYPE_4 = "4";
    /**
     * 验证业务类型 修改密码
     */
    public static final String USER_VER_TYPE_5 = "5";
    /**
     * 验证业务类型 修改手机号的新手机号验证
     */
    public static final String USER_VER_TYPE_6 = "6";
    /**
     * 验证业务类型 商户获取用户详情
     */
    public static final String USER_VER_TYPE_7 = "7";
    /**
     * 验证业务类型 门店修改所属餐饮中心
     */
    public static final String USER_VER_TYPE_8 = "8";

    /**
     * 数据库存储的验证类型 找回密码
     */
    public static final int USER_VER_DB_TYPE_1 = 1;
    /**
     * 数据库存储的验证类型 修改支付宝配置;修改独立收款门店配置
     */
    public static final int USER_VER_DB_TYPE_2 = 2;
    /**
     * 数据库存储的验证类型 修改登陆手机号
     */
    public static final int USER_VER_DB_TYPE_3 = 3;
    /**
     * 数据库存储的验证类型 修改密码
     */
    public static final int USER_VER_DB_TYPE_4 = 4;
    /**
     * 数据库存储的验证类型 修改手机号的新手机号验证
     */
    public static final int USER_VER_DB_TYPE_5 = 5;
    /**
     * 数据库存储的验证类型 商户获取用户详情
     */
    public static final int USER_VER_DB_TYPE_6 = 6;
    /**
     * 数据库存储的验证类型 门店修改所属餐饮中心
     */
    public static final int USER_VER_DB_TYPE_8 = 8;
    /**
     * 数据库存储的验证类型 微信进件短信发送
     */
    public static final int VER_TYPE_WECHAT = 7;

    /**
     * 时间定义 用户验证码超时时间300秒
     */
    public static final int VER_OUT_TIME = 300;
    /**
     * 时间定义 用户验证码超时时间1800秒
     */
    public static final int VER_OUT_TIME_4_ADMIN = 1800;

    /**
     * 收银员组织架构id
     */
    public static final int CASHIER_ORG_ID = 0;

    private Integer userId;
    private Integer merchantId;
    private String userName;
    private String realName;
    private String mcPasswordHash;
    private String identifierCode;
    private String mobile;
    private Integer orgId;
    private Integer userType;
    private Integer lockStatus;
    private Integer status;
    private String verificationCode;
    private Date sendTime;
    private Date editTime;
    private Date createTime;
    private Date updateTime;
    private Integer refundable;
    /**
     * 收银终端是否允许查看统计,1：是；0：否
     */
    private Integer statisticStatus;
    private String newUserName;
    /**
     * 新增表字段用户来源，1商家中心 2纵横客 3满客宝
     */
    private Integer userSource;

    /**
     * 扩展字段
     */
    private List<String> orgInfo;
    private List<String> roleList;
    private List<Integer> roleIdList;
    /**
     * 查询使用,角色对象列表 cjw
     */
    private Integer roleId;
    /**
     * 用户所属组织架构id对应的orgFullCode cjw
     */
    private String orgFullCode;

    /**
     * 收银员列表使用，门店 ID
     */
    private Integer canteenId;

    /**
     * 是否门店管理员
     */
    private Integer isContractor;

    /**
     * 查询门店列表数据使用，扩展字段
     * 当前登录用户的用户类型，用于判断是否超级管理员
     */
    private Integer currentUserType;

    /**
     * 管理费百分比，扩展字段
     */
    private Integer managementFeePercent;

    private String mkbPasswordHash;

    private String zhkPasswordHash;

    /**
     * 存量数据标识 for档口与餐饮中心关联关系 0不是 1是
     */
    private Integer isStock;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class UserSearch extends UserDO {
        private static final long serialVersionUID = 4801305304900289090L;
        /**
         * 商家ids
         */
        private List<Integer> merchantIds;
        /**
         * 用户ids
         */
        private List<Integer> userIds;
    }
}
