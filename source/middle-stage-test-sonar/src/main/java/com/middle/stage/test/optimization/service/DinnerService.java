package com.middle.stage.test.optimization.service;

import com.middle.stage.test.optimization.dao.data.DinnerDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店所属餐别业务处理类
 *
 * @author zhaoyongqiang
 * @date 2020/1/6
 */
@Service
public interface DinnerService {

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
     * 批量添加
     *
     * @param dinnerList
     */
    int batchInsert(List<DinnerDO> dinnerList);

    /**
     * 逻辑删除餐别门店关系数据
     *
     * @param list 老数据
     * @return 更新条数
     */
    int batchDeleteByCondition(List<DinnerDO> list);

    int batchUpdateByCondition(List<DinnerDO> list);

    /**
     * 根据门店查询所有餐别信息
     *
     * @param canteenId
     * @return
     */
    List<DinnerDO> selectByCanteenId(Integer canteenId);

}
