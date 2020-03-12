package com.middle.stage.test.optimization.model.form;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 接收参数
 *
 * @author zhaoyongqiang
 * @date 2020/2/3
 */
@Data
@Slf4j
public class DinnerTypeModel {

    /**
     * 餐别主键id
     */
    protected Integer dinnerTypeId;
    /**
     * 餐别名称
     */
    protected String dinnerTypeName;

}
