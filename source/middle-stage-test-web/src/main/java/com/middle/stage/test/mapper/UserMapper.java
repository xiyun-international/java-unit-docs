package com.middle.stage.test.mapper;

import com.middle.stage.test.data.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 通过手机号查询
     * @param mobile
     * @return
     */
    UserDO selectByMobile(String mobile);
}