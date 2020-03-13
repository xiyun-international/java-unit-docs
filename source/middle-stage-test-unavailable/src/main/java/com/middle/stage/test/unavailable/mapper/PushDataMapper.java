package com.middle.stage.test.unavailable.mapper;

import com.middle.stage.test.unavailable.data.PushData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PushDataMapper {

    /**
     * 查询最近的推送任务
     *
     * @return
     */
    PushData selectLately();
}