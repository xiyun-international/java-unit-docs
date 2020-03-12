package com.middle.stage.test.optimization.dao.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 门店餐别关联实体类
 *
 * @author zhaoyongqiang
 * @date 2020-01-06
 */
@Data
public class DinnerDO implements Serializable {

    private static final long serialVersionUID = 1034763672381229237L;
    /**
     * 自增主键id
     */
    private Integer dinnerId;

    /**
     * 餐别ID
     */
    private Integer dinnerTypeId;

    /**
     * 门店ID
     */
    private Integer canteenId;

    /**
     * 就餐开始时间(转换成分如:8:30转换为8*60+30=510)
     */
    private Integer startTime;

    /**
     * 就餐结束时间(转换成分如:8:30转换为8*60+30=510)
     */
    private Integer endTime;

    /**
     * 状态(0:无效1:有效)
     */
    private Byte deleteStatus;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建人id
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateUserName;

    /**
     * 更新人id
     */
    private Integer updateUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class DinnerSearch extends DinnerDO {

        private Integer excludeDinnerTypeId;
    }
}