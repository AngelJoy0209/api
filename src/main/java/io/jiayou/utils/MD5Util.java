package io.jiayou.utils;

import java.security.MessageDigest;


/**
 * Created by 12154 on 2017-2-27.
 */
public class MD5Util {
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    public static void main(String[] args) {
		System.out.println(MD5Util.MD5("back_url=null&body=退款&notify_url=null&out_trade_no=36970297&timeout_express=null&total_amount=199.00&token=jiayougo"));
	}
}
