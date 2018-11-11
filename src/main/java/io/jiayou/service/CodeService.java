package io.jiayou.service;

import io.jiayou.entity.coupon.po.SmsMsgCodeEntity;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.service.IService;

public interface CodeService extends IService<SmsMsgCodeEntity>{
	void getCodeImg(HttpServletResponse response,String phoneNo) throws IOException;
	
	Integer sendCodeMsg(String phoneNo); 
	
	SmsMsgCodeEntity getVerifiCode(SmsMsgCodeEntity entity);
	
}
