package com.middle.stage.test.optimization.model.view;

import com.middle.stage.test.optimization.dao.data.DinnerTypeDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 餐别前端展示类
 *
 * @author zhaoyongqiang
 * @date 2020/1/6
 */
@Data
@Slf4j
public class DinnerTypeView implements Serializable {

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
     * 时间界限
     */
    private static final int TIME_LIMIT = 10;

    /**
     * int分钟转行时间段补充
     */
    private static final String SUPPLEMENT_TIME = "0";

    /**
     * 就餐开始时间(字符串时间段 00:00)
     */
    private String strDefaultStartTime;

    /**
     * 就餐结束时间(字符串时间段 00:00)
     */
    private String strDefaultEndTime;

    public static DinnerTypeView intDefaultTimeToStr(DinnerTypeDO dinnerTypeDO) {
        try {
            DinnerTypeView dinnerTypeView = new DinnerTypeView();
            BeanUtils.copyProperties(dinnerTypeDO, dinnerTypeView);
            Integer defaultStartTime = dinnerTypeDO.getDefaultStartTime();
            Integer defaultEndTime = dinnerTypeDO.getDefaultEndTime();

            Integer startHour = defaultStartTime / TIME_UNIT_MINUTE;
            Integer startMinute = defaultStartTime % TIME_UNIT_MINUTE;
            Integer endHour = defaultEndTime / TIME_UNIT_MINUTE;
            Integer endMinute = defaultEndTime % TIME_UNIT_MINUTE;

            String strStartHour = startHour < TIME_LIMIT ? (SUPPLEMENT_TIME + startHour) : startHour.toString();
            String strStartMinute = startMinute < TIME_LIMIT ? (SUPPLEMENT_TIME + startMinute) : startMinute.toString();
            String strEndHour = endHour < TIME_LIMIT ? (SUPPLEMENT_TIME + endHour) : endHour.toString();
            String strEndMinute = endMinute < TIME_LIMIT ? (SUPPLEMENT_TIME + endMinute) : endMinute.toString();

            dinnerTypeView.setStrDefaultStartTime(strStartHour + ":" + strStartMinute);
            dinnerTypeView.setStrDefaultEndTime(strEndHour + ":" + strEndMinute);
            return dinnerTypeView;
        } catch (Exception e) {
            log.error("DinnerTypeView.intDefaultTimeToStr error DinnerTypeDO=[{}]", dinnerTypeDO, e);
            return null;
        }
    }
}
