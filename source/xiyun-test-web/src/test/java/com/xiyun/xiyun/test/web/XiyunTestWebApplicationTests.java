package com.xiyun.xiyun.test.web;

import com.alibaba.fastjson.JSONObject;
import com.xiyun.xiyun.test.dao.data.UserDO;
import com.xiyun.xiyun.test.service.commons.CallResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class XiyunTestWebApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginInterfaceTest() throws Exception {

        UserDO userDO = new UserDO();
        userDO.setMobile("17611166370");
        userDO.setPassword("123456");

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userDO)))
                .andReturn()
                .getResponse();
        //验证http状态码
        Assertions.assertNotEquals(MockMvcResultMatchers.status().isOk(), response.getStatus());
        CallResult callResult = JSONObject.parseObject(response.getContentAsString(), CallResult.class);
        //验证业务状态码
        Assertions.assertNotEquals(callResult.getCode(), CallResult.RETURN_STATUS_OK);

    }


}
