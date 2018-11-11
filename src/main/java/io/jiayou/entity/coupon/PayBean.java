package io.jiayou.entity.coupon;

import io.jiayou.utils.MD5Util;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PayBean {
	private String body;
	private String out_trade_no;
	private Double total_fee;
	private String notify_url;
	private String back_url;
	private String platform;

	private String time_stamp;
	private String sign;


	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getBack_url() {
		return back_url;
	}

	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}

	public String getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public boolean validate(){
		TreeMap<String,Object> treeMap = new TreeMap<String,Object>();
		treeMap.put("body",body);
		treeMap.put("out_trade_no",out_trade_no);
		treeMap.put("total_fee",total_fee);
		treeMap.put("notify_url",notify_url);
		treeMap.put("back_url",back_url);
		treeMap.put("platform",platform);
		treeMap.put("time_stamp",time_stamp);
		StringBuilder str = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = treeMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> next = iterator.next();
			if(!next.getKey().equals("total_fee")){
				str.append(next.getKey()).append("=").append(next.getValue()).append("&");
			}else{
				str.append(next.getKey()).append("=").append(String.format("%.2f", total_fee)).append("&");
			}
		}
		return MD5Util.MD5(str.append("token=jiayougo").toString()).equals(sign);
		/*str.append("back_url=").append(back_url).append("&body=").append(body).append("&notify_url=").append(notify_url)
				.append("&out_trade_no=").append(out_trade_no).append("&time_stamp=").append(time_stamp)
				.append("&total_fee=").append(String.format("%.2f", total_fee)).append("&token=jiayougo");*/

	}
	
	public String setSign(){
		TreeMap<String,Object> treeMap = new TreeMap<String,Object>();
		treeMap.put("body",body);
		treeMap.put("out_trade_no",out_trade_no);
		treeMap.put("total_fee",total_fee);
		treeMap.put("notify_url",notify_url);
		treeMap.put("back_url",back_url);
		treeMap.put("platform",platform);
		treeMap.put("time_stamp",time_stamp);
		StringBuilder str = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = treeMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> next = iterator.next();
			if(!next.getKey().equals("total_fee")){
				str.append(next.getKey()).append("=").append(next.getValue()).append("&");
			}else{
				str.append(next.getKey()).append("=").append(String.format("%.2f", total_fee)).append("&");
			}
		}
		this.sign = MD5Util.MD5(str.append("token=jiayougo").toString());
		return str.toString();
	}

	public static void main(String[] args) {
		String str1 = "{\"body\":\"\\u8bd5\\u7eb8+\\u4f53\\u6e29\\u8ba1+USB\\u676f\\u57ab+\\u624b\\u8868\",\"out_trade_no\":\"1502259704A001177920833\",\"total_fee\":0.01,\"notify_url\":\"http:\\/\\/test6.haizeworld.com\\/Pay\\/Pay\\/wxNotify\",\"back_url\":\"http:\\/\\/test6.haizeworld.com\\/Pay\\/Pay\\/payStatus?status=1&invc_id=A001177920833&money=314.00\",\"time_stamp\":1502267928,\"platform\":1,\"sign\":\"1115898ada1b546a26f4ffa40057b8a0\"}";
//		PayBean payBean = JSON.parseObject(WebUtils.convert(str1), PayBean.class);
//		System.out.println(payBean.getBody());
		/*PayBean payBean = JSON.parseObject(str1, PayBean.class);
		System.out.println(payBean.validate());
		TreeMap<String,Object> treeMap = new TreeMap<String,Object>();
		treeMap.put("body",payBean.getBody());
		treeMap.put("out_trade_no",payBean.getOut_trade_no());
		treeMap.put("total_fee",payBean.getTotal_fee());
		treeMap.put("notify_url",payBean.getNotify_url());
		treeMap.put("back_url",payBean.getBack_url());
		treeMap.put("platform",payBean.getPlatform());
		treeMap.put("time_stamp",payBean.getTime_stamp());
		StringBuilder str = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = treeMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> next = iterator.next();
			if(!next.getKey().equals("total_fee")){
				str.append(next.getKey()).append("=").append(next.getValue()).append("&");
			}else{
				str.append(next.getKey()).append("=").append(String.format("%.2f",next.getValue())).append("&");
			}
		}
		System.out.println(MD5Util.MD5(str.append("token=jiayougo").toString()));
		System.out.println(payBean.getSign());*/
	}
	/*public boolean validate(){
		StringBuilder str = new StringBuilder();
		str.append("back_url=").append(back_url).append("body=").append(body).append("notify_url=").append(notify_url)
				.append("out_trade_no=").append(out_trade_no).append("time_stamp=").append(time_stamp)
				.append("total_fee=").append(String.format("%.2f", total_fee)).append("token=jiayougo");
		return MD5Util.MD5(str.toString()).equals(sign);
	}*/
}
