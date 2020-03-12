package com.middle.stage.test.coverage.data;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhaoyongqiang
 * @date 2020-03-07
 */
@Data
public class MarkttingModel {

    private String flag;

    private Integer payType;

    private Integer shopId;

    private BigDecimal payAmount;

}