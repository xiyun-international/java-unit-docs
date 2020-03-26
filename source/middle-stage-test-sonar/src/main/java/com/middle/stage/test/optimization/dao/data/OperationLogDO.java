package com.middle.stage.test.optimization.dao.data;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjw on 2017/12/20.
 *
 * @author cjw
 */
@Data
public class OperationLogDO {
    /**
     * 操作类型
     * 创建   1
     * 编辑   2
     * 删除   3
     * 其它   4
     */
    public static final Integer OPERATION_CREATE = 1;
    public static final Integer OPERATION_EDIT = 2;
    public static final Integer OPERATION_DELETE = 3;
    public static final Integer OPERATION_OTHER = 4;

    /**
     * 操作类型翻译
     */
    public static final Map<Integer, String> OPERATION_TYPE_MAP = new HashMap<Integer, String>() {{
        put(OPERATION_CREATE, "创建");
        put(OPERATION_EDIT, "编辑");
        put(OPERATION_DELETE, "删除");
        put(OPERATION_OTHER, "其他");
    }};

    // 日志表id
    private BigInteger logId;

    /**
     * 操作对象id
     */
    private Long objectId;

    /**
     * 操作对象类型
     */
    private Integer objectType;

    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 操作说明
     */
    private String message;

    /**
     * 操作对象原始数据
     */
    private String operationOldData;

    /**
     * 操作对象当前数据
     */
    private String operationNewData;

    /**
     * 当前操作用户id
     */
    private Integer operationUserId;

    /**
     * 当前操作时间
     */
    private Date operationTime;

    /**
     * 商家id
     */
    private Integer merchantId;
}
