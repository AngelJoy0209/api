package io.jiayou.service;

import io.jiayou.entity.coupon.vo.CustCouponEntity;

import com.baomidou.mybatisplus.service.IService;

public interface CustCardService extends IService<CustCouponEntity>{
	public boolean setLjqToCust(String orderCode); 
}
