package io.jiayou.entity;

import io.jiayou.utils.MD5Util;

public class AlipayBean {
	private String out_trade_no;//商户订单号
	private String subject;//订单名称
	private Double total_amount;//付款金额
	private String body;//商品描述
	private String notify_url;//异步通知地址
	private String back_url;//回调地址
	private String timeout_express;//超时时间
	private String product_code;//销售产品编码
	private String sign;//md5签名
	private String app_ins;//支付宝账户标识


	public String getApp_ins() {
		return app_ins;
	}

	public void setApp_ins(String app_ins) {
		this.app_ins = app_ins;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public String getTimeout_express() {
		return timeout_express;
	}

	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public boolean validate(){
		StringBuilder str = new StringBuilder();
		str.append("back_url=").append(back_url).append("&body=").append(body).append("&notify_url=").append(notify_url)
				.append("&out_trade_no=").append(out_trade_no).append("&timeout_express=").append(timeout_express)
				.append("&total_amount=").append(String.format("%.2f", total_amount)).append("&token=jiayougo");
		System.out.println(MD5Util.MD5(str.toString()));
		return MD5Util.MD5(str.toString()).equals(sign);
	}

	public String getSignAuto(){
		StringBuilder str = new StringBuilder();
		str.append("back_url=").append(back_url).append("&body=").append(body).append("&notify_url=").append(notify_url)
				.append("&out_trade_no=").append(out_trade_no).append("&timeout_express=").append(timeout_express)
				.append("&total_amount=").append(String.format("%.2f", total_amount)).append("&token=jiayougo");
		return MD5Util.MD5(str.toString());
	}
	
	public static enum TYPE{
		BFWL(1,"缤纷物流"),HJY(2,"惠家有"),SHZX(3,"上海尊享"),JT(4,"集团"),HJGY(5,"行家广域");

		TYPE(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
		private int value;
		private String desc;
		public int getValue() {
			return value;
		}
		public String getDesc() {
			return desc;
		}
		
	}
	
}
