/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.jiayou.service.impl;

import io.jiayou.dao.CardOrderDao;
import io.jiayou.dao.CustInfoDao;
import io.jiayou.dao.EventInfoDao;
import io.jiayou.datasources.DataSourceNames;
import io.jiayou.datasources.annotation.DataSource;
import io.jiayou.entity.AlipayBean;
import io.jiayou.entity.coupon.po.CardOrderEntity;
import io.jiayou.entity.coupon.vo.EventInfoEntity;
import io.jiayou.form.AlipayForm;
import io.jiayou.service.AlipayService;
import io.jiayou.service.CustCardService;
import io.jiayou.service.impl.WechatPayServiceImpl.PAYMENTTYPE;
import io.jiayou.service.impl.WechatPayServiceImpl.PAYTYPE;
import io.jiayou.service.impl.WechatPayServiceImpl.STATUSTYPE;
import io.jiayou.utils.HttpUtils;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("alipayService")
@PropertySource(value = {"classpath:system.properties"},encoding="utf-8")
public class AlipayServiceImpl extends ServiceImpl<EventInfoDao, EventInfoEntity> implements AlipayService {

	@Autowired
	EventInfoDao eventInfoDao;
	@Autowired
	CustCardService custCardService;
	@Autowired
	CardOrderDao cardOrderDao;
	@Autowired
	CustInfoDao custinfoDao;
	@Value("${alipay_url}")
    private String url;
	@Value("${app_ins}")
    private String appins;
	@Value("${notify_url}")
    private String notify;
	
	@DataSource(name = DataSourceNames.SECOND)
	public String pay(AlipayForm payInfo){
		EventInfoEntity query = new EventInfoEntity();
		query.setEventId(Integer.parseInt(payInfo.getEvent_id()));
		EventInfoEntity eventInfo = eventInfoDao.getEventById(query);
		AlipayBean info = new AlipayBean();
		//获取单价
		BigDecimal buyAmt = eventInfo.getBuyAmt();
		//获取活动名称
		String eventNm = eventInfo.getEventNm();
		BigDecimal amount = new BigDecimal(payInfo.getOrd_qty());
		info.setApp_ins(appins);
		info.setBody("购买礼金券");
		info.setOut_trade_no(System.currentTimeMillis()+payInfo.getCust_id()+payInfo.getEvent_id());
		info.setTotal_amount(buyAmt.multiply(amount).doubleValue());
//		info.setTotal_amount(0.01);
		String temp = "http://testpay.haizeworld.com/coupon/pay?eventId="+payInfo.getEvent_id()+"&number="+amount+"&money="+info.getTotal_amount()+"&cust_id="+payInfo.getCust_id();
		String backurl = Base64.getEncoder().encodeToString(temp.getBytes());
		info.setBack_url("http://testpay.haizeworld.com/coupon/alipayresult?afreshurl="+backurl);
		info.setNotify_url(notify);
		info.setProduct_code(payInfo.getEvent_id());
		info.setSubject(eventNm);
		info.setSign(info.getSignAuto());
		
		CardOrderEntity orderEntity = new CardOrderEntity();
		orderEntity.setOrderNo(info.getOut_trade_no());
		orderEntity.setCustId(Integer.parseInt(payInfo.getCust_id()));
		orderEntity.setEventId(Integer.parseInt(payInfo.getEvent_id()));
		orderEntity.setAmount(payInfo.getOrd_qty());
		orderEntity.setOrderBelong(PAYTYPE.JT.getDesc());
		orderEntity.setUnitPrice(buyAmt);
		orderEntity.setTotalPrice(new BigDecimal(info.getTotal_amount()));
		orderEntity.setStatus(STATUSTYPE.WAITPAY.getValue());
		orderEntity.setPaymentType(PAYMENTTYPE.ALI.getValue());
		cardOrderDao.save(orderEntity);
		
		return HttpUtils.postJson(url, JSONObject.toJSONString(info));
	}
	
	@DataSource(name = DataSourceNames.SECOND)
	public int notify(String out_trade_no,String trade_no,Date pay_time){
		CardOrderEntity query = new  CardOrderEntity();
        query.setOrderNo(out_trade_no);
        CardOrderEntity entity = cardOrderDao.getCardOrder(query);
        if(entity == null){
        	return -1;
        }
        Integer status = entity.getStatus();
        if(status == 2){//已经支付成功
        	return -1;
        }
//        BigDecimal totalPrice = entity.getTotalPrice(); 
        CardOrderEntity cardOrderPo = new CardOrderEntity();
        cardOrderPo.setPaymentTime(pay_time);
        cardOrderPo.setOrderNo(out_trade_no);
        cardOrderPo.setOutOrderNo(trade_no);
        cardOrderPo.setStatus(STATUSTYPE.SUCCESS.getValue());
        Integer updateCount = cardOrderDao.updateByOrderNo(cardOrderPo);
        if(updateCount > 0){
        	custCardService.setLjqToCust(out_trade_no);
        	return 0;
    	}
        
		return -1;
	}

}
