package com.hhxc.demo.pay;

public class Config {

    /**
     * 微信支付-配置
     */

    // 商户号
    public static final String WX_MCH_ID = "1265371401";

    // 应用ID
    public static final String WX_APP_ID = "wx2602be4e3d11a326";

    // API密钥，在商户平台设置
    public static final String WX_API_KEY = "ee1dd9e6d4468139e0bdda5904b6785e";

    /**
     * 支付宝-配置
     */

    // 商户PID
    public static final String ZFB_PARTNER = "2088021271435195";

    // 商户收款账号
    public static final String ZFB_SELLER = "150394459@qq.com";

    // 商户私钥，pkcs8格式
    public static final String ZFB_RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALPl3QFk+kOOppBJ1ldZWCBlLp7hGW2tIpeRkc2EAyOHCfdcFO+Syo32SHcgw3BIAcrelUnUm8HaFESMxW9p5GeCq+tii9IO6nU7EEAx+8U96845bfrY+syuuIr602DKsUMnCdxqKN2dr4fs/WnvxOgcNs1p271F5E1cc1AMPLSTAgMBAAECgYBI9uE1kJQk1HXgLeJ+mSEyOne3PwtGPaO7H/KoDXQMc9fp+PIQ3VPEUnC/yI0cPEhl1PNt94qZDG+OAh6N+sAFQIXPwq0+d3PvhPz/ATk2ZqcnvvkDDrgU2xNvyj13okkbNiBP3rMKCidOa/AqVZ3VX/gxgYoy2HIeseuVbcmPYQJBAO/K7c3YO59qiNYHgh56c2t8FCKpbJX9uvDgUUaTfSmKJdDFEyQMoFQLykyhPXmSDoBDlnG267iymTGVPFndLvECQQDADpcfSmqZMqKpMmzdsevDpcgSY1c5M8ID4MTziqfGjV8CFGGT0r+LVDPwrSrRIeFiHFz3R6Zjm6vxNsi+xyPDAkEA4SC7XOvrJkMmDxNjhWvfWwVV/ijkinl5/lYL2F/2PX62/CdaThkQmXesSX+EMNQAi0lpQH/FFVWQYC3FSHVRcQJBAL2JaKDLyxm3fAnopsG/RgJGSIXFHh5osu8q0pPa4aq0d+/Qd1/wW3JAPiYmb5wGiKxPe3vabR3eUrjxrPmCBAsCQQCC/I2nsLA7tItkTJ1GeExPP30IY0iZOwAFiaCLEAHnfzDjbKRwjUxg4IL/hnsy7IUOqq+bbZ5xXAL9jYZmNaBo";

    // 商户公钥
    public static final String ZFB_RSA_PUBLIC = "";

    /**
     * 支付超时时间：10分钟
     */
    public static int PAY_TIMEOUT_WX = 60 * 10; // 微信支付，单位：秒
    public static String PAY_TIMEOUT_ALI = "10m"; // 支付宝支付

}
