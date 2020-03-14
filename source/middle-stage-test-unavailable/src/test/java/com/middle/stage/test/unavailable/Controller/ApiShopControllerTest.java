package com.middle.stage.test.unavailable.Controller;

import com.alibaba.fastjson.JSONObject;
import com.middle.stage.test.unavailable.commons.CallResult;
import com.middle.stage.test.unavailable.data.ShopDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ApiShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static ShopDO shopDO;

    @BeforeAll
    static void beforLoginTest() {
        shopDO = new ShopDO();
        shopDO.setShopId(1);
        shopDO.setShopName("zyq");
    }

    @Test
    void loginInterfaceTest() throws Exception {

        //验证测试用例是否创建
        Assertions.assertNotNull(shopDO, "shopDO is null");

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/shop/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(shopDO)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();
        //验证http状态码
        Assertions.assertNotEquals(MockMvcResultMatchers.status().isOk(), response.getStatus());
        CallResult callResult = JSONObject.parseObject(response.getContentAsString(), CallResult.class);
        //验证业务状态码
        Assertions.assertEquals(callResult.getCode(), CallResult.RETURN_STATUS_OK);
        log.info("[测试通过]");
    }
}