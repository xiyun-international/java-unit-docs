package com.middle.stage.test.optimization.model.form;

import com.middle.stage.test.optimization.dao.data.DinnerTypeDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 接收参数
 *
 * @author zhaoyongqiang
 * @date 2020/2/3
 */
@Data
@Slf4j
public class DinnerTypeForm {

    /**
     * 餐别主键id
     */
    protected Integer dinnerTypeId;
    /**
     * 餐别名称
     */
    protected String dinnerTypeName;

    /**
     * 时间以分钟为单位转换
     */
    private static final int TIME_UNIT_MINUTE = 60;

    /**
     * 时间段分割字符
     */
    private static final String TIME_SPLIT_CHARACTER = ":";

    /**
     * 就餐开始时间(字符串时间段 00:00)
     */
    private String strDefaultStartTime;

    /**
     * 就餐结束时间(字符串时间段 00:00)
     */
    private String strDefaultEndTime;

    public DinnerTypeForm() {
    }

    public DinnerTypeForm(Integer dinnerTypeId, String dinnerTypeName, String strDefaultStartTime, String strDefaultEndTime) {
        this.dinnerTypeId = dinnerTypeId;
        this.dinnerTypeName = dinnerTypeName;
        this.strDefaultStartTime = strDefaultStartTime;
        this.strDefaultEndTime = strDefaultEndTime;
    }

    public static DinnerTypeDO strDefaultTimeToInt(DinnerTypeForm dinnerTypeForm) {
        try {
            DinnerTypeDO DinnerTypeDO = new DinnerTypeDO();
            BeanUtils.copyProperties(dinnerTypeForm, DinnerTypeDO);

            String strDefaultStartTime = dinnerTypeForm.getStrDefaultStartTime();
            String[] startTimeArr = strDefaultStartTime.split(TIME_SPLIT_CHARACTER);
            DinnerTypeDO.setDefaultStartTime(Integer.parseInt(startTimeArr[0]) * TIME_UNIT_MINUTE + Integer.parseInt(startTimeArr[1]));

            String strDefaultEndTime = dinnerTypeForm.getStrDefaultEndTime();
            String[] endTimeArr = strDefaultEndTime.split(TIME_SPLIT_CHARACTER);
            DinnerTypeDO.setDefaultEndTime(Integer.parseInt(endTimeArr[0]) * TIME_UNIT_MINUTE + Integer.parseInt(endTimeArr[1]));

            return DinnerTypeDO;
        } catch (Exception e) {
            log.error("DinnerTypeView.strDefaultTimeToInt error DinnerTypeForm=[{}]", dinnerTypeForm, e);
            return null;
        }
    }
}
