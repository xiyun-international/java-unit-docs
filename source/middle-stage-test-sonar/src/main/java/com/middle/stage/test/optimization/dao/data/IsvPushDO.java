package com.middle.stage.test.optimization.dao.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 推送ISV队列实体
 * Created by Katrina on 2017/11/03.
 *
 * @author Katrina
 */
@Data
public class IsvPushDO implements Serializable {
    private static final long serialVersionUID = 4323486958134865222L;

    /**
     * 操作数据类型：
     * 1.门店数据；2.商家管理员数据；3.收银员账号数据；4.商家配置数据；
     * 5.商家校园卡PID；6.用户手机号;7门店校园卡消费配置;8.商户返佣PID；
     * 13.微信支付配置；14.应用订购，停用和启用；15.富友进件电子协议生成、签署、图片上传、通知流程；17.银联支付配置;18餐别信息
     */
    public static final Integer OBJECT_TYPE_SHOP = 1;
    public static final Integer OBJECT_TYPE_ADMIN = 2;
    public static final Integer OBJECT_TYPE_CASHIER = 3;
    public static final Integer OBJECT_TYPE_CONFIG = 4;
    public static final Integer OBJECT_TYPE_CARD_PID = 5;
    public static final Integer OBJECT_TYPE_ADMIN_NAME = 6;
    public static final Integer OBJECT_TYPE_CANTEEN_CARD_CONFIG = 7;
    public static final Integer OBJECT_TYPE_REBATE_PID = 8;
    public static final Integer OBJECT_TYPE_ORG = 10;
    public static final Integer OBJECT_TYPE_MEMBER = 11;
    public static final Integer OBJECT_TYPE_MEMBER_CATEGORY = 12;
    //    public static final int OBJECT_TYPE_WECHAT_PAY_CONFIG = 13;
    //    public static final int OBJECT_TYPE_UNIONPAY_PUSH = 17;
    public static final Integer OBJECT_TYPE_MODULE_MERCHANT_ORDER = 14;
    public static final Integer OBJECT_TYPE_DINNER = 25;
    /**
     * 同步支付配置到网关
     */
    public static final int OBJECT_TYPE_SYNC_PAYCONFIG = 20;
    public static final String OBJECT_DATA_SYNC_PAYCONFIG = "gateway";
    /**
     * 同步支付配置、校园卡配置、微信商户号到开放平台
     */
    public static final int OBJECT_TYPE_SYNC_OPEN_PLATFORM = 21;

    /**
     * 操作数据类型：限制消费会员分类
     */
    public static final int OBJECT_TYPE_MEMBER_TYPE = 22;

    public static final String OBJECT_DATA_SYNC_OPEN_PLATFORM = "openPlatform";

    /**
     * 太空桥web服务端接口-更新门店下所有设备的支付通道
     */
    public static final int OBJECT_TYPE_SYNC_SPACE_BRIDGE = 24;
    public static final String OBJECT_DATA_SYNC_SPACE_BRIDGE = "spaceBridge";
    /**
     * 网关同步门店银联直连支付配置
     */
    public static final int OBJECT_TYPE_SYNC_SHOP_UNION_PAYCONFIG = 23;
    public static final String OBJECT_DATA_SYNC_SHOP_UNION_PAYCONFIG = "gateway_shop_unionpay";

    /**
     * 操作类型：1 create；2 update；3 门店收银员绑定；4 门店收银员解绑；5.变更状态;6:删除操作;7:导入操作;8:创建餐别；9：变更餐别
     */
    public static final Integer OPERATE_TYPE_CREATE = 1;
    public static final Integer OPERATE_TYPE_UPDATE = 2;
    public static final Integer OPERATE_TYPE_BIND = 3;
    public static final Integer OPERATE_TYPE_UNBIND = 4;
    public static final Integer OPERATE_TYPE_CHANGE_STATE = 5;
    public static final Integer OPERATE_TYPE_DELETE = 6;
    public static final Integer OPERATE_TYPE_IMPORT = 7;

    /**
     * 其它推送消息推送地址，默认spaceId = 0;
     */
    public static final int SPACE_FOR_OTHER = 0;
    /**
     * 处理状态
     */
    /**
     * 待处理，等待读取处理
     */
    public static final int PROCESS_STATUS_DEFAULT = 0;
    /**
     * 处理成功，不再读取
     */
    public static final int PROCESS_STATUS_SUCCESS = 1;
    /**
     * 处理失败, 继续处理
     */
    public static final int PROCESS_STATUS_FAIL = 2;
    /**
     * 无效的推送消息，不再处理
     */
    public static final int PROCESS_STATUS_INVALID = 3;

    /**
     * 读取锁定：1是
     */
    public static final int READ_LOCK_TRUE = 1;
    /**
     * 读取锁定：0否
     */
    public static final int READ_LOCK_FALSE = 0;

    private Integer id;
    private String objectId;
    private Integer operateType;
    private Integer objectType;
    private Integer spaceId;
    private Integer processStatus;
    private String objectData;

    private Integer execCount;
    private Integer nextTime;

    private Integer createTime;
    private Integer updateTime;

    private String spaceDomain;

    private Integer readLock;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class IsvPushSearch extends IsvPushDO {
        List<Integer> processStatusAr;

        List<Integer> ids;
    }
}
