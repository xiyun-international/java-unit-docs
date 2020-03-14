package com.middle.stage.test.optimization.dao;


import com.middle.stage.test.optimization.dao.data.DinnerDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 门店餐别关系mapper
 *
 * @author zhaoyongqiang
 * @date 2020/1/6
 */
@Mapper
@Component(value = "DinnerMapper")
public interface DinnerMapper {
    /**
     * 根据主键删除
     *
     * @param dinnerId
     * @return
     */
    int deleteByPrimaryKey(Integer dinnerId);

    /**
     * 添加门店餐别信息
     *
     * @param dinnerDO
     * @return
     */
    int insert(DinnerDO dinnerDO);

    /**
     * 批量添加门店餐别信息
     *
     * @param newDinnerList
     * @return
     */
    int batchInsert(List<DinnerDO> newDinnerList);

    /**
     * 添加门店餐别信息（字段不为空）
     *
     * @param dinnerDO
     * @return
     */
    int insertSelective(DinnerDO dinnerDO);

    /**
     * 根据主键查询实体类
     *
     * @param dinnerId
     * @return
     */
    DinnerDO selectByPrimaryKey(Integer dinnerId);

    /**
     * 根据主键更新不为空的字段
     *
     * @param dinnerDO
     * @return
     */
    int updateByPrimaryKeySelective(DinnerDO dinnerDO);

    /**
     * 根据主键更新全部
     *
     * @param dinnerDO
     * @return
     */
    int updateByPrimaryKey(DinnerDO dinnerDO);

    /**
     * 逻辑删除餐别门店关系数据
     *
     * @param list 老数据
     * @return 更新条数
     */
    int batchDeleteByCondition(List<DinnerDO> list);

    /**
     * 批量更新餐别门店关系数据
     *
     * @param list 老数据
     * @return 更新条数
     */
    int batchUpdateByCondition(List<DinnerDO> list);

    /**
     * 根据门店查询所有餐别信息
     *
     * @param canteenId
     * @return
     */
    List<DinnerDO> selectByCanteenId(Integer canteenId);


    /**
     * @author yifan.wu
     *
     * @date 2020-01-15 17:27
     * @decription 查询门店餐别信息
     * @param
     * @return
     */
    List<DinnerDO> selectListBySearch(DinnerDO.DinnerSearch searchModel);
}