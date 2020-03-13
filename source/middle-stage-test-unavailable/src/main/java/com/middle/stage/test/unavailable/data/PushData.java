package com.middle.stage.test.unavailable.data;

import lombok.Data;

/**
 * @author zhaoyongqiang
 * @date 2020-03-07
 */
@Data
public class PushData {

    /**
     * 推送的数据id
     */
    private Integer objectId;

    /**
     * 惭怍类型
     */
    private Integer operateType;

    /**
     * 数据类型
     */
    private Integer objectType;

}