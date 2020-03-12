package com.middle.stage.test.coverage.data;

import lombok.Data;

/**
 * @author zyq
 */
@Data
public class UserModel {

    private String userId;

    private String openId;

    private String mobile;

    private String userName;

    public UserModel(String userId, String openId, String mobile, String userName) {
        this.userId = userId;
        this.openId = openId;
        this.mobile = mobile;
        this.userName = userName;
    }
}
