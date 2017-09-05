package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/6/16.
 */
public class RechargeResponse extends BaseResponse {


    /**
     * id : ch_Dm9en1XjvHu9Gybnn59OSCiP
     * object : charge
     * created : 1466494907
     * livemode : false
     * paid : false
     * refunded : false
     * app : app_T8WfD8r5CyHC9OWz
     * channel : alipay
     * order_no : PA2016062115414885215
     * client_ip : 192.168.10.11
     * amount : 10000
     * amount_settle : 10000
     * currency : cny
     * subject : 余额充值
     * body : 余额充值
     * extra : {}
     * time_paid :
     * time_expire : 1466581307
     * time_settle :
     * transaction_no :
     * refunds : {"object":"list","url":"/v1/charges/ch_Dm9en1XjvHu9Gybnn59OSCiP/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code :
     * failure_msg :
     * metadata : {}
     * credential : {"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"余额充值\"&it_b_pay=\"2016-06-22 15:41:47\"&notify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_Dm9en1XjvHu9Gybnn59OSCiP\"&out_trade_no=\"PA2016062115414885215\"&partner=\"2008864731752057\"&payment_type=\"1\"&seller_id=\"2008864731752057\"&service=\"mobile.securitypay.pay\"&subject=\"余额充值\"&total_fee=\"100.00\"&sign=\"SDQweVRHSzBHMFdER1N5amY1YnpYMU9P\"&sign_type=\"RSA\""}}
     * description :
     */

    public DataBean data;

    public static class DataBean {
        public String id;
        public String object;
        public long created;
        public boolean livemode;
        public boolean paid;
        public boolean refunded;
        public String app;
        public String channel;
        public String order_no;
        public String client_ip;
        public long amount;
        public long amount_settle;
        public String currency;
        public String subject;
        public String body;
        public ExtraBean extra;
        public String time_paid;
        public long time_expire;
        public String time_settle;
        public String transaction_no;
        /**
         * object : list
         * url : /v1/charges/ch_Dm9en1XjvHu9Gybnn59OSCiP/refunds
         * has_more : false
         * data : []
         */

        public RefundsBean refunds;
        public int amount_refunded;
        public String failure_code;
        public String failure_msg;
        public MetadataBean metadata;
        /**
         * object : credential
         * alipay : {"orderInfo":"_input_charset=\"utf-8\"&body=\"余额充值\"&it_b_pay=\"2016-06-22 15:41:47\"&notify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_Dm9en1XjvHu9Gybnn59OSCiP\"&out_trade_no=\"PA2016062115414885215\"&partner=\"2008864731752057\"&payment_type=\"1\"&seller_id=\"2008864731752057\"&service=\"mobile.securitypay.pay\"&subject=\"余额充值\"&total_fee=\"100.00\"&sign=\"SDQweVRHSzBHMFdER1N5amY1YnpYMU9P\"&sign_type=\"RSA\""}
         */

        public CredentialBean credential;
        public String description;

        public static class ExtraBean {
        }

        public static class RefundsBean {
            public String object;
            public String url;
            public boolean has_more;
            public List<?> data;
        }

        public static class MetadataBean {
        }

        public static class CredentialBean {
            public String object;
            /**
             * orderInfo : _input_charset="utf-8"&body="余额充值"&it_b_pay="2016-06-22 15:41:47"&notify_url="https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_Dm9en1XjvHu9Gybnn59OSCiP"&out_trade_no="PA2016062115414885215"&partner="2008864731752057"&payment_type="1"&seller_id="2008864731752057"&service="mobile.securitypay.pay"&subject="余额充值"&total_fee="100.00"&sign="SDQweVRHSzBHMFdER1N5amY1YnpYMU9P"&sign_type="RSA"
             */

            public AlipayBean alipay;

            public static class AlipayBean {
                public String orderInfo;
            }
        }
    }
}
