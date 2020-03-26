package com.middle.stage.test.optimization.dao.data;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * 餐别类型实体类
 *
 * @author zhaoyongqiang
 * @date 2020-01-06
 */
@Data
public class DinnerTypeDO {
    /**
     * 餐别主键id
     */
    protected Integer dinnerTypeId;
    /**
     * 餐别名称
     */
    protected String dinnerTypeName;

    /**
     * 餐别描述
     */
    protected String description;

    /**
     * 就餐开始时间(转换成分如:8:30转换为8*60+30=510)
     */
    protected Integer defaultStartTime;

    /**
     * 就餐结束时间(转换成分如:8:30转换为8*60+30=510)
     */
    protected Integer defaultEndTime;

    /**
     * 状态(0:无效1:有效)
     */
    protected Boolean validStatus;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;

    public DinnerTypeDO() {
    }

    public DinnerTypeDO(Integer dinnerTypeId, String dinnerTypeName, Integer defaultStartTime, Integer defaultEndTime) {
        this.dinnerTypeId = dinnerTypeId;
        this.dinnerTypeName = dinnerTypeName;
        this.defaultStartTime = defaultStartTime;
        this.defaultEndTime = defaultEndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DinnerTypeDO that = (DinnerTypeDO) o;
        return Objects.equals(dinnerTypeId, that.dinnerTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dinnerTypeId);
    }
}