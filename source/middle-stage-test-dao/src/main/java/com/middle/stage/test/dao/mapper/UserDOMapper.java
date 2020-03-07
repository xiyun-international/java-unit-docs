package com.middle.stage.test.dao.mapper;

import com.middle.stage.test.dao.data.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDOMapper {

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(String id);

    UserDO selectByMobile(String mobile);
}