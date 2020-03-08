package com.middle.stage.test.dao.mapper;

import com.middle.stage.test.dao.data.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDOMapper {

    int insert(UserDO record);

    /**
     * 添加不为空字段
     * @param record
     * @return
     */
    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(String id);

    /**
     * 通过手机号查询
     * @param mobile
     * @return
     */
    UserDO selectByMobile(String mobile);
}