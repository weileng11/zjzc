package cn.bs.zjzc.net;

public class RequestUrl {

    public interface SubPaths {
        // 注册
        String regPath = "/Login/reg";
        /**
         * 获取短信验证码 类型（默认 1 为注册，2 为忘记密码，3 为修改支付密码，
         * 4 为更换绑定手机, 5 为修改登录密码，6 为验证收货）
         */
        String getCheckCodePath = "/Login/get_code";
        // 登录
        String loginPath = "/Login/login";
        // 注销
        String logoutPath = "/Login/logout";
        // 修改登录密码
        String changeLoginPassword = "/Login/change_pwd";
        // 修改头像
        String changeHeader = "/My/change_head";
        //忘记密码
        String forgetPassword = "/Login/forget_pwd";
        //获取用户信息
        String getUserInfo = "/My/get_info";
        //修改用户信息
        String changeUserInfo = "/My/change_info";
        //获取行业信息
        String getIndustry = "/My/out_list";
        //反馈信息
        String feeback = "/Sms/feedback";
        //获取反馈信息列表
        String feebackList = "/Sms/feedback_list";
        //删除反馈信息
        String deleteFeedback = "/Sms/feedback_del";
        //申请接单用户
        String applyOrderTaker = "/My/apply_accept";
        //我的钱包首页信息
        String walletIndexInfo = "/Wallet/index";
        //认证信息
        String verificationInfo = "/My/my_apply";
        //获取账户余额信息
        String balanceInfo = "/Wallet/balance";
        //获取绑定银行卡帐号信息
        String getBankInfo = "/Wallet/withdraw_acount";
        //获取银行卡列表
        String getBankList = "/Wallet/rank_list";
        //添加帐号
        String addBackAccount = "/Wallet/change_withdraw_acount";
        //提现
        String withdraw = "/Wallet/withdraw";
        //充值
        String recharge = "/Wallet/recharge";
        //获取充值明细
        String rechargeDetail = "/Wallet/recharge_list";
        //获取提现明细
        String withdrawDetail = "/Wallet/withdraw_list";
        //获取站内信消息列表
        String newsList = "/sms/sms";
        //设置站内信消息列表为已读
        String setNewsRead = "/sms/read";
        //删除站内信消息列表
        String deleteNews = "/sms/delete";
        //编辑订单设置
        String oderSetting = "/Data/edit_order_setting";
        //获取订单设置
        String getOderSetting = "/Data/order_setting";
        //获取配送距离和运费
        String getCarryMoney = "/Order/get_carry_money";
        //获取广告列表
        String getAdvertisementList = "/ad/ad";
        //获取省份和城市列表
        String getProvinceCityList = "/Data/get_city_list";
        //编辑常用地址
        String editAddress = "/Data/edit_address";
        //订单详情
        String orderDetail = "/Order/order_detail";
        //下单
        String order = "/Order/order";
        //订单列表
        String orderList = "/Order/order_list";
        //常用地址
        String getOftenAddress = "/Data/get_address";
        // 取消订单
        String cancelOrder = "/Order/order_cancel";
        // 确认取货
        String confirmTakeGoods = "/Order/take_goods";
        //保价费计算方式
        String getInsuredWay = "/Order/insured_way";
        //获取订单总费用--支付前
        String getOrderAmount = "/Order/order_amount";
        //订单可用优惠券
        String orderCoupon = "/Coupon/order_coupon";
        //订单支付
        String orderPay = "/Order/pay_order";
        //订单评价
        String orderEvaluation = "/Order/evaluate";
        //查看订单评价
        String checkEvaluation = "/Evaluate/order_evaluate";
        //确认取货(我的接单)
        String takeGoods = "/Order/take_goods";
        //完成订单
        String finishOrder = "/Order/complete";
        //获取(完成订单的)验证码
        String sendCode = "/Order/send_code";
        //获取评价列表
        String getEvaluationList = "/Evaluate/evaluate_list";
        //订单支付完成--添加小费
        String addTipMoney = "/Order/add_tip_money";
        //获取收支明细列表
        String fundDetail = "/Wallet/balance_pay_list";
        //获取常见问题列表
        String getFAQList = "/Data/faq_list";
        //获取积分列表
        String getPointList = "/Data/points_list";
        //版本更新
        String apkUpdate = "/Data/apk_update";
        //星级说明
        String starRule = "/Html/star_code";
        //获取客服电话
        String servicePhone = "/Data/customer_phone";
        //获取费用列表
        String charge_list = "/Html/charge_list";
        //获取优惠券使用规则
        String couponRule = "/Html/coupon_rule";
        //获取优惠券列表
        String couponList = "/Coupon/my_coupon";
        //首页地图上，附近接单，下单用户的 锚点
        String nearbyRadar = "/Data/map_show";
    }

    public interface WebPath {
        //获取用户守则
        String getUserRule = "/user_code";
        //关于我们
        String aboutMe = "/about";
        //用户注册协议
        String userAgreement = "/user_agreement";
        //
        String pointRule = "/points_rule";
        //广告详情
        String advDetail = "/api.php/ad/ad_main";
    }

    public static final String socketHost = "219.137.154.4";
    public static final int socketPort = 8877;

    public static String getRequestPath(String subPath) {
        return rootUrl + subPath;
    }

    public static String getWebRequestPath(String subPath) {
        return webRootUrl + subPath;
    }

    public static String getImgPath(String imgPath) {
        return imgRootUrl + imgPath;
    }

//    static final String rootUrl = "http://yafeng.ibona.cn/api.php";
//    static final String webRootUrl = "";
//    static final String imgRootUrl = "http://yafeng.ibona.cn";

    static final String rootUrl = "http://666.1kb.ren:8899/api.php";
    static final String webRootUrl = "http://666.1kb.ren:8899/api.php/Html";
    static final String imgRootUrl = "http://666.1kb.ren:8899";
}