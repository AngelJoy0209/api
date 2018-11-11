package io.jiayou.utils;

import java.util.HashMap;
import java.util.Map;

public class Test {

//	public static void main(String[] args) {
//		String a = "lrgnCd=330000";
//		String b = HttpUtils.sendPost("http://192.168.210.114:8080/jiayou-api/coupon/wxnotify_url", a);
//		System.out.println("===========>"+b);
//	}
	
	public static void main(String[] args) throws Exception{
        String xml = "<xml>\n" +
                "    <appid>\n" +
                "        <![CDATA[wx075bad87fbd618e7]]>\n" +
                "    </appid>\n" +
                "    <bank_type>\n" +
                "        <![CDATA[CFT]]>\n" +
                "    </bank_type>\n" +
                "    <cash_fee>\n" +
                "        <![CDATA[1]]>\n" +
                "    </cash_fee>\n" +
                "    <fee_type>\n" +
                "        <![CDATA[CNY]]>\n" +
                "    </fee_type>\n" +
                "    <is_subscribe>\n" +
                "        <![CDATA[N]]>\n" +
                "    </is_subscribe>\n" +
                "    <mch_id>\n" +
                "        <![CDATA[1274534701]]>\n" +
                "    </mch_id>\n" +
                "    <nonce_str>\n" +
                "        <![CDATA[RNnW7o8ig3HRT2dh]]>\n" +
                "    </nonce_str>\n" +
                "    <openid>\n" +
                "        <![CDATA[ow7QAwYk_gRp-npwA8NGWiZtsqHw]]>\n" +
                "    </openid>\n" +
                "    <out_trade_no>\n" +
                "        <![CDATA[15371729861619999991009225]]>\n" +
                "    </out_trade_no>\n" +
                "    <result_code>\n" +
                "        <![CDATA[SUCCESS]]>\n" +
                "    </result_code>\n" +
                "    <return_code>\n" +
                "        <![CDATA[SUCCESS]]>\n" +
                "    </return_code>\n" +
                "    <sign>\n" +
                "        <![CDATA[AA05E31E37510D9DCF191634682376F2]]>\n" +
                "    </sign>\n" +
                "    <time_end>\n" +
                "        <![CDATA[20180917163104]]>\n" +
                "    </time_end>\n" +
				
                "    <total_fee>1</total_fee>\n" +
                "    <trade_type>\n" +
                "        <![CDATA[JSAPI]]>\n" +
                "    </trade_type>\n" +
                "    <transaction_id>\n" +
                "        <![CDATA[4200000191201809172073072693]]>\n" +
                "    </transaction_id>\n" +
                "</xml>\n";
        String signkey = "OU23O0CJQ65PLNNG68XYLNBE6L43YBVH";
        Map<String, String> stringStringMap = WXPayUtil.xmlToMap(xml);
        Map<String,String> strmap = new HashMap<String, String>();
        for(String str: stringStringMap.keySet()){
            System.out.println(str+":"+stringStringMap.get(str).trim());
            strmap.put(str.trim(),stringStringMap.get(str).trim());
        }
        boolean signatureValid = WXPayUtil.isSignatureValid(strmap, signkey);
        System.out.println(signatureValid);
    }
}
