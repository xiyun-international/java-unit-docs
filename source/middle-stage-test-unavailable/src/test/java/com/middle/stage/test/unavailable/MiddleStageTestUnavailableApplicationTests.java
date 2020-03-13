package com.middle.stage.test.unavailable;

import com.alibaba.fastjson.JSONObject;
import com.middle.stage.test.unavailable.commons.CallResult;
import com.middle.stage.test.unavailable.commons.HttpClientUtil;
import com.middle.stage.test.unavailable.data.PushData;
import com.middle.stage.test.unavailable.data.ShopDO;
import com.middle.stage.test.unavailable.mapper.PushDataMapper;
import com.middle.stage.test.unavailable.mapper.ShopMapper;
import com.middle.stage.test.unavailable.service.impl.ShopServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class MiddleStageTestUnavailableApplicationTests {

    @Mock
    private PushDataMapper pushDataMapper;

    @Mock
    private ShopMapper shopMapper;

    @Mock
    private HttpClientUtil httpClientUtil;

    @InjectMocks
    private ShopServiceImpl shopService;

    private static PushData pushData;

    private static ShopDO shopDO;

    private static Integer objectId = 123;

    private String url = "http://127.0.0.1:6666/:";

    private String httpResult = "ok";

    @BeforeAll
    static void beforePushDataTest() {
        pushData = new PushData();
        pushData.setObjectId(objectId);
        pushData.setObjectType(1);

        shopDO = new ShopDO();
        shopDO.setShopId(objectId);
        shopDO.setShopName("zyq");
    }

    @Test
    void pushDataTest() throws Exception {

        when(pushDataMapper.selectLately()).thenReturn(pushData);
        when(shopMapper.selectById(objectId)).thenReturn(shopDO);
        when(httpClientUtil.sendHttpPost(url, JSONObject.toJSONString(shopDO))).thenReturn(httpResult);

        CallResult callResult = shopService.pushData();

        verify(pushDataMapper).selectLately();
        verify(shopMapper).selectById(objectId);
        verify(httpClientUtil).sendHttpPost(url, JSONObject.toJSONString(shopDO));

        Assertions.assertEquals(CallResult.RETURN_STATUS_OK, callResult.getCode());
        Assertions.assertEquals(callResult.getContent(), httpResult);
        log.info("[测试通过]");

    }
}
