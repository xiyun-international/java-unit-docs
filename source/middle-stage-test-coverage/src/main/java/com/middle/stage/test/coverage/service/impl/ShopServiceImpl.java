package com.middle.stage.test.coverage.service.impl;

import com.middle.stage.test.coverage.commons.CallResult;
import com.middle.stage.test.coverage.data.MarkttingModel;
import com.middle.stage.test.coverage.data.ShopDO;
import com.middle.stage.test.coverage.dubbo.FuyouDubboService;
import com.middle.stage.test.coverage.dubbo.KoubeiDubboService;
import com.middle.stage.test.coverage.dubbo.UserDubboService;
import com.middle.stage.test.coverage.enums.PayTypeEnum;
import com.middle.stage.test.coverage.mapper.ShopMapper;
import com.middle.stage.test.coverage.service.ShopService;
import org.springframework.util.StringUtils;

/**
 * @author zhaoyongqiang
 * @date 2020/3/7
 */
public class ShopServiceImpl implements ShopService {

    private ShopMapper shopMapper;

    private FuyouDubboService fuyouDubboService;

    private KoubeiDubboService koubeiDubboService;

    private UserDubboService userDubboService;

    /**
     * Mockito使用注解注入依赖关系，需提供构造器
     *
     * @param fuyouDubboService
     * @param koubeiDubboService
     * @param userDubboService
     */
    public ShopServiceImpl(FuyouDubboService fuyouDubboService, KoubeiDubboService koubeiDubboService, UserDubboService userDubboService, ShopMapper shopMapper) {
        this.fuyouDubboService = fuyouDubboService;
        this.koubeiDubboService = koubeiDubboService;
        this.userDubboService = userDubboService;
        this.shopMapper = shopMapper;
    }

    @Override
    public CallResult pay(MarkttingModel markttingModel) {

        CallResult callResult = validate(markttingModel);
        if (callResult != null) {
            return callResult;
        }
        ShopDO shopDO = shopMapper.selectById(markttingModel.getShopId());
        if (!shopDO.getMarktingSwitch()) {
            return CallResult.fail(CallResult.RETURN_STATUS_FAIL, "门店未开通营销活动!");
        }
        CallResult openIdResult = null;
        if (markttingModel.getPayType().equals(PayTypeEnum.PAY_TYPE_FUYOU.getCode())) {
            openIdResult = fuyouDubboService.getOpenId(markttingModel.getFlag());
        }
        if (markttingModel.getPayType().equals(PayTypeEnum.PAY_TYPE_KOUBEI.getCode())) {
            openIdResult = koubeiDubboService.getAliUserId(markttingModel.getFlag());
        }
        if (openIdResult == null || openIdResult.getCode() == CallResult.RETURN_STATUS_FAIL) {
            return CallResult.fail(CallResult.RETURN_STATUS_FAIL, "查询openIdResult失败!");
        }
        String openId = openIdResult.getContent();

        return userDubboService.getUserInfo(openId);
    }

    private CallResult validate(MarkttingModel markttingModel) {
        if (markttingModel == null) {
            return CallResult.fail(CallResult.RETURN_STATUS_FAIL, "参数为空!");
        }
        if (markttingModel.getPayType() == null) {
            return CallResult.fail(CallResult.RETURN_STATUS_FAIL, "payType为空!");
        }
        if (markttingModel.getShopId() == null) {
            return CallResult.fail(CallResult.RETURN_STATUS_FAIL, "shopId为空!");
        }
        if (markttingModel.getPayAmount() == null) {
            return CallResult.fail(CallResult.RETURN_STATUS_FAIL, "userID为空!");
        }
        if (StringUtils.isEmpty(markttingModel.getShopId())) {
            return CallResult.fail(CallResult.RETURN_STATUS_FAIL, "payAmount为空!");
        }
        return null;
    }
}
