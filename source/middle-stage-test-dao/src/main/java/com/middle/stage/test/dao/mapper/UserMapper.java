package com.middle.stage.test.dao.mapper;

import com.middle.stage.test.dao.data.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insert(UserDO record);

    /**
     * 添加不为空字段
     * @param userDO
     * @return
     */
    int insertSelective(UserDO userDO);

    /**
     * 通过手机号查询
     * @param mobile
     * @return
     */
    UserDO selectByMobile(String mobile);
}