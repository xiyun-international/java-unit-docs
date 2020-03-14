package com.middle.stage.test.web;

import com.middle.stage.test.data.UserDO;
import com.middle.stage.test.service.UserService;
import com.middle.stage.test.commons.CallResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user/login")
    public CallResult login(@RequestBody UserDO userDO) {
        if (userDO == null) {
            return CallResult.fail(CallResult.RETURN_STATUS_PARAM_ERROR, "参数异常，请检查参数！");
        }
        return userService.login(userDO);
    }
}
