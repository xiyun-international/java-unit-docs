package com.middle.stage.test.optimization.dao;


import com.middle.stage.test.optimization.dao.data.DinnerTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 餐别类型mapper
 *
 * @author zhaoyongqiang
 * @date 2020/1/6
 */
@Mapper
@Component(value = "DinnerTypeMapper")
public interface DinnerTypeMapper {
    /**
     * 根据主键删除
     *
     * @param dinnerTypeId
     * @return
     */
    int deleteByPrimaryKey(Integer dinnerTypeId);

    /**
     * 添加餐别信息
     *
     * @param dinnerTypeDO
     * @return
     */
    int insert(DinnerTypeDO dinnerTypeDO);

    /**
     * 添加门店餐别信息(字段不为空)
     *
     * @param dinnerTypeDO
     * @return
     */
    int insertSelective(DinnerTypeDO dinnerTypeDO);

    /**
     * 根据主键查询实体类
     *
     * @param dinnerTypeId
     * @return
     */
    DinnerTypeDO selectByPrimaryKey(Integer dinnerTypeId);

    /**
     * 根据主键更新不为空的字段
     *
     * @param dinnerTypeDO
     * @return
     */
    int updateByPrimaryKeySelective(DinnerTypeDO dinnerTypeDO);

    /**
     * 根据主键更新全部
     *
     * @param dinnerTypeDO
     * @return
     */
    int updateByPrimaryKey(DinnerTypeDO dinnerTypeDO);

    /**
     * 查询全部
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


}