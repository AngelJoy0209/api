package io.jiayou.service.impl;

import io.jiayou.common.UrlConf;
import io.jiayou.dao.CardOrderDao;
import io.jiayou.dao.CustInfoDao;
import io.jiayou.dao.EventInfoDao;
import io.jiayou.datasources.DataSourceNames;
import io.jiayou.datasources.annotation.DataSource;
import io.jiayou.entity.coupon.PayBean;
import io.jiayou.entity.coupon.po.CardOrderEntity;
import io.jiayou.entity.coupon.vo.EventInfoEntity;
import io.jiayou.service.CustCardService;
import io.jiayou.service.WechatPayService;
import io.jiayou.utils.HttpUtils;
import io.jiayou.utils.WXPayUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("wechatPayService")
public class WechatPayServiceImpl extends ServiceImpl<EventInfoDao, EventInfoEntity> implements WechatPayService{

	@Autowired
	EventInfoDao eventInfoDao;
	@Autowired
	CardOrderDao cardOrderDao;
	@Autowired
	CustInfoDao custinfoDao;
	@Autowired
	CustCardService custCardService;
	
	private final static Logger logger = LoggerFactory.getLogger(WechatPayServiceImpl.class);
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public String pay(String eventId, String custId, Integer count) {
		EventInfoEntity query = new EventInfoEntity();
		query.setEventId(Integer.parseInt(eventId));
		EventInfoEntity eventInfo = eventInfoDao.getEventById(query);
		//获取单价
		BigDecimal buyAmt = eventInfo.getBuyAmt();
		//获取活动名称
		String eventNm = eventInfo.getEventNm();
		BigDecimal amount = new BigDecimal(count);
		StringBuilder doUrl = new StringBuilder(UrlConf.DO);
		String out_trade_no = System.currentTimeMillis()+custId+eventId;
		//计算支付金额
		Double total_fee = buyAmt.multiply(amount).doubleValue();
		String totalFeeStr = String.format("%.2f", total_fee);
		String notify_url = UrlConf.NOTIFYURL;
		String needParams = UrlConf.BACK_URL_IN+"?eventId="+eventId+"&number="+count+"&money="+totalFeeStr+"&cust_id="+custId;
		String body = "";
		String s = "";
		String backUrl = "";
		try {
			body = Base64.getEncoder().encodeToString(eventNm.getBytes("utf-8"));
			s = Base64.getEncoder().encodeToString(needParams.getBytes("utf-8"));
			StringBuilder back_url = new StringBuilder(UrlConf.BACK_URL).append("?afreshurl=").append(s);
			backUrl = Base64.getEncoder().encodeToString(back_url.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String platform = UrlConf.PLATFORM;
		PayBean payBean = new PayBean();
		//获取签名，并拼接参数
		Map<String,String> paramMap = new HashMap<String,String>();
		String timeStamp = System.currentTimeMillis()+"";
		paramMap.put("back_url", backUrl);
		paramMap.put("body", body);
		paramMap.put("notify_url", notify_url);
		paramMap.put("out_trade_no", out_trade_no);
		paramMap.put("total_fee",  totalFeeStr);
		paramMap.put("platform", platform);
		paramMap.put("time_stamp", timeStamp);
		payBean.setBack_url(backUrl);
		payBean.setTotal_fee(new BigDecimal(totalFeeStr).doubleValue());
		payBean.setBody(body);
		payBean.setNotify_url(notify_url);
		payBean.setOut_trade_no(out_trade_no);
		payBean.setPlatform(platform);
		payBean.setTime_stamp(timeStamp);
		payBean.setSign();
		paramMap.put("sign", payBean.getSign());
		String json = "";
		try {
			paramMap.put("body", URLEncoder.encode(paramMap.get("body").toString(),"UTF-8"));
			json = URLEncoder.encode(JSON.toJSONString(paramMap),"UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		doUrl.append("?json=").append(json);
		String redirecturl = doUrl.toString();
		String param = "platform="+platform+"&redirecturl="+redirecturl;
		CardOrderEntity orderEntity = new CardOrderEntity();
		orderEntity.setOrderNo(out_trade_no);
		orderEntity.setCustId(Integer.parseInt(custId));
		orderEntity.setEventId(Integer.parseInt(eventId));
		orderEntity.setAmount(count);
		orderEntity.setOrderBelong(PAYTYPE.JT.getDesc());
		orderEntity.setUnitPrice(buyAmt);
		orderEntity.setTotalPrice(new BigDecimal(totalFeeStr));
		orderEntity.setStatus(STATUSTYPE.WAITPAY.getValue());
		orderEntity.setPaymentType(PAYMENTTYPE.WX.getValue());
		Integer saveCount = cardOrderDao.save(orderEntity);
		String a = "";
		logger.info("调取微信支付平台参数====>"+param);
		if(saveCount > 0){
			a = HttpUtils.postUrlEncode(UrlConf.OUATHURL,param);
			a = a.replace("%7B", "%257B").replace("%7D", "%257D");
		}
		logger.info("微信支付平台返回参数====>"+a);
		return a;
	}
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public String notify(String xml) throws Exception{
		logger.info("微信支付后回调入参========>"+xml);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Map<String, String> map = WXPayUtil.xmlToMap(xml);
	        boolean signatureValid = WXPayUtil.isSignatureValid(map, UrlConf.SIGN_KEY);
	        if(!signatureValid){
	        	return "";
	        }
	        //校验成功
	        //获取订单号
	        String orderNo = map.get("out_trade_no")!=null?map.get("out_trade_no").toString():"";
	        //获取金额
	        String totalFeeStr = map.get("total_fee")!=null?map.get("total_fee").toString():"0";
	        BigDecimal totalFee = new BigDecimal(totalFeeStr).divide(new BigDecimal(100));
	        CardOrderEntity query = new  CardOrderEntity();
	        query.setOrderNo(orderNo);
	        CardOrderEntity entity = cardOrderDao.getCardOrder(query);
	        if(entity == null){
	        	return "";
	        }
	        Integer status = entity.getStatus();
	        if(status == 2){//已经支付成功
	        	Map<String,String> resultMap = new HashMap<String,String>();
	        	resultMap.put("return_code", "SUCCESS");
	        	resultMap.put("return_msg", "OK");
	        	String resultStr = WXPayUtil.mapToXml(resultMap);
	        	return resultStr;
	        }
	        BigDecimal totalPrice = entity.getTotalPrice(); 
	        Integer a = totalPrice.compareTo(totalFee);
	        if(a != 0){
	        	return "";
	        }
	        String resultCode = map.get("result_code")!=null ? map.get("result_code").toString():"";
	        	//支付成功业务操作
        	CardOrderEntity cardOrderPo = new CardOrderEntity();
        	String outOrderNo = map.get("transaction_id")!=null?map.get("transaction_id").toString():"";
        	String successTime = map.get("time_end")!=null?map.get("time_end").toString():"";
        	if(successTime.length() == 14){
        		successTime = successTime.subSequence(0, 4).toString()+"-"+successTime.subSequence(4, 6).toString()+"-"+successTime.subSequence(6, 8).toString()+" "+successTime.subSequence(8, 10).toString()+":"+successTime.subSequence(10, 12).toString()+":"+successTime.subSequence(12, 14).toString();
        		Date paymentTime = sdf.parse(successTime);
        		cardOrderPo.setPaymentTime(paymentTime);
        	}
        	cardOrderPo.setOrderNo(orderNo);
        	cardOrderPo.setOutOrderNo(outOrderNo);
        	if("SUCCESS".equals(resultCode)){
        		cardOrderPo.setStatus(STATUSTYPE.SUCCESS.getValue());
        	}else{
        		cardOrderPo.setStatus(STATUSTYPE.FAIL.getValue());
        	}
        	Integer updateCount = cardOrderDao.updateByOrderNo(cardOrderPo);
        	if(updateCount == 0){
        		return "";
        	}
        	//赋予礼金业务。
        	//查询礼金面额
        	//获取当前用户礼金券的seq
        	if("SUCCESS".equals(resultCode)){
        		
        		custCardService.setLjqToCust(orderNo);
        		
        	}
        	Map<String,String> resultMap = new HashMap<String,String>();
        	resultMap.put("return_code", "SUCCESS");
        	resultMap.put("return_msg", "OK");
        	String resultStr = WXPayUtil.mapToXml(resultMap);
        	return resultStr;

	}
	
	public static enum PAYTYPE {
		
		BFWL(1,"缤纷物流"),HJY(2,"惠家有"),SHZX(3,"上海尊享"),JT(4,"集团");

		PAYTYPE(int value, String desc) {
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
	public static enum STATUSTYPE {
			
			WAITPAY(1,"待支付"),SUCCESS(2,"支付成功"),FAIL(3,"支付失败");
	
			STATUSTYPE(int value, String desc) {
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
	
	public static enum PAYMENTTYPE {
		
		ALI(1,"支付宝"),WX(2,"微信");

		PAYMENTTYPE(int value, String desc) {
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
