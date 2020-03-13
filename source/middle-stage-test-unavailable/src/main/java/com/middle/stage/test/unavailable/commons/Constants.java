package com.middle.stage.test.unavailable.commons;


import java.nio.charset.Charset;

/**
 * 通用常量定义
 *
 * @author liukui
 */
public class Constants {
    /**
     * 满客宝订单列表接口
     */
    public static final String MKB_API_ORDER_LIST_URL = "https://xyxk.mankebao.cn/api/wwxBillInfo.action";

    /**
     * 纵横客接口签名盐
     */
    public static final String ZHK_API_SALT = "sdfa92u4jwmdlmc";

    /**
     * 网关接口签名salt
     */
    public static final String GW_API_SALT = "FAASCBKcwggSjAgEAAoIBAQCBK";

    /**
     * 开放平台签名salt
     */
    public static final String OPL_API_SALT = "txzkUIMrWmPU9Bo";

    /**
     * 设置获取docker容器中的环境变量
     */
    static {
        System.getenv().forEach((key, value) -> {
            System.setProperty(key, value);
        });
    }

    /**
     * 商家中心环节变量
     *
     * @Author: liukui
     */
    public static class DefineEnv {
        /**
         * 生产环境
         */
        public final static String ENV_PROD = "prod";
        /**
         * 测试环境
         */
        public final static String ENV_TEST = "test";
        /**
         * 开发环境
         */
        public final static String ENV_DEV = "dev";
        /**
         * 开发环境
         */
        public final static String ENV_MIRROR = "mirror";

    }

    /**
     * 相关定义
     */
    public static class DefineHttp {
        /**
         * PSOT请求
         */
        public static final String REQUEST_POST_TYPE = "POST";
        /**
         * GET请求
         */
        public static final String REQUEST_GET_TYPE = "GET";
        /**
         * 请求字符集UTF-8
         */
        public static final String CHARSET_UTF_8 = Charset.forName("UTF-8").name();

        /**
         * 请求字符集GBK
         */
        public static final String CHARSET_GBK = Charset.forName("GBK").name();

        /**
         * ISO-8859-1
         */
        public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";
        /**
         * windows 系统
         */
        public static final String REQUEST_WINDOW_NT = "windows nt";
        public static final String OS_IOS = "";

    }

    public static class DefineSign {
        /**
         * 未知类型
         */
        public static final String UNKNOWN = "unknown";
        /**
         * 英文逗号
         */
        public static final String COMMA = ",";
        /**
         * 网络URL请求分隔符
         */
        public static final String HTTP_URL_SPLIT = "&";
        /**
         * 网络URL请求等号
         */
        public static final String HTTP_URL_EQUAL = "=";
        /**
         * 英文点
         */
        public static final String DOT = ".";

    }
}
