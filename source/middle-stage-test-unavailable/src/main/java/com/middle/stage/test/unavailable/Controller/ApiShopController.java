package com.middle.stage.test.unavailable.Controller;

import com.alibaba.fastjson.JSONObject;
import com.middle.stage.test.unavailable.commons.CallResult;
import com.middle.stage.test.unavailable.data.ShopDO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zyq
 */
@RestController
public class ApiShopController {

    @PostMapping("/shop/save")
    public CallResult shopSave(@RequestBody ShopDO shopDO) {
        if (shopDO == null) {
            return CallResult.fail(CallResult.RETURN_STATUS_PARAM_ERROR, "参数异常，请检查参数！");
        }
        return CallResult.success(CallResult.RETURN_STATUS_OK, "数据保存成功", JSONObject.toJSONString(""));
    }
}
