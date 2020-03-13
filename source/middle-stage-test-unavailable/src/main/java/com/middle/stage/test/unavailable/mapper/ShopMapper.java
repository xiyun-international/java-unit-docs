package com.middle.stage.test.unavailable.mapper;

import com.middle.stage.test.unavailable.data.ShopDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopMapper {

    /**
     * 通过推送标识查找门店数据
     *
     * @param objectId
     * @return
     */
    ShopDO selectById(Integer objectId);
}