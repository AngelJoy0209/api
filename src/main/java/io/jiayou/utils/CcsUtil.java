package io.jiayou.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccs.client.bean.GetCallByCustInfo;
import com.ccs.client.bean.GetCustByCallInfo;
import com.ccs.client.post.ClientPost;

/**
 * 电话查客户信息接口工具类
 * @author zhao.long
 * 2016-6-29 上午11:49:51
 * CcsUtil.java
 */
public class CcsUtil {
	
	/**
	 * 获得ip地址的方法
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 根据客户信息查电话
	 * @param custId
	 * @param ip
	 * @return
	 */
	public static Map<String,String> getCallByCustMap(String custId,String ip){
		Map<String,String> map = new HashMap<String, String>();
		String rtnValue = "";
		try {
			GetCallByCustInfo custInfo = new GetCallByCustInfo();
			custInfo.setOs_id("AppInterface");
			custInfo.setOs_mod("AppInterface");
			custInfo.setCust_id(custId);
			custInfo.setIp_addr(ip);
			rtnValue = ClientPost.getCallByCust(custInfo);
		} catch (Exception e) {
			System.out.println("AppInterface-->CssUtil中根据客户信息查电话异常." + e);
		}
		if(rtnValue != null && rtnValue.length() > 0){
			JSONObject j1 = JSONObject.parseObject(rtnValue);
	    	if(j1 != null){
	    		String data = j1.getString("data");
	    		if(data != null){
	    			JSONObject jo = JSONObject.parseObject(data);
	    			map.put("teld", jo.get("teld") != null?jo.get("teld").toString():"");
	    			map.put("telh", jo.get("telh")!= null?jo.get("telh").toString():"");
	    			map.put("teln", jo.get("teln")!= null?jo.get("teln").toString():"");
	    			map.put("teli", jo.get("teli")!= null?jo.get("teli").toString():"");
	    			map.put("hp_teld", jo.get("hp_teld")!= null?jo.get("hp_teld").toString():"");
	    			map.put("hp_telh", jo.get("hp_telh")!= null?jo.get("hp_telh").toString():"");
	    			map.put("hp_teln", jo.get("hp_teln")!= null?jo.get("hp_teln").toString():"");
	    			map.put("phone",jo.get("phone")!= null?jo.get("phone").toString():"");
	    			map.put("mobile",jo.get("mobile")!= null?jo.get("mobile").toString():"");
	    		}
	    	}
		}
		return map;
	}
	
	
	public static void main(String[] args) {
		String rtnValue = "";
		try {
			GetCustByCallInfo custInfo = new GetCustByCallInfo();
			custInfo.setOs_id("AppInterface");
			custInfo.setOs_mod("AppInterface");
			custInfo.setCall_no("18810000320");
			custInfo.setIp_addr("127.0.0.1");
			rtnValue = ClientPost.getCustByCall(custInfo);
		} catch (Exception e) {
			System.out.println("AppInterface-->CssUtil中根据电话查客户id异常." + e);
		}
		System.out.println(rtnValue);
	}
	
	/**
	 * 根据电话查客户信息
	 * @param callNo
	 * @param ip
	 * @return
	 */
	public static List<String> getCustIdListByCall(String callNo,String ip){
		String rtnValue = "";
		List<String> list = new ArrayList<String>();
		try {
			GetCustByCallInfo custInfo = new GetCustByCallInfo();
			custInfo.setOs_id("AppInterface");
			custInfo.setOs_mod("AppInterface");
			custInfo.setCall_no(callNo);
			custInfo.setIp_addr(ip);
			rtnValue = ClientPost.getCustByCall(custInfo);
		} catch (Exception e) {
			System.out.println("AppInterface-->CssUtil中根据电话查客户id异常." + e);
		}
		if(rtnValue != null && rtnValue.length() > 0){
			JSONObject j = JSONObject.parseObject(rtnValue);
			if(j==null){
				return null;
			}
			String data = j.getString("data");
			if(StringUtils.isEmpty(data)){
				return null;
			}
			JSONObject jo = JSONObject.parseObject(data);
			if(jo == null){
				return null;
			}
	    	String listStr = jo.getString("cust_info");
	    	if(StringUtils.isEmpty(listStr)){
	    		return null;
	    	}
    		JSONArray ja = JSONArray.parseArray(listStr) ;
    		Iterator<Object> it = ja.iterator();
    		while(it.hasNext()){
    			JSONObject cust = (JSONObject)it.next();
    			if(!StringUtils.isEmpty(cust.getString("cust_id"))){
    				list.add(cust.getString("cust_id"));
    			}
    		}
		}
		return list;
	}
}
