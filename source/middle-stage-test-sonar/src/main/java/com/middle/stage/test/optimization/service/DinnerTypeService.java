package com.middle.stage.test.optimization.service;


import com.middle.stage.test.optimization.dao.data.DinnerTypeDO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 餐别类型业务处理类
 *
 * @author zhaoyongqiang
 * @date 2020/1/6
 */
@Service
public interface DinnerTypeService {

    /**
     * 根据主键查询实体类
     *
     * @param dinnerTypeId
     * @return
     */
    DinnerTypeDO selectByPrimaryKey(Integer dinnerTypeId);

    /**
     * 查询全部餐别
     *
     * @return
     */
    List<DinnerTypeDO> selectAll();

    /**
     * 根据门店查询生效的餐别信息
     *
     * @return
     */
    List<DinnerTypeDO> selectDinnerTypeByCanteenId(Integer canteenId);

    /**
     * 餐别集合转换成map
     *
     * @param list
     * @return
     */
    HashMap<Integer, DinnerTypeDO> listToMap(List<DinnerTypeDO> list);
}
