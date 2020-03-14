package com.middle.stage.test.coverage;

import com.alibaba.fastjson.JSONObject;
import com.middle.stage.test.coverage.commons.CallResult;
import com.middle.stage.test.coverage.data.MarkttingModel;
import com.middle.stage.test.coverage.data.ShopDO;
import com.middle.stage.test.coverage.data.UserModel;
import com.middle.stage.test.coverage.dubbo.FuyouDubboService;
import com.middle.stage.test.coverage.dubbo.KoubeiDubboService;
import com.middle.stage.test.coverage.dubbo.UserDubboService;
import com.middle.stage.test.coverage.enums.PayTypeEnum;
import com.middle.stage.test.coverage.mapper.ShopMapper;
import com.middle.stage.test.coverage.service.impl.ShopServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class ShopServiceImplTest {

    //通过注解模拟依赖的接口或类
    @Mock
    private ShopMapper mockShopMapper;
    @Mock
    private FuyouDubboService mockFuyouDubboService;
    @Mock
    private KoubeiDubboService mockKoubeiDubboService;
    @Mock
    private UserDubboService mockUserDubboService;

    //通过注解自动注入依赖关系
    @InjectMocks
    private ShopServiceImpl shopService;

    //参数
    static MarkttingModel markttingModel;

    //模拟门店结果
    static ShopDO mockShopResult;

    //模拟openID查询结果
    static CallResult mockOpenIdResult;

    static CallResult mockUserResult;

    static int shopId = 999;

    static String openId = "asdasd56789asdfgjhkklllasd";

    static String falg = "123456";

    static UserModel mockUserModel;


    @BeforeAll
    static void beforLoginTest() {
        markttingModel = new MarkttingModel();
        markttingModel.setPayAmount(new BigDecimal("26.75"));
        markttingModel.setPayType(PayTypeEnum.PAY_TYPE_FUYOU.getCode());
        markttingModel.setShopId(shopId);
        markttingModel.setFlag(falg);

        mockShopResult = new ShopDO();
        mockShopResult.setId(shopId);
        mockShopResult.setMarktingSwitch(true);

        mockOpenIdResult = new CallResult(CallResult.RETURN_STATUS_OK, "查询成功", "asdasd56789asdfgjhkklllasd");
        mockUserModel = new UserModel("zyq", openId, "17612345678", "zyq");
        mockUserResult = new CallResult(CallResult.RETURN_STATUS_OK, "查询成功", JSONObject.toJSONString(mockUserModel));

    }


    @Test
    @DisplayName("营销支付测试")
    void payTest() {

        Assertions.assertNotNull(markttingModel, "markttingModel can not be null!");
        Assertions.assertNotNull(mockShopResult, "shopResult can not be null!");
        Assertions.assertNotNull(mockOpenIdResult, "openIdResult can not be null!");
        Assertions.assertNotNull(mockUserModel, "userModel can not be null!");

        when(mockShopMapper.selectById(shopId)).thenReturn(mockShopResult);
        when(mockFuyouDubboService.getOpenId(falg)).thenReturn(mockOpenIdResult);
        when(mockUserDubboService.getUserInfo(openId)).thenReturn(mockUserResult);

        CallResult callResult = shopService.pay(markttingModel);
        Assertions.assertEquals(CallResult.RETURN_STATUS_OK, callResult.getCode());
        UserModel userModel = JSONObject.parseObject(callResult.getContent(), UserModel.class);
        Assertions.assertEquals(mockUserModel.getMobile(), userModel.getMobile());

        log.info("[测试通过]");
    }
}