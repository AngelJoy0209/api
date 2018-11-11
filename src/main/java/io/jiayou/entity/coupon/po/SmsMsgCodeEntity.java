package io.jiayou.entity.coupon.po;

import java.util.Date;

public class SmsMsgCodeEntity {
	
	/**
	 * 电话号码
	 */
	public String phoneNo;
	/**
	 * 验证码
	 */
	public String code;
	/**
	 * 发送短信内容
	 */
	public String msg;
	
	public Integer codeType;
	
	public Date createTime;
	
	public Date expiryTime;
	
	
	public Integer getCodeType() {
		return codeType;
	}
	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
