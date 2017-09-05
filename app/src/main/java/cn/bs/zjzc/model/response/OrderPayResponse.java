package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by mgc on 2016/7/2.
 */
public class OrderPayResponse extends BaseResponse{


    /**
     * id : ch_0iTWv95O0yDSy5e5yDzzHmjP
     * object : charge
     * created : 1466406246
     * livemode : false
     * paid : false
     * refunded : false
     * app : app_1Gqj58ynP0mHeX1q
     * channel : alipay_wap
     * order_no : 189fd440a223
     * client_ip : 127.0.0.1
     * amount : 100
     * amount_settle : 100
     * currency : cny
     * subject : Your Subject
     * body : Your Body
     * extra : {"success_url":"http://example.com/success","cancel_url":"http://example.com/cancel"}
     * time_paid : null
     * time_expire : 1466492646
     * time_settle : null
     * transaction_no : null
     * refunds : {"object":"list","url":"/v1/charges/ch_0iTWv95O0yDSy5e5yDzzHmjP/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code : null
     * failure_msg : null
     * metadata : {}
     * credential : {"object":"credential","alipay_wap":{"channel_url":"https://mapi.alipay.com/gateway.do","body":"Your Body","it_b_pay":"2016-06-21 15:04:06","notify_url":"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_0iTWv95O0yDSy5e5yDzzHmjP","out_trade_no":"189fd440a223","partner":"2008845014558968","payment_type":"1","return_url":"http%3A%2F%2Fexample.com%2Fsuccess","seller_id":"2008845014558968","service":"alipay.wap.create.direct.pay.by.user","subject":"Your Subject","total_fee":"1.00","sign":"otYXSLFAB0Cak2zg5aULfLlB/GCjtjYeOeJDy5u+1bydOIsN/i17531M+FYXxRNnKmM14GSzkxdf576t8cJdjFJXrZYrmuypuMTniPCFBCOEpX/bAT6LnJfSd/gzuUYKFExlqCOSgdBKNmMfsFRcLMHWwNmjQMLmgMFKSLbrRcU=","sign_type":"RSA"}}
     * description : null
     */

    public DataBean data;

    public static class DataBean {
        public String id;
        public String object;
        public int created;
        public boolean livemode;
        public boolean paid;
        public boolean refunded;
        public String app;
        public String channel;
        public String order_no;
        public String client_ip;
        public int amount;
        public int amount_settle;
        public String currency;
        public String subject;
        public String body;
        /**
         * success_url : http://example.com/success
         * cancel_url : http://example.com/cancel
         */

        public ExtraBean extra;
        public Object time_paid;
        public int time_expire;
        public Object time_settle;
        public Object transaction_no;
        /**
         * object : list
         * url : /v1/charges/ch_0iTWv95O0yDSy5e5yDzzHmjP/refunds
         * has_more : false
         * data : []
         */

        public RefundsBean refunds;
        public int amount_refunded;
        public Object failure_code;
        public Object failure_msg;
        public MetadataBean metadata;
        /**
         * object : credential
         * alipay_wap : {"channel_url":"https://mapi.alipay.com/gateway.do","body":"Your Body","it_b_pay":"2016-06-21 15:04:06","notify_url":"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_0iTWv95O0yDSy5e5yDzzHmjP","out_trade_no":"189fd440a223","partner":"2008845014558968","payment_type":"1","return_url":"http%3A%2F%2Fexample.com%2Fsuccess","seller_id":"2008845014558968","service":"alipay.wap.create.direct.pay.by.user","subject":"Your Subject","total_fee":"1.00","sign":"otYXSLFAB0Cak2zg5aULfLlB/GCjtjYeOeJDy5u+1bydOIsN/i17531M+FYXxRNnKmM14GSzkxdf576t8cJdjFJXrZYrmuypuMTniPCFBCOEpX/bAT6LnJfSd/gzuUYKFExlqCOSgdBKNmMfsFRcLMHWwNmjQMLmgMFKSLbrRcU=","sign_type":"RSA"}
         */

        public CredentialBean credential;
        public Object description;

        public static class ExtraBean {
            public String success_url;
            public String cancel_url;
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
             * channel_url : https://mapi.alipay.com/gateway.do
             * body : Your Body
             * it_b_pay : 2016-06-21 15:04:06
             * notify_url : https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_0iTWv95O0yDSy5e5yDzzHmjP
             * out_trade_no : 189fd440a223
             * partner : 2008845014558968
             * payment_type : 1
             * return_url : http%3A%2F%2Fexample.com%2Fsuccess
             * seller_id : 2008845014558968
             * service : alipay.wap.create.direct.pay.by.user
             * subject : Your Subject
             * total_fee : 1.00
             * sign : otYXSLFAB0Cak2zg5aULfLlB/GCjtjYeOeJDy5u+1bydOIsN/i17531M+FYXxRNnKmM14GSzkxdf576t8cJdjFJXrZYrmuypuMTniPCFBCOEpX/bAT6LnJfSd/gzuUYKFExlqCOSgdBKNmMfsFRcLMHWwNmjQMLmgMFKSLbrRcU=
             * sign_type : RSA
             */

            public AlipayWapBean alipay_wap;

            public static class AlipayWapBean {
                public String channel_url;
                public String body;
                public String it_b_pay;
                public String notify_url;
                public String out_trade_no;
                public String partner;
                public String payment_type;
                public String return_url;
                public String seller_id;
                public String service;
                public String subject;
                public String total_fee;
                public String sign;
                public String sign_type;
            }
        }
    }
}
