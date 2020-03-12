package com.middle.stage.test.optimization.dao.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 门店数据传输对象
 *
 * @author cjw
 * @date 2019-07-30
 */
@Data
public class CanteenDTO implements Serializable {
    private static final long serialVersionUID = 6181781965383592955L;

    private Integer canteenId;

    private String canteenName;

    private String canteenCode;

    private Byte type;

    private Integer supplierId;

    private Integer merchantId;

    private Integer orgId;

    private String orgFullCode;

    private Integer equId;

    private Integer contractorId;

    private String isvShopId;

    private String isvId;

    private Byte status;

    private Byte pushStatus;

    private Integer createTime;

    private Integer updateTime;

    private String qrImageUrl;

    private Byte generateStatus;

    private String qrSingleUrl;


    private Byte belongTo;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Query extends CanteenDTO {
        private static final long serialVersionUID = -5758289203622302348L;
    }
}
