package com.middle.stage.test.unavailable.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.middle.stage.test.unavailable.commons.CallResult;
import com.middle.stage.test.unavailable.commons.HttpClientUtil;
import com.middle.stage.test.unavailable.data.PushData;
import com.middle.stage.test.unavailable.data.ShopDO;
import com.middle.stage.test.unavailable.mapper.PushDataMapper;
import com.middle.stage.test.unavailable.mapper.ShopMapper;
import com.middle.stage.test.unavailable.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaoyongqiang
 * @date 2020/3/7
 */
@Slf4j
@Service
public class ShopServiceImpl implements ShopService {


    @Autowired
    private PushDataMapper pushDataMapper;

    @Autowired
    private ShopMapper shopMapper;

    private HttpClientUtil httpClientUtil;

    private String url = "http://127.0.0.1:6666/:";

    public ShopServiceImpl(PushDataMapper pushDataMapper, ShopMapper shopMapper, HttpClientUtil httpClientUtil) {
        this.pushDataMapper = pushDataMapper;
        this.shopMapper = shopMapper;
        this.httpClientUtil = httpClientUtil;
    }

    public ShopServiceImpl() {

    }

    @Override
    public CallResult pushData() {

        PushData pushData = pushDataMapper.selectLately();
        if (pushData == null) {
            return CallResult.success(CallResult.RETURN_STATUS_OK, "没有可以推送的数据", null);
        }
        ShopDO shopDO = shopMapper.selectById(pushData.getObjectId());

        CallResult callResult = null;
        try {
            String result = httpClientUtil.sendHttpPost(url, JSONObject.toJSONString(shopDO));
            callResult = new CallResult(CallResult.RETURN_STATUS_OK, "调用成功", result);
        } catch (Exception e) {
            callResult = new CallResult(CallResult.RETURN_STATUS_FAIL, "推送数据失败！");
            log.error("推送数据失败  callResult = [{}]", callResult, e);
        }
        return callResult;
    }
}
