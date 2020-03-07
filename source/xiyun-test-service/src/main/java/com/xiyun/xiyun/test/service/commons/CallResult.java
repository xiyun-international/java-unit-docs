package com.xiyun.xiyun.test.service.commons;

import lombok.Data;

/**
 * 响应信息实体类
 *
 * @author zhaoyongqiang
 * @date 2020/3/7
 */
@Data
public class CallResult {

    /**
     * 成功
     */
    public static final int RETURN_STATUS_OK = 1;

    /**
     * 失败
     */
    public static final int RETURN_STATUS_FAIL = -1;

    /**
     * 未注册
     */
    public static final int RETURN_STATUS_UNREGISTERED = -2;

    /**
     * 密码不正确
     */
    public static final int RETURN_STATUS_PASW_INCORRECT = -3;

    /**
     * 状态值
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 数据对象
     */
    private Object content;

    public CallResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CallResult(int code, String msg, Object content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public static CallResult success(int code, String msg, Object content) {
        return new CallResult(code, msg, content);
    }

    public static CallResult fail(int code, String msg) {
        return new CallResult(code, msg);
    }
}
