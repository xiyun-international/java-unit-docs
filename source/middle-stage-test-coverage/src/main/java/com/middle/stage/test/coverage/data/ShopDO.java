package com.middle.stage.test.coverage.data;

import lombok.Data;

import java.util.Date;

/**
 * @author zhaoyongqiang
 * @date 2020-03-07
 */
@Data
public class ShopDO {

    /**
     * 主键
     */
    private Integer id;

    private Integer merchantId;

    private Boolean marktingSwitch;


}