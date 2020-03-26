package com.middle.stage.test.optimization.model.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class CanteenCreateForm {

    /**
     * 前端传递的字符串数组
     */
    private String strNewDinnerList;
}
