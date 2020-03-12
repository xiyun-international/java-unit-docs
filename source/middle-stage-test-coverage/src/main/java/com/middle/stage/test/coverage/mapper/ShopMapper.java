package com.middle.stage.test.coverage.mapper;

import com.middle.stage.test.coverage.data.ShopDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopMapper {

    /**
     * 查询门店信息
     *
     * @param shopId
     * @return
     */
    ShopDO selectById(Integer shopId);
}