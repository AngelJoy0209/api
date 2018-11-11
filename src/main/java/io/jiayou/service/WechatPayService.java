package io.jiayou.service;

import io.jiayou.entity.coupon.vo.EventInfoEntity;

import com.baomidou.mybatisplus.service.IService;

public interface WechatPayService extends IService<EventInfoEntity>{

	String pay(String eventId, String custId, Integer count);
	
	String notify(String xml) throws Exception;
}
