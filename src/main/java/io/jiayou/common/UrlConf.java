package io.jiayou.common;

import java.util.ResourceBundle;

public class UrlConf {

	protected static final String V = "online";

	//读取配置文件，本地的是local_conf,线上为online_conf
	private static ResourceBundle URL = ResourceBundle.getBundle("conf/" + V + "_url");

	public static final String OUATHURL = URL.getString("payment.platform.ouathurl").trim();
	public static final String DO = URL.getString("payment.platform.do").trim();
	public static final String NOTIFYURL = URL.getString("payment.platform.notifyurl").trim();
	public static final String PLATFORM = URL.getString("payment.platform").trim();
	public static final String SIGN_KEY = URL.getString("payment.sign.key").trim();
	public static final String BACK_URL = URL.getString("wechat.back.url").trim();
	public static final String BACK_URL_IN = URL.getString("wechat.back.url.in").trim();

	

	public static void main(String[] args) {
		System.out.println("OUATHURL=>" + OUATHURL);
		System.out.println("DO=>" + DO);
		System.out.println("NOTIFYURL=>" + NOTIFYURL);
		System.out.println("PLATFORM=>" + PLATFORM);
		System.out.println("SIGN_KEY=>" + SIGN_KEY);
		System.out.println("BACK_URL=>" + BACK_URL);
		System.out.println("BACK_URL_IN=>" + BACK_URL_IN);
	}

}
